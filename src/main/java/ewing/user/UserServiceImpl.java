package ewing.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import ewing.application.AppAsserts;
import ewing.application.paging.Page;
import ewing.entity.User;
import ewing.security.AuthorityOrRole;
import ewing.security.PermissionTree;
import ewing.security.SecurityUser;
import ewing.user.vo.FindUserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 用户服务实现。
 **/
@Service
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Long addUser(User user) {
        AppAsserts.notNull(user, "用户不能为空！");
        AppAsserts.hasText(user.getUsername(), "用户名不能为空！");
        AppAsserts.hasText(user.getPassword(), "密码不能为空！");
        AppAsserts.isTrue(userDao.countWhere(
                qUser.username.eq(user.getUsername())) < 1,
                "用户名已被使用！");
        if (!StringUtils.hasText(user.getNickname())) {
            user.setNickname(user.getUsername());
        }
        user.setCreateTime(new Date());
        return userDao.insertWithKey(user);
    }

    @Override
    @Cacheable(cacheNames = "UserCache", key = "#userId", unless = "#result==null")
    public User getUser(Long userId) {
        AppAsserts.notNull(userId, "用户ID不能为空！");
        return userDao.selectByKey(userId);
    }

    @Override
    @CacheEvict(cacheNames = "UserCache", key = "#user.userId")
    public long updateUser(User user) {
        AppAsserts.notNull(user, "用户不能为空！");
        AppAsserts.notNull(user.getUserId(), "用户ID不能为空！");
        return userDao.updateBean(user);
    }

    @Override
    public Page<User> findUsers(FindUserParam findUserParam) {
        BooleanExpression expression = Expressions.TRUE;
        // 用户名
        expression = expression.and(StringUtils.hasText(findUserParam.getUsername())
                ? qUser.username.eq(findUserParam.getUsername()) : null);
        // 昵称
        expression = expression.and(StringUtils.hasText(findUserParam.getNickname())
                ? qUser.nickname.eq(findUserParam.getNickname()) : null);
        return userDao.selectPage(findUserParam, expression, qUser.createTime.desc());
    }

    @Override
    @CacheEvict(cacheNames = "UserCache", key = "#userId")
    public long deleteUser(Long userId) {
        AppAsserts.notNull(userId, "用户ID不能为空！");
        return userDao.deleteByKey(userId);
    }

    @Override
    public SecurityUser getByUsername(String username) {
        AppAsserts.hasText(username, "用户名不能为空！");
        return userDao.getByUsername(username);
    }

    @Override
    public List<AuthorityOrRole> getUserAuthorities(Long userId) {
        AppAsserts.notNull(userId, "用户ID不能为空！");
        return userDao.getUserAuthorities(userId);
    }

    @Override
    public List<PermissionTree> getUserPermissions(Long userId) {
        AppAsserts.notNull(userId, "用户ID不能为空！");
        return userDao.getUserPermissions(userId);
    }

}
