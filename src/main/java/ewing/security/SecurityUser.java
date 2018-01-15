package ewing.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Security 用户。
 * 该对象需要缓存和序列化，尽量保持结构稳定。
 *
 * @author Ewing
 */
public class SecurityUser implements UserDetails {

    private Long userId;

    private String username;

    private String nickname;

    private String password;

    /**
     * 功能点权限。
     */
    private List<AuthorityNode> authorities;

    /**
     * 数据许可权限。
     */
    private List<PermissionNode> permissions;

    /**
     * 注解中hasRole表达式会调用该方法。
     */
    @Override
    public List<AuthorityNode> getAuthorities() {
        return authorities;
    }

    /**
     * Authority相当于角色。
     */
    public void setAuthorities(List<AuthorityNode> authorities) {
        this.authorities = authorities;
    }

    @JsonIgnore
    public List<PermissionNode> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionNode> permissions) {
        this.permissions = permissions;
    }

    /**
     * 判断用户是否有对应的权限编码。
     */
    public boolean hasPermission(String code) {
        return getPermissionByCode(code) != null;
    }

    /**
     * 根据权限编码获取用户权限。
     */
    public PermissionNode getPermissionByCode(String code) {
        for (PermissionNode permission : permissions) {
            if (permission.getCode().equals(code)) {
                return permission;
            }
        }
        return null;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
