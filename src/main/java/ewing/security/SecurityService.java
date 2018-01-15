package ewing.security;

import java.util.List;

/**
 * 安全服务接口。
 **/
public interface SecurityService extends SecurityBeans {

    SecurityUser getSecurityUser(String username);

    List<AuthorityNode> getUserAuthorities(Long userId);

    List<PermissionNode> getUserPermissions(Long userId);

}
