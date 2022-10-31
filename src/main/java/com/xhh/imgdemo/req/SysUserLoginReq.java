package com.xhh.imgdemo.req;

public class SysUserLoginReq {

    private String LoginName;

    private String password;

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SysUserEntity{" +
                ", LoginName='" + LoginName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
