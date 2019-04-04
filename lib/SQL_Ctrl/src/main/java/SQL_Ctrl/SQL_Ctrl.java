/*
 * FileName: SQL_Ctrl.java
 * Implement the operation of the database
 *
 * @author Teoan
 * @create 07/19/2018
 * @editer XUMUMI
 * @edited 07/21/2018
 *
 *
 * 文件名: SQL_Ctrl.java
 * 对 数据库 的增删改查操作进行包装
 *
 * @作者      Teoan
 * @创建时间  07/19/2018
 * @编辑      XUMUMI
 * @编辑时间  04/04/2019
 */

package SQL_Ctrl;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class SQL_Ctrl {
    private Connection conn;
    private Statement stmt = null;
    private ResultSet rs = null;
    public SQL_Ctrl(String driver, String url, String usr, String passwd)
            throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, usr, passwd);
    }

    /* close Connection & ResultSet
     * 用于关闭 Connection 和 ResultSet */
    public void close(){
        try {
            if(conn != null) conn.close();
            if(stmt != null) stmt.close();
            if(rs != null) rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /* insert new records in a table
     * 在表中插入新记录 */
    public boolean add(String tableName, Map<String, Object> map) throws SQLException {
        StringBuilder insertSql = new StringBuilder("INSERT INTO " + tableName + "(");
        StringBuilder values = new StringBuilder("VALUES(");

        for(Map.Entry<String, Object> entry : map.entrySet()) {
            insertSql.append("`").append(entry.getKey()).append("`, ");
            values.append(entry.getValue()).append(", ");
        }

        /* remove the last ", "
         * 移除最后一个「, 」*/
        insertSql.setLength(insertSql.length() - 2);
        values.setLength(values.length() - 2);

        insertSql.append(") ");
        values.append(");");

        return conn.createStatement().execute(insertSql.toString() + values);
    }

    /* select data from a table & return ResultSet
     * 从表中获取记录并返回结果集 */
     public ResultSet select(String tableName, ArrayList<String> list, String condition) throws SQLException {
        StringBuilder selectSql = new StringBuilder("SELECT ");
        for(String str: list){
            selectSql.append(str).append(", ");
        }

        selectSql.setLength(selectSql.length() - 2);

        selectSql.append(" FROM ").append(tableName).append(" ").append(condition);
        return conn.createStatement().executeQuery(selectSql.toString());
    }

    /* select data from a table & return String
     * 从表中获取记录并返回字符串 */
    public String select(String tableName, String str, String condition) throws SQLException{
        String ret = null;
        final String selectSql = String.format("SELECT %s FROM %s %s", str, tableName, condition);

        rs = conn.createStatement().executeQuery(selectSql);
        if(rs.next()) ret = rs.getString(1);

        /* close ResultSet
         * 关闭 ResultSet */
        rs.close();
        rs = null;

        return ret;
    }

    /* Calling SQL function
     * 调用 SQL 函数 */
    public String select(String fun) throws SQLException{
        String ret = null;
        final String selectSql = String.format("SELECT %s", fun);

        rs = conn.createStatement().executeQuery(selectSql);
        if(rs.next()) ret = rs.getString(1);

        /* close ResultSet
         * 关闭 ResultSet */
        rs.close();
        rs = null;

        return ret;
    }

    /* modify the existing records in a table
     * 修改表中的记录 */
    public boolean update(String tableName, Map<String, Object> map, String condition) throws SQLException {
        StringBuilder updateSql = new StringBuilder("UPDATE " + tableName + " SET ");
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            updateSql.append("`").append(entry.getKey()).append("` = ").append(entry.getValue()).append(", ");
        }

        updateSql.setLength(updateSql.length() - 2);
        return conn.createStatement().execute(String.format("%s %s", updateSql, condition));
    }

    public static void main(String[] args){
        ResultSet rs = null;
        try {
            SQL_Ctrl sql_ctrl=new SQL_Ctrl("org.mariadb.jdbc.Driver","jdbc:mysql://192.168.123.90/itas",
                    "itas","12F05054D059E041DAAC75FA45F45C044E057E5E");
            System.out.println(sql_ctrl.select("PASSWORD('test')"));
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } 
    }

}
