package com.authSys.entity;

public class UserEntity {
    private Integer userId;
    private String acct;
    private String passwd;
    private String userName;

    public UserEntity() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAcct() {
        return acct;
    }

    public void setAcct(String acct) {
        this.acct = acct;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", acct='" + acct + '\'' +
                ", passwd='" + passwd + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
