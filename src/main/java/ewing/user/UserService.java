package ewing.user;

import ewing.application.paging.Page;
import ewing.entity.User;
import ewing.user.vo.FindUserParam;

/**
 * 用户服务接口。
 **/
public interface UserService extends UserBeans {

    Long addUser(User user);

    User getUser(Long userId);

    long updateUser(User user);

    Page<User> findUsers(FindUserParam findUserParam);

    long deleteUser(Long userId);

}
