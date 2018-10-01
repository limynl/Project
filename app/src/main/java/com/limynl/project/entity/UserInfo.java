package com.limynl.project.entity;

/**
 * Created by Lmy on 2017/10/31.
 * email 1434117404@qq.com
 */

public class UserInfo {
    private String phone;
    private int userId;

    public UserInfo() {
    }

    public UserInfo(String phone, int userId) {
        this.phone = phone;
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
