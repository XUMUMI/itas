package Inquiry;

import java.sql.SQLException;

import DataBase.SQL;
import MacAddress.*;

public class SignStatus {
    private int status = StatusCode.SIGN_UP.getCode();
    private String userName = null;
    public SignStatus(String macAddress){
        try {
            SQL sql = new SQL();
            this.userName = sql.getUserName(new MacAddress(macAddress));
            if(userName != null) this.status = StatusCode.SIGN_IN.getCode();
            sql.close();
        } catch (SQLException | ClassNotFoundException | MacFormatException e) {
            e.printStackTrace();
            status = StatusCode.UNKNOWN_ERROR.getCode();
        }
    }
}

