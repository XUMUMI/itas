package DataBase;

import MacAddress.MacAddress;
import SQL_Ctrl.SQL_Ctrl;

import java.sql.SQLException;
import java.util.Map;

public class SQL {
    /* JDBC driver */
    private static SQL_Ctrl jdbc;
    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String URL = "jdbc:mariadb://192.168.123.90:3306/itas";
    private static final String USER = "itas";
    private static final String PASSWORD = "12F05054D059E041DAAC75FA45F45C044E057E5E";

    public SQL() throws SQLException, ClassNotFoundException {
        jdbc = new SQL_Ctrl(DRIVER, URL, USER, PASSWORD);
    }

    public String getUserName(MacAddress macAddress) throws SQLException {
        return jdbc.select(
                "T_USER_INFO",
                "USER_NAME",
                "WHERE MAC = '" + macAddress.getCont() + "'");
    }

    public String signIn(MacAddress macAddress, String password) throws SQLException {
        return jdbc.select(
                "T_USER_INFO",
                "USER_NAME",
                "WHERE MAC = '" + macAddress.getCont() + "' "
                        + "AND `PASSWORD` = PASSWORD('" + password + "')");
    }

    public boolean signUp(MacAddress macAddress, String userName, String password) throws SQLException {
        boolean ret = false;
        if(!checkUserName(userName)){
            password = jdbc.select("PASSWORD('" + password + "')");
            Map<String, Object> info = Map.of(
                    "USER_NAME", String.format("'%s'", userName),
                    "MAC", String.format("'%s'", macAddress.getCont()),
                    "PASSWORD", String.format("'%s'", password)
            );
            jdbc.add("T_USER_INFO", info);
            ret = checkUserName(userName);
        }
        return ret;
    }

    public boolean checkUserName(String userName) throws SQLException {
        return (
                jdbc.select(
                        "T_USER_INFO",
                        "USER_NAME",
                        "WHERE USER_NAME = '" + userName + "'")
                        != null
        );
    }

    public void close(){
        jdbc.close();
    }
}