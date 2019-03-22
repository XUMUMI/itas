/**
 * FileName W2db.java
 * Update sig 2 db
 *
 * @author  XUMUMI
 * @create 07/13/2018
 *
 *
 * 文件名  W2db.java
 * 将信号强度写入数据库
 *
 * @作者  XUMUMI
 * @创建时间  07/13/2018
 *
 **/

import Log.Log;
import MacAddress.MacAddress;
import MacAddress.MacFormatException;
import SQL_Ctrl.SQL_Ctrl;

import java.sql.SQLException;
import java.util.Map;

class W2db {

    /* JDBC driver */
    private static SQL_Ctrl jdbc;
    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String URL = "jdbc:mariadb://192.168.123.90:3306/itas";
    private static final String USER = "itas";
    private static final String PASSWORD = "12F05054D059E041DAAC75FA45F45C044E057E5E";

    W2db() throws SQLException, ClassNotFoundException {
        jdbc = new SQL_Ctrl(DRIVER, URL, USER, PASSWORD);
    }

    /* get id
     * 获取 id */
    private static int getID(String table, String where) throws SQLException {
        int id = 0;
        String idStr = jdbc.select(table, "ID", "WHERE " + where);
        if(idStr != null) id = Integer.parseInt(idStr);
        return id;
    }

    /* get id by MAC
     * 通过 MAC 获取 id */
    private static int getID(String table, MacAddress MAC) throws SQLException {
        return getID(table, "`MAC` = '" + MAC.getCont() + "'");
    }

    /* get id by id
     * 通过 id 获取 id */
    private static int getID(int usrID, int apID) throws SQLException {
        return getID("T_USER_AP", "`AP_ID` = " + apID + " and `USER_ID` = " + usrID);
    }

    /* sent 2 db
     * 发送到数据库 */
    private static String send(MacAddress apMAC, MacAddress userMAC, String sig){

        String ret = "Unknown device or user: " + apMAC.getCont() + "|" + userMAC.getCont() + "|" + sig;

        try {

            /* find AP & user id
             * 查找探针及用户 ID */
            int apID = getID("T_AP_INFO", apMAC);
            int usrID = getID("T_USER_INFO", userMAC);

            /* AP & user found
             * AP 及用户均存在 */
            if ((apID & usrID) != 0){
                /* get user log
                 * 获取用户记录 */
                int logID = getID(usrID, apID);

                if (logID == 0){
                    /* insert
                     * 插入记录 */
                    Map<String, Object> log = Map.of(
                            "AP_ID", String.format("'%s'", apID),
                            "USER_ID", String.format("'%s'", usrID),
                            "LAST_TIME", "NOW()",
                            "SIG", String.format("'%s'", sig)
                    );
                    jdbc.add("T_USER_AP", log);
                }

                else {
                    /* update
                     * 更新记录 */
                    jdbc.update("T_USER_AP", Map.of("SIG", sig), "WHERE ID = " + logID);
                }

                /* update log
                 * 更新日志 */
                String usrName = jdbc.select(
                        "T_USER_INFO",
                        "USER_NAME",
                        "WHERE ID = " + usrID
                );
                ret = "Update: " + usrName + ": " + sig;

                /* debug log
                 * 调试日志 */
//                ret = "Update: " + apMAC.toUpperCase() + "|" + userMAC.toUpperCase() + "|" + sig;
                Log.sta(ret);
            }
        } catch (SQLException e) {
//            e.printStackTrace();
            Log.err("Database ERROR!");
        }
        /* ret = new Date() + "[client_log]: " + ret; */
        return ret;
    }

    String apSig(String str) throws MacFormatException {

        /* cut string by "|"
         * 按照「|」对字符串进行切割 */

        /* 60:01:94:AA:98:A2|91:65:2D:49:EC:63|EC:10:9F:9A:1E:2F|02|08|05|-68 */

        String arr[] = str.split("\\|");
        String ret = "Unable to parse string: \"" + str + '"';

        if (arr.length == 7) {
            MacAddress apMAC = new MacAddress(arr[0]);
            MacAddress userMAC = new MacAddress(arr[1]);
            String sig = arr[6];

            /* update log
             * 更新日志 */
            ret = send(apMAC, userMAC, sig);
        }
        else Log.err(ret);
        return ret;
    }

    static void close(){
        try {
            jdbc.close();
        }catch (NullPointerException ignored){
            Log.err("Close database connection FAIL!");
        }
    }
}
