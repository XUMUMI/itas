package com.itas.itas.tcp;

import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * author:Teoan
 * date: On 2018/10/9
 */
public class itas_Tcp {
    /**
     * 服务器Socket对象
     */
    private Socket socket = null;
    /**
     * 端口
     */
    public static int PORT = 5000;
    public static String HOST = "127.0.0.0";
    receiveMsgTask receiveMsgTask = null;
    TcpLister lister;

    /**
     * 读写Buffer
     */
    private BufferedReader reader;
   // BufferedWriter writer;
    private PrintWriter writer;
    private String massage;

    /**
     * 构造tcp对象，传入参数
     * @param HOST：ip地址
     * @param PORT：对应地址的端口号
     * @param lister：回调的接口对象，用于对不同连接状态的处理
     */

    public itas_Tcp(String HOST, int PORT,TcpLister lister){
        this.HOST = HOST;
        this.PORT = PORT;
        this.lister=lister;
    }


    public class receiveMsgTask extends AsyncTask<Void,String,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {   //在子线程中运行
            Looper.prepare();
            if (socket == null)
            {
                lister.onConnectFailed();
            }
            else {
                try {
                    if (socket.isInputShutdown())
                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                    while (!isCancelled()) {
                        if (socket.isConnected() && !socket.isInputShutdown()) {
                            while ((massage = reader.readLine()) != null) {
                                publishProgress(massage);
                                Log.e("socket",  "收到："+massage);   //收到消息
                                if (massage.equals("exit"))   //结束标志
                                {
                                    publishProgress("socket close!");
                                }
                            }
                        }
                        socket.sendUrgentData(0xFF);
                    }
                } catch (IOException e) {
                    publishProgress("socket close!");
                    Log.e("socket",  "已断开连接！");   //断开连接
                    closeSocket();
                    e.printStackTrace();
                    return false;
                }
                Looper.loop();
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if(values[0].equals("socket close!"))
            {
                lister.onTcpColosed();
                closeSocket();
            }
            else
            {
                lister.onReceive(values[0]);
            }

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                closeSocket();
            }

        }
    }

    /**
     * 根据对应tcp端口和地址，创建连接Socket。
     */
    public void createSocket() {
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                try {
                    if(socket == null||socket.isClosed())
                    {
                        socket = new Socket(HOST,PORT);
                        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                socket.getOutputStream())), true);
                        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                        receiveMsgTask = new receiveMsgTask();
                        receiveMsgTask.execute();
                        lister.onConnectSuccess();
                        Log.e("socket","连接成功！");
                    }
                } catch (IOException e) {
                    lister.onConnectFailed();
                    Log.e("socket","连接失败！");
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }.start();
    }


    /**
     * 向对应tcp地址发送数据，必须在createSocket()执行后调用
     */
    public boolean sendMsg(final String msg) {
        final boolean[] flag = {false};
        new Thread(){
            @Override
            public void run() {
                if (socket.isOutputShutdown()) {
                    try {
                        writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                                socket.getOutputStream())), true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
//            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                if(socket.isConnected()&&!socket.isOutputShutdown()){
                    Looper.prepare();
                    writer.println(msg);
                    flag[0] = true;
                    lister.sendSuccess();
                    Looper.loop();
                }
            }
        }.start();
        return flag[0];

    }

    /**
     * 关闭Socket连接
     */
   public void closeSocket()  {
        if(socket!=null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(reader!=null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(writer!=null)
            writer.close();
        if(receiveMsgTask!=null)
            receiveMsgTask.cancel(true);

    }
}
