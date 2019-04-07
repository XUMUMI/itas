package Inquiry;

import DataBase.SQL;

import java.sql.SQLException;

public class ViaUserName {
    private int status = StatusCode.UNAVAILABLE.getCode();
    public  ViaUserName(String userName){
        try {
            SQL sql = new SQL();
            if(!sql.checkUserName(userName)) status = StatusCode.AVAILABLE.getCode();
            sql.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
