package ewing.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ewing.application.common.TreeNode;

import java.util.List;

/**
 * 许可权限树节点。
 * 该对象需要缓存和序列化，尽量保持结构稳定。
 *
 * @author 权限树节点。
 */
public class PermissionNode implements TreeNode<PermissionNode, Long> {

    private Long permissionId;

    private Long parentId;

    private String name;

    private String code;

    private String type;

    private String target;

    private List<PermissionNode> children;

    @Override
    public Long getId() {
        return permissionId;
    }

    @Override
    public Long getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public List<PermissionNode> getChildren() {
        return children;
    }

    @Override
    public void setChildren(List<PermissionNode> children) {
        this.children = children;
    }

    @JsonIgnore
    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
