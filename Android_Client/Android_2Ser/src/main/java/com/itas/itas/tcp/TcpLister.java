package com.itas.itas.tcp;

/**
 * author:Teoan
 * date: On 2018/10/29
 */
public interface TcpLister {
    /**
     * 当收到消息时执行
     */
    void onReceive(String msg);


    /**
     * 当连接失败时执行
     */
    void onConnectFailed();


    /**
     * 当Tcp连接断开时执行
     */
    void onTcpColosed();

    /**
     * 当连接成功时执行
     */
    void onConnectSuccess();

    /**
     * 当发送成功时执行
     */
    void sendSuccess();
}
