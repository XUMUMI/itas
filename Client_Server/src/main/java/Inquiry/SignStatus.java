package Inquiry;

import java.sql.SQLException;

import DataBase.SQL;
import MacAddress.*;

public class SignStatus {
    public int status = SignCode.SIGN_UP.getCode();
    public String userName = null;
    public SignStatus(String macAddress){
        try {
            SQL sql = new SQL();
            this.userName = sql.getUserName(new MacAddress(macAddress));
            if(userName != null) this.status = SignCode.SIGN_IN.getCode();
            sql.close();
        } catch (SQLException | ClassNotFoundException | MacFormatException e) {
            e.printStackTrace();
        }
    }
}

