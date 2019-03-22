import Log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * FileName APServerTest.java
 * For test APServer work
 * Run the APServer first
 * Check network connect
 * Check the configuration of the firewall
 *
 * @author  XUMUMI
 * @create  07/13/2018
 *
 *
 * 文件名  APServerTest.java
 * 用于测试 APServer 是否工作
 * 先运行 APServer
 * 检查网络连接
 * 检查防火墙设置
 *
 * @作者      XUMUMI
 * @创建时间  07/13/2018
 *
 **/

public class APServerTest {
    public static void main(String[] args) {
        try {
            /* test sock
             * 测试 sock */
            Socket socket = new Socket("localhost", 1331);

            /* send
             * 发送流 */
            PrintWriter send = new PrintWriter(socket.getOutputStream());

            /* test string
             * format: [MAC of AP Probe]|[MAC of Source STA]|[MAC of Receiver]|[Frame Type]|[Frame Type]|[Channel]|[RSSI]
             *
             * 测试数据
             * 格式: 「AP 探针 MAC」|「Frame 源 MAC」|「Frame 目的 MAC」|「Frame 大类」|「Frame 小类」|「信道」|「RSSI 信号强度」 */
            send.write("60:01:94:AA:98:A2|91:65:2D:49:EC:63|EC:10:9F:9A:1E:2F|02|08|05|-60");
            send.flush();
            socket.shutdownOutput();

            /* Receive
             * 接收服务器响应 */
            BufferedReader Receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String info = null;
            while((info = Receive.readLine()) != null)  System.out.println(info);

            /* close buff
             * 关闭 buff */
            send.close();
            Receive.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.err("Please run APServer first!");
        }
    }
}