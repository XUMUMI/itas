package com.itas.itas.itas_gson;

/**
 * @author Teoan
 * @title: MacStatus
 * @projectName Android_Client
 * @description: Gson解析类
 * @date 2019/3/1816:18
 */
public class MacStatus {

    /**
     * status : 0
     * userName : Teoan-Xiaomi2
     */

    private int status =1;
    private String userName;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
