package ewing.application.query;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.util.BeanUtils;
import com.querydsl.core.util.ReflectionUtils;
import com.querydsl.sql.PrimaryKey;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import ewing.application.paging.Page;
import ewing.application.paging.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 数据访问支持类。
 */
@Repository
@SuppressWarnings("unchecked")
public class CommonBaseDao implements CommonDao {

    @Autowired
    protected SQLQueryFactory queryFactory;

    protected List<Path> getKeyPaths(RelationalPathBase<?> base) {
        if (base == null) {
            throw new IllegalArgumentException("PathBase can not null.");
        }
        // 获取主键及属性
        PrimaryKey primaryKey = base.getPrimaryKey();
        if (primaryKey != null) {
            List<Path> paths = primaryKey.getLocalColumns();
            if (paths == null || paths.isEmpty()) {
                throw new IllegalArgumentException("Primary paths can not empty.");
            } else {
                return paths;
            }
        } else {
            throw new IllegalArgumentException("PrimaryKey can not null.");
        }
    }

    /**
     * 创建主键等于参数的表达式。
     */
    protected BooleanExpression keyEquals(List<Path> keyPaths, Object key) {
        if (keyPaths.size() == 1) {
            return ((SimpleExpression) keyPaths.get(0)).eq(key);
        } else {
            // 多个主键时使用实体作为主键值创建表达式。
            return beanKeyEquals(keyPaths, key);
        }
    }

    /**
     * 使用实体作为主键值创建表达式。
     */
    protected BooleanExpression beanKeyEquals(List<Path> keyPaths, Object bean) {
        if (bean == null) {
            throw new IllegalArgumentException("Argument can not null.");
        }
        BooleanExpression equals = Expressions.FALSE;
        try {
            for (Path path : keyPaths) {
                String name = path.getMetadata().getName();
                Method getter = ReflectionUtils.getGetterOrNull(bean.getClass(), name);
                if (getter == null) {
                    throw new IllegalArgumentException("No key property: " + name);
                }
                BooleanExpression equal = ((SimpleExpression) path).eq(getter.invoke(bean));
                equals = (equals == Expressions.FALSE ? equal : equals.and(equal));
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return equals;
    }

    /**
     * 获取Bean的属性Setter。
     */
    protected Method findSetter(Object bean, String name, Class type) {
        String methodName = "set" + BeanUtils.capitalize(name);
        Class beanClass = bean.getClass();
        while (beanClass != null && !beanClass.equals(Object.class)) {
            try {
                return beanClass.getDeclaredMethod(methodName, type);
            } catch (SecurityException | NoSuchMethodException e) {
                // 跳过并继续找父类
            }
            beanClass = beanClass.getSuperclass();
        }
        return null;
    }

    /**
     * 给Bean设置属性值。
     */
    protected void setBeanProperty(Object bean, String name, Object value) {
        if (bean == null || name == null || value == null) {
            throw new IllegalArgumentException("Arguments can not null.");
        }
        Method setter = findSetter(bean, name, value.getClass());
        if (setter == null) {
            throw new IllegalArgumentException("Key setter can not null.");
        }
        try {
            setter.invoke(bean, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <B> B selectByKey(RelationalPathBase<B> base, Object key) {
        return queryFactory.selectFrom(base)
                .where(keyEquals(getKeyPaths(base), key))
                .fetchOne();
    }

    @Override
    public <E> E selectByKey(RelationalPathBase<?> base, Object key, Expression<E> expression) {
        return queryFactory.select(expression)
                .from(base)
                .where(keyEquals(getKeyPaths(base), key))
                .fetchOne();
    }

    @Override
    public <B> B selectOne(RelationalPathBase<B> base, Predicate predicate) {
        return queryFactory.selectFrom(base)
                .where(predicate)
                .fetchOne();
    }

    @Override
    public <E> E selectOne(RelationalPathBase<?> base, Predicate predicate, Expression<E> expression) {
        return queryFactory.select(expression)
                .from(base)
                .where(predicate)
                .fetchOne();
    }

    @Override
    public long countWhere(RelationalPathBase<?> base, Predicate predicate) {
        return queryFactory.selectFrom(base)
                .where(predicate)
                .fetchCount();
    }

    @Override
    public <B> List<B> selectWhere(RelationalPathBase<B> base, Predicate predicate, OrderSpecifier... orders) {
        return queryFactory.selectFrom(base)
                .where(predicate)
                .orderBy(orders)
                .fetch();
    }

    @Override
    public <E> List<E> selectWhere(RelationalPathBase<?> base, Expression<E> expression, Predicate predicate, OrderSpecifier... orders) {
        return queryFactory.select(expression)
                .from(base)
                .where(predicate)
                .orderBy(orders)
                .fetch();
    }

    @Override
    public <B> Page<B> selectPage(RelationalPathBase<B> base, Pager pager, Predicate predicate, OrderSpecifier... orders) {
        SQLQuery<B> query = queryFactory
                .selectFrom(base)
                .where(predicate)
                .orderBy(orders);
        return QueryHelper.queryPage(pager, query);
    }

    @Override
    public <E> Page<E> selectPage(RelationalPathBase<?> base, Pager pager, Expression<E> expression, Predicate predicate, OrderSpecifier... orders) {
        SQLQuery<E> query = queryFactory
                .select(expression)
                .from(base)
                .where(predicate)
                .orderBy(orders);
        return QueryHelper.queryPage(pager, query);
    }

    @Override
    public long deleteByKey(RelationalPathBase<?> base, Object key) {
        return queryFactory.delete(base)
                .where(keyEquals(getKeyPaths(base), key))
                .execute();
    }

    @Override
    public long deleteBean(RelationalPathBase<?> base, Object bean) {
        return queryFactory.delete(base)
                .where(beanKeyEquals(getKeyPaths(base), bean))
                .execute();
    }

    @Override
    public long deleteWhere(RelationalPathBase<?> base, Predicate predicate) {
        return queryFactory.delete(base)
                .where(predicate)
                .execute();
    }

    @Override
    public long updateBean(RelationalPathBase<?> base, Object bean) {
        return queryFactory.update(base)
                .populate(bean)
                .where(beanKeyEquals(getKeyPaths(base), bean))
                .execute();
    }

    @Override
    public long updateBeans(RelationalPathBase<?> base, Object... beans) {
        SQLUpdateClause update = queryFactory.update(base);
        for (Object bean : beans) {
            update.populate(bean)
                    .where(beanKeyEquals(getKeyPaths(base), bean))
                    .addBatch();
        }
        return update.isEmpty() ? 0L : update.execute();
    }

    @Override
    public SQLUpdateClause updaterByKey(RelationalPathBase<?> base, Object key) {
        return queryFactory.update(base)
                .where(keyEquals(getKeyPaths(base), key));
    }

    @Override
    public SQLUpdateClause updaterWhere(RelationalPathBase<?> base, Predicate predicate) {
        return queryFactory.update(base)
                .where(predicate);
    }

    @Override
    public long insertBean(RelationalPathBase<?> base, Object bean) {
        return queryFactory.insert(base)
                .populate(bean)
                .execute();
    }

    @Override
    public long insertBeans(RelationalPathBase<?> base, Object... beans) {
        SQLInsertClause insert = queryFactory.insert(base);
        for (Object bean : beans) {
            insert.populate(bean).addBatch();
        }
        return insert.isEmpty() ? 0L : insert.execute();
    }

    @Override
    public <T> T insertWithKey(RelationalPathBase<?> base, Object bean) {
        List<Path> keyPaths = getKeyPaths(base);
        if (keyPaths.size() > 1) {
            throw new UnsupportedOperationException("Multiple primary key.");
        }
        Path keyPath = keyPaths.get(0);
        Object value = queryFactory.insert(base)
                .populate(bean)
                .executeWithKey(keyPath);
        setBeanProperty(bean, keyPath.getMetadata().getName(), value);
        return (T) value;
    }

    @Override
    public <T> List<T> insertWithKeys(RelationalPathBase<?> base, Object... beans) {
        List<Path> keyPaths = getKeyPaths(base);
        if (keyPaths.size() > 1) {
            throw new UnsupportedOperationException("Multiple primary key.");
        }
        Path keyPath = keyPaths.get(0);
        SQLInsertClause insert = queryFactory.insert(base);
        for (Object bean : beans) {
            insert.populate(bean).addBatch();
        }
        List<Object> values = insert.isEmpty() ? Collections.emptyList()
                : insert.executeWithKeys(keyPath);
        String name = keyPath.getMetadata().getName();
        for (int i = 0; i < beans.length && i < values.size(); i++) {
            setBeanProperty(beans[i], name, values.get(i));
        }
        return (List<T>) values;
    }

}
