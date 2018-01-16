package ewing.application.query;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.dml.SQLUpdateClause;

import java.util.List;

/**
 * 数据访问接口。
 */
public interface CommonDao {

    /**
     * 根据ID查询实体对象。
     */
    <B> B selectByKey(RelationalPathBase<B> base, Object key);

    /**
     * 根据ID查询指定结果。
     */
    <E> E selectByKey(RelationalPathBase<?> base, Object key, Expression<E> expression);

    /**
     * 根据ID查询唯一的实体对象。
     */
    <B> B selectOne(RelationalPathBase<B> base, Predicate predicate);

    /**
     * 根据ID查询唯一的指定结果。
     */
    <E> E selectOne(RelationalPathBase<?> base, Predicate predicate, Expression<E> expression);

    /**
     * 根据条件表达式查询总数。
     */
    long countWhere(RelationalPathBase<?> base, Predicate predicate);

    /**
     * 根据条件表达式查询实体对象。
     */
    <B> List<B> selectWhere(RelationalPathBase<B> base, Predicate predicate, OrderSpecifier... orders);

    /**
     * 根据条件表达式查询实体对象。
     */
    <E> List<E> selectWhere(RelationalPathBase<?> base, Expression<E> expression, Predicate predicate, OrderSpecifier... orders);

    /**
     * 根据条件表达式分页查询实体对象。
     */
    <B> Page<B> selectPage(RelationalPathBase<B> base, Pager pager, Predicate predicate, OrderSpecifier... orders);

    /**
     * 根据条件表达式分页查询实体对象。
     */
    <E> Page<E> selectPage(RelationalPathBase<?> base, Pager pager, Expression<E> expression, Predicate predicate, OrderSpecifier... orders);

    /**
     * 根据ID从数据库删除实体。
     */
    long deleteByKey(RelationalPathBase<?> base, Object key);

    /**
     * 根据实体中的ID属性删除实体。
     * 兼容带有对应ID属性的实体对象。
     */
    long deleteBean(RelationalPathBase<?> base, Object bean);

    /**
     * 根据条件参数删除实体。
     */
    long deleteWhere(RelationalPathBase<?> base, Predicate predicate);

    /**
     * 根据对象中的ID属性和非null属性更新实体。
     * 兼容带有对应ID属性且至少有一个要更新的属性的实体对象。
     */
    long updateBean(RelationalPathBase<?> base, Object bean);

    /**
     * 批量根据对象中的ID属性和非null属性更新实体。
     * 兼容带有对应ID属性且至少有一个要更新的属性的实体对象。
     */
    long updateBeans(RelationalPathBase<?> base, Object... bean);

    /**
     * 根据ID参数创建更新器。
     */
    SQLUpdateClause updaterByKey(RelationalPathBase<?> base, Object key);

    /**
     * 根据条件参数创建更新器。
     */
    SQLUpdateClause updaterWhere(RelationalPathBase<?> base, Predicate predicate);

    /**
     * 将实体对象非null属性插入到数据库。
     * 兼容至少包含一个对应的非null属性的实体对象。
     */
    long insertBean(RelationalPathBase<?> base, Object bean);

    /**
     * 批量将实体对象非null属性插入到数据库。
     * 兼容至少包含一个对应的非null属性的实体对象。
     */
    long insertBeans(RelationalPathBase<?> base, Object... beans);

    /**
     * 将实体对象属性插入并返回ID值。
     * 兼容至少包含一个对应的非null属性的实体对象。
     */
    <T> T insertWithKey(RelationalPathBase<?> base, Object bean);

    /**
     * 批量将实体对象属性插入并返回ID值。
     * 兼容至少包含一个对应的非null属性的实体对象。
     */
    <T> List<T> insertWithKeys(RelationalPathBase<?> base, Object... beans);
}
