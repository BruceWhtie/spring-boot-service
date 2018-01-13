package ewing.user;

import com.querydsl.core.types.Projections;
import ewing.application.query.BaseBeanDao;
import ewing.application.query.BeanDao;
import ewing.security.AuthorityOrRole;
import ewing.security.PermissionTree;
import ewing.security.SecurityUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户数据访问实现。
 */
@Repository
public class UserDaoImpl extends BaseBeanDao implements UserDao {

    public UserDaoImpl() {
        super(qUser);
    }

    @Override
    public SecurityUser getByUsername(String username) {
        return queryFactory.select(
                Projections.bean(SecurityUser.class, qUser.all()))
                .from(qUser)
                .where(qUser.username.eq(username))
                .fetchOne();
    }

    @Override
    public List<AuthorityOrRole> getUserAuthorities(Long userId) {
        // 用户->角色->权限
        return queryFactory.select(Projections
                .bean(AuthorityOrRole.class, qAuthority.all()))
                .from(qAuthority)
                .join(qRoleAuthority)
                .on(qAuthority.authorityId.eq(qRoleAuthority.authorityId))
                .join(qUserRole)
                .on(qRoleAuthority.roleId.eq(qUserRole.roleId))
                .where(qUserRole.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<PermissionTree> getUserPermissions(Long userId) {
        // 用户->角色->许可
        return queryFactory.select(Projections
                .bean(PermissionTree.class, qPermission.all()))
                .from(qPermission)
                .join(qRolePermission)
                .on(qPermission.permissionId.eq(qRolePermission.permissionId))
                .join(qUserRole)
                .on(qRolePermission.roleId.eq(qUserRole.roleId))
                .where(qUserRole.userId.eq(userId))
                .fetch();
    }

}
