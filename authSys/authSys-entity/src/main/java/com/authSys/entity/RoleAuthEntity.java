package com.authSys.entity;

public class RoleAuthEntity {
    private Integer id;
    private Integer roleId;
    private Integer authId;

    public RoleAuthEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    @Override
    public String toString() {
        return "RoleAuthEntity{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", authId=" + authId +
                '}';
    }
}
