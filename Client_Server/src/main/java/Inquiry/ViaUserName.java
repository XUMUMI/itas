package Inquiry;

import DataBase.SQL;

import java.sql.SQLException;

public class ViaUserName {
    public int status = SignCode.UNAVAILABLE.getCode();
    public  ViaUserName(String userName){
        try {
            SQL sql = new SQL();
            if(!sql.checkUserName(userName)) status = SignCode.AVAILABLE.getCode();
            sql.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
