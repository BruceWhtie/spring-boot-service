package ewing.user;

import ewing.application.paging.Page;
import ewing.entity.User;
import ewing.security.AuthorityOrRole;
import ewing.security.PermissionTree;
import ewing.security.SecurityUser;
import ewing.user.vo.FindUserParam;

import java.util.List;

/**
 * 用户服务接口。
 **/
public interface UserService extends UserBeans {

    Long addUser(User user);

    User getUser(Long userId);

    long updateUser(User user);

    Page<User> findUsers(FindUserParam findUserParam);

    long deleteUser(Long userId);

    SecurityUser getByUsername(String username);

    List<AuthorityOrRole> getUserAuthorities(Long userId);

    List<PermissionTree> getUserPermissions(Long userId);
}
