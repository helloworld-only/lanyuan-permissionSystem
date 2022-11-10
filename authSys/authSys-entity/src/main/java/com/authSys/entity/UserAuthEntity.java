package com.authSys.entity;

public class UserAuthEntity {
    private Integer userId;
    private String authName;

    public UserAuthEntity() {
    }

    @Override
    public String toString() {
        return "UserAuthEntity{" +
                "userId=" + userId +
                ", authName='" + authName + '\'' +
                '}';
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }
}
