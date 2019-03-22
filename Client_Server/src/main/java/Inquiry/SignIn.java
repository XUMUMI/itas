package Inquiry;

import DataBase.SQL;
import MacAddress.*;

import java.sql.SQLException;

public class SignIn {
    public int status = SignCode.FILD.getCode();
    public SignIn(String macAddress, String password){
        try {
            SQL sql = new SQL();
            if(sql.signIn(new MacAddress(macAddress), password) != null) status = SignCode.SUCCESS.getCode();
            sql.close();
        } catch (SQLException | ClassNotFoundException | MacFormatException e) {
            e.printStackTrace();
        }
    }
}
