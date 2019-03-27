package com.example.a27707.mycall.HttpUtil;

import com.example.a27707.mycall.itas_Gson.*;

/**
 * @author Teoan
 * @title: HttpStatusLister
 * @projectName MyCall
 * @description: http状态监听器
 * @date 2019/3/1916:50
 */
public interface HttpStatusLister {
    /**
     * 当成功获取到MacStatus类时
     */
    void onGetMacStatus(MacStatus macStatus);

    /**
     * 当成功获取到LoginStatus类时
     */
    void onGetLoginStatus(LoginStatus loginStatus);


    /**
     * 当成功获取到SignStatus类时
     */
    void onGetSignStatus(SignStatus signStatus);





}
