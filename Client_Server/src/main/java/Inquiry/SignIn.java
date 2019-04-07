package Inquiry;

import DataBase.SQL;
import MacAddress.*;

import java.sql.SQLException;

public class SignIn {
    private int status = StatusCode.FIELD.getCode();
    public SignIn(String macAddress, String password){
        try {
            SQL sql = new SQL();
            if(sql.signIn(new MacAddress(macAddress), password) != null) status = StatusCode.SUCCESS.getCode();
            sql.close();
        } catch (SQLException | ClassNotFoundException | MacFormatException e) {
            e.printStackTrace();
        }
    }
}
