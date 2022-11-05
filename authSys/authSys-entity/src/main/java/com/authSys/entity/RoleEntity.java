package com.authSys.entity;

public class RoleEntity {

    private Integer roleId;
    private String roleName;

    public RoleEntity() {
    }

    @Override
    public String toString() {
        return "RoleEntity{" +
                "id=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
