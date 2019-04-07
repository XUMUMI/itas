package Inquiry;

import DataBase.SQL;
import MacAddress.*;

import java.sql.SQLException;

public class SignUp {
    private int status = StatusCode.FIELD.getCode();
    public SignUp(String macAddress, String userName, String password){
        try {
            SQL sql = new SQL();
            if(sql.signUp(new MacAddress(macAddress), userName, password)) status = StatusCode.SUCCESS.getCode();
            sql.close();
        } catch (SQLException | ClassNotFoundException | MacFormatException e) {
            e.printStackTrace();
        }
    }
}
