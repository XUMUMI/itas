/**
 * FileName APServer,java
 * This class implements AP server of itas.
 * Multi-threaded listening port
 *
 * @author  XUMUMI
 * @create  07/13/2018
 *
 * 文件名      APServer,java
 * 此类实现 itas 的 AP 服务器, 负责整体把控及网络服务调用
 * 多线程监听端口
 *
 * @作者      XUMUMI
 * @创建时间  07/13/2018
 *
 **/

import Log.Log;
import MacAddress.MacFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class APServer {


    public static void main(String[] args) {
        try {
            /* init db connection
             * 初始化数据库连接 */
            W2db w2db = new W2db();

            /* setup socket
             * 配置 socket */
            ServerSocket serverSocket;
            
            /* set port (const int)
             * 设置端口号 */
            final int PORT = 1331;
            
            serverSocket = new ServerSocket(PORT);
            Log.sta("Waiting...");

            do {
                /* get info from client
                   获取客户端信息 */
                Socket socket = serverSocket.accept();
                /* start new thread
                 * 新建线程 */
                new serThr(socket, w2db).start();
                /* echo log
                 * 输出日志 */
                Log.sta("IP = " + socket.getInetAddress().getHostAddress());
            } while (true);

        } catch (IOException e) {
//            e.printStackTrace();
            Log.err("Server socket ERROR!");

        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
            Log.err("Database ERROR!");

        } finally {
            W2db.close();
        }
    }
}

class serThr extends Thread {
    private Socket socket;
    private W2db w2db;

    /* init
     * 初始化 (构造函数) */
    serThr(Socket socket, W2db w2db) {
        this.socket = socket;
        this.w2db = w2db;
    }

    public void run() {
        try {
            PrintWriter send = null;

            /* receive content
             * 接收 content */
            BufferedReader receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String info;
            while ((info = receive.readLine()) != null) {
                send = new PrintWriter(socket.getOutputStream());
                send.write(w2db.apSig(info));

                /* flush buff
                 * 清空 buff */
                send.flush();
            }

            /* close socket & buff
             * 关闭 socket 和 流 */
            socket.shutdownInput();
            socket.close();
            if(send != null) send.close();
            receive.close();
        } catch (java.net.SocketException e){
//            e.printStackTrace();
            Log.warn("Connection reset!");
        } catch (IOException e) {
//            e.printStackTrace();
            Log.err("Send info FAIL!");
        } catch (MacFormatException e){
//            e.printStackTrace();
            Log.err("MAC ERROR!");
        }
    }
}
