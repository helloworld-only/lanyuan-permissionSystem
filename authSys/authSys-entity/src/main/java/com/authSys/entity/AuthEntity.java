package com.authSys.entity;

public class AuthEntity {
    private Integer authId;
    private String authName;

    public AuthEntity() {
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    @Override
    public String toString() {
        return "AuthEntity{" +
                "id=" + authId +
                ", authName='" + authName + '\'' +
                '}';
    }
}
