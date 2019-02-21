package com.tz_tech.module.common.model;

import java.io.Serializable;

public class UserRoleDto implements Serializable {
    //用户名
    private String userName;

    //角色ID
    private Integer roleId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRoleDto{" +
                "userName='" + userName + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
