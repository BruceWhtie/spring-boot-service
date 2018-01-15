package ewing.user;

import ewing.application.query.BeanBaseDao;
import org.springframework.stereotype.Repository;

/**
 * 用户数据访问实现。
 */
@Repository
public class UserDaoImpl extends BeanBaseDao implements UserDao {

    public UserDaoImpl() {
        super(qUser);
    }

}
