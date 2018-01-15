package ewing.security;

import com.querydsl.core.types.Projections;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 安全数据访问实现。
 */
@Repository
public class SecurityDaoImpl implements SecurityDao {

    @Autowired
    private SQLQueryFactory queryFactory;

    @Override
    public SecurityUser getSecurityUser(String username) {
        return queryFactory.select(
                Projections.bean(SecurityUser.class, qUser.all()))
                .from(qUser)
                .where(qUser.username.eq(username))
                .fetchOne();
    }

    @Override
    public List<AuthorityNode> getUserAuthorities(Long userId) {
        // 用户->角色->权限
        return queryFactory.select(Projections
                .bean(AuthorityNode.class, qAuthority.all()))
                .from(qAuthority)
                .join(qRoleAuthority)
                .on(qAuthority.authorityId.eq(qRoleAuthority.authorityId))
                .join(qUserRole)
                .on(qRoleAuthority.roleId.eq(qUserRole.roleId))
                .where(qUserRole.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<PermissionNode> getUserPermissions(Long userId) {
        // 用户->角色->许可
        return queryFactory.select(Projections
                .bean(PermissionNode.class, qPermission.all()))
                .from(qPermission)
                .join(qRolePermission)
                .on(qPermission.permissionId.eq(qRolePermission.permissionId))
                .join(qUserRole)
                .on(qRolePermission.roleId.eq(qUserRole.roleId))
                .where(qUserRole.userId.eq(userId))
                .fetch();
    }
}
