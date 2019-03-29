package com.example.a27707.mycall.HttpUtil;
import android.util.Log;


import com.example.a27707.mycall.Mac.*;
import com.example.a27707.mycall.itas_Gson.*;
import com.google.gson.Gson;
import com.example.a27707.mycall.okhttp.*;

import RSA.*;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import javax.crypto.Cipher;

import okhttp3.Call;
import okhttp3.Response;

public class HttpUtil {
        private static MacStatus macstatus = new MacStatus();
        private static LoginStatus loginstatus = new LoginStatus();
        private static SignStatus signstatus = new SignStatus();
        private static String mac ="";
        private static  Gson gson = new Gson();
        private static final String  publicKey= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCYzfYWKbZ0JRfuykGP7F4g4q8Nv9aNMhmFTc16/DfI2AF0HJk3kaXuc/ULm1Ev3dedF4ifv41SnYaTqTC5xxES23kY8o9nHA7iBb4rIZ2j1ywWo7Q4AaFO4g8/krk1X3ktzSmjw0iRs7hdcYx/b16MFaLGHcOuIEUM1KAm76tUQIDAQAB";

        /**
         * 获取MAC状态，判断是否已注册
         */
        public static MacStatus getFindMACstatus() {
        return macstatus;
    }
        /**
         * @description: 获取手机mca地址赋值给mac
         * @return 返回mac地址
         * @throws  MacFormatException
         * @author Teoan
         * @date 2019/3/18 16:56
         */
        private static void getMac(){
            try {
               MacAddress MacAddress = new MacAddress(itas_Mac.getMac());
               mac = MacAddress.getCont();
                Log.d("MAC",mac);
            } catch (MacFormatException e) {
                Log.d("MAC","MAC获取失败！");
                e.printStackTrace();
            }
        }



        /**
         * 判断登录状态
         */
        public static LoginStatus getloginstatus() {
            return loginstatus;
        }

        /**
         * 判断获取到的MAC地址是否已注册
         * @param url 服务器地址
         * @return 返回一个macStauts类，已注册macstatus.getStauts()返回1,macstatus.getUsarName()返回 "用户名";未注册macstatus.getStauts()返回0,macstatus.getUsarName()返回返回"";
         * 网络请求超时macstatus.getStauts()返回2,macstatus.getUsarName()返回 "" 网络连接异常macstatus.getStauts()返回3,macstatus.getUsarName()返回 ""
         *
         */
        public static void judgeMAC(String url ,final HttpStatusLister lister){
            if (mac.equals("")) getMac();
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("mac",mac);
            hashMap.put("reqs","ident");
            OkhttpUtil.okHttpPost(url, hashMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    if(e instanceof SocketTimeoutException){//判断超时异常
                        Log.d("okhttp","连接超时");
                        macstatus.setStatus(2);
                    }
                    if(e instanceof ConnectException){
                        Log.d("okhttp","连接异常");
                        macstatus.setStatus(3);
                    }
                    lister.onGetMacStatus(macstatus);
                }

                @Override
                public void onResponse(String responseStr ,Response response) {
                    if( responseStr!=""&&response.code()==200){
                        Log.d("http", responseStr);
                            try {
                                responseStr = RSA.publicCrypt (Cipher.DECRYPT_MODE,  responseStr, publicKey);
                                Log.d("response", responseStr);
                            } catch (Exception e) {
                                Log.d("RSA","decrypt error!");
                                e.printStackTrace();
                            }
                            macstatus = gson.fromJson( responseStr, MacStatus.class);
                            lister.onGetMacStatus(macstatus);
                    }
                    else{
                        macstatus.setStatus(response.code());
                        Log.d("response", responseStr);
                        lister.onGetMacStatus(macstatus);
                    }
                }
            });
        }

        /**
         * @description: 登陆函数  加密后发送给服务器
         * @param url 服务器地址
         * @param password 密码
         * @return 成功登录loginstatus.getStatus()返回1;失败loginstatus.getStatus()返回0;超时loginstatus.getStatus()返回2 连接异常loginstatus.getStatus()返回3
         * @throws
         * @author Teoan
         * @date 2019/3/17 21:55
         */
        public static void login(String url, String password,HttpStatusLister lister){
            if (mac.equals("")) getMac();
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("reqs","signIn");
            hashMap.put("mac",mac);
            try {
                password = RSA.publicCrypt (Cipher.ENCRYPT_MODE, password, publicKey);
            } catch (Exception e) {
                Log.d("RSA","encrypt error!");
                e.printStackTrace();
            }
            hashMap.put("password",password);
            OkhttpUtil.okHttpPost(url, hashMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    if(e instanceof SocketTimeoutException){//判断超时异常
                        Log.d("okhttp","连接超时");
                        loginstatus.setStatus(2);
                    }
                    if(e instanceof ConnectException){
                        Log.d("okhttp","连接异常");
                        loginstatus.setStatus(3);
                    }
                    lister.onGetLoginStatus(loginstatus);
                }

                @Override
                public void onResponse(String responseStr, Response response) {
                    if (responseStr != "" && response.code() == 200) {
                        Log.d("http", responseStr);
                        try {
                            responseStr = RSA.publicCrypt(Cipher.DECRYPT_MODE, responseStr, publicKey);
                            Log.d("response", responseStr);
                        } catch (Exception e) {
                            Log.d("RSA", "decrypt error!");
                            e.printStackTrace();
                        }
                        loginstatus = gson.fromJson(responseStr, LoginStatus.class);
                        lister.onGetLoginStatus(loginstatus);
                    } else {
                        loginstatus.setStatus(response.code());
                        Log.d("response", responseStr);
                        lister.onGetLoginStatus(loginstatus);
                    }
                }

            });
        }
        /**
         * @description 注册新用户
         * @param url 服务器地址
         * @param userName 用户名
         * @param password 密码
         * @return 成功登录signstauts.getStatus()返回1;失败signstauts.getStatus()返回0;连接超时signstauts.getStatus()返回2 连接异常signstauts.getStatus()返回3
         * @throws
         * @author Teoan
         * @date 2019/3/17 22:13
         */
        public static void signUp(String url, String userName, String password ,HttpStatusLister lister){
            if (mac.equals("")) getMac();
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("reqs","signUp");
            hashMap.put("mac",mac);
            try {
                password = RSA.publicCrypt (Cipher.ENCRYPT_MODE, password, publicKey);
            } catch (Exception e) {
                Log.d("RSA","encrypt error!");
                e.printStackTrace();
            }
            hashMap.put("password",password);
            hashMap.put("userName",userName);
            OkhttpUtil.okHttpPost(url, hashMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    if(e instanceof SocketTimeoutException){//判断超时异常
                        Log.d("okhttp","连接超时");
                       signstatus.setStatus(2);
                    }
                    if(e instanceof ConnectException){
                        Log.d("okhttp","连接异常");
                       signstatus.setStatus(3);
                    }
                    lister.onGetSignStatus(signstatus);
                }

                @Override
                public void onResponse(String responseStr, Response response) {
                    if (responseStr != "" && response.code() == 200) {
                        Log.d("http", responseStr);
                        try {
                            responseStr = RSA.publicCrypt(Cipher.DECRYPT_MODE, responseStr, publicKey);
                            Log.d("response", responseStr);
                        } catch (Exception e) {
                            Log.d("RSA", "decrypt error!");
                            e.printStackTrace();
                        }
                        signstatus = gson.fromJson(responseStr, SignStatus.class);
                        lister.onGetSignStatus(signstatus);
                    } else {
                        signstatus.setStatus(response.code());
                        Log.d("response", responseStr);
                        lister.onGetSignStatus(signstatus);
                    }
                }
            });
        }
}
