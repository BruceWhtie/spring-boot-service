package ewing.user;

import ewing.application.query.BeanDao;
import ewing.security.AuthorityOrRole;
import ewing.security.PermissionTree;
import ewing.security.SecurityUser;

import java.util.List;

/**
 * 用户数据访问接口。
 */
public interface UserDao extends BeanDao, UserBeans {

    SecurityUser getByUsername(String username);

    List<AuthorityOrRole> getUserAuthorities(Long userId);

    List<PermissionTree> getUserPermissions(Long userId);

}
