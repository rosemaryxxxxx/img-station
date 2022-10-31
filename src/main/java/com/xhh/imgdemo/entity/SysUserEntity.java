package com.xhh.imgdemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sys_user")
public class SysUserEntity {
    private Long id;

    private String LoginName;

    private String name;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "id=" + id +
                ", LoginName='" + LoginName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
