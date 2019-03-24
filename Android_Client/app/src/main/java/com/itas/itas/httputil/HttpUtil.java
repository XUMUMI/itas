package com.itas.itas.httputil;
import android.content.Context;
import android.util.Log;


import com.itas.itas.mac.*;
import com.itas.itas.itas_gson.*;
import com.google.gson.Gson;
import com.itas.itas.okhttp.*;
import com.itas.itas.RSA.*;


import java.util.HashMap;

import javax.crypto.Cipher;

import okhttp3.Call;

public class HttpUtil {
        private static MacStatus macstatus = new MacStatus();
        private static LoginStatus loginstatus = new LoginStatus();
        private static SignStatus signstatus = new SignStatus();
        private static String mac ="";
        private static  Gson gson = new Gson();
        private static final String  publicKey= "";

        /**
         * 获取MAC状态，判断是否已注册
         */
        public static MacStatus getFindMACstatus() {
        return macstatus;
    }
        /**
         * @description: 获取手机mca地址赋值给mac
         * @param context 传入context，一般为MainActivity
         * @return 返回mac地址
         * @throws  MacFormatException
         * @author Teoan
         * @date 2019/3/18 16:56
         */
        private static void setMac(Context context){
            itas_Mac myMac=new itas_Mac();
            try {
                MacAddress MacAddress = new MacAddress(myMac.getMac(context));
                mac = MacAddress.getCont();
                Log.e("MAC",mac);
            } catch (MacFormatException e) {
                Log.e("MAC","MAC获取失败！");
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
         * @param  context 传入context，一般为MainActivity
         * @return 返回一个macStauts类，已注册macstatus.getStauts()返回1,macstatus.getUsarName()返回 "用户名";未注册macstatus.getStauts()返回0,macstatus.getUsarName()返回返回"";
         * 网络请求超时macstatus.getStauts()返回2,macstatus.getUsarName()返回 ""
         *
         */
        public static void judgeMAC(String url , Context context,HttpStatusLister lister){
            if (mac.equals("")) setMac(context);
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("mac",mac);
            hashMap.put("reqs","ident");
            OkhttpUtil.okHttpPost(url, hashMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    macstatus.setStatus(2);
                }

                @Override
                public void onResponse(String response) {
                    if(response!=null){
                        Log.e("http",response);
                        if(response.isEmpty()){
                            Log.e("error","Date is null");
                        }else{
                            try {
                                response = RSA.publicCrypt (Cipher.DECRYPT_MODE, response, publicKey);
                            } catch (Exception e) {
                                Log.e("RSA","decrypt error!");
                                e.printStackTrace();
                            }
                            macstatus = gson.fromJson(response, MacStatus.class);
                            lister.onGetMacStatus(macstatus);
                        }
                    }
                    else
                        Log.e("error","Date is null");
                }
            });
        }

        /**
         * @description: 登陆函数  加密后发送给服务器
         * @param url 服务器地址
         * @param passwd 密码
         * @return 成功登录loginstatus.getStatus()返回1;失败loginstatus.getStatus()返回0;超时loginstatus.getStatus()返回2
         * @throws
         * @author Teoan
         * @date 2019/3/17 21:55
         */
        public static void login(String url, String passwd,HttpStatusLister lister){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("reqs","signIn");
            hashMap.put("mac",mac);
            try {
                passwd = RSA.publicCrypt (Cipher.ENCRYPT_MODE, passwd, publicKey);
            } catch (Exception e) {
                Log.e("RSA","encrypt error!");
                e.printStackTrace();
            }
            hashMap.put("password",passwd);
            OkhttpUtil.okHttpPost(url, hashMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    loginstatus.setStatus(2);
                }

                @Override
                public void onResponse(String response) {
                    if(response!=null){
                        Log.e("http",response);
                        if(response.isEmpty()){
                            Log.e("error","Date is null");
                        }else{
                            loginstatus = gson.fromJson(response, LoginStatus.class);
                            lister.onGetLoginStatus(loginstatus);
                        }
                    }
                    else
                        Log.e("error","Date is null");
                }

            });
        }
        /**
         * @description 注册新用户
         * @param url 服务器地址
         * @param userName 用户名
         * @param passwd 密码
         * @return 成功登录signstauts.getStatus()返回1;失败signstauts.getStatus()返回0;连接超时signstauts.getStatus()返回2
         * @throws
         * @author Teoan
         * @date 2019/3/17 22:13
         */
        public static void sign(String url, String passwd, String userName,HttpStatusLister lister){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("reqs","signUp");
            hashMap.put("mac",mac);
            hashMap.put("password",passwd);
            hashMap.put("userName",userName);
            OkhttpUtil.okHttpPost(url, hashMap, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    signstatus.setStatus(2);
                }

                @Override
                public void onResponse(String response) {
                    if(response!=null){
                        Log.e("http",response);
                        if(response.isEmpty()){
                            Log.e("error","Date is null");
                        }else{
                            try {
                                response = RSA.publicCrypt (Cipher.DECRYPT_MODE, response, publicKey);
                            } catch (Exception e) {
                                Log.e("RSA","decrypt error!");
                                e.printStackTrace();
                            }
                            signstatus = gson.fromJson(response, SignStatus.class);
                            lister.onGetSignStatus(signstatus);
                        }
                    }
                    else
                        Log.e("error","Date is null");
                }

            });
        }
}
