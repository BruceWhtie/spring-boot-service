package ewing.security;

import java.util.List;

/**
 * 安全数据访问接口。
 */
public interface SecurityDao extends SecurityBeans {

    SecurityUser getSecurityUser(String username);

    List<AuthorityNode> getUserAuthorities(Long userId);

    List<PermissionNode> getUserPermissions(Long userId);

}
