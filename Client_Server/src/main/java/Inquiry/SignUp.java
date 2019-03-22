package Inquiry;

import DataBase.SQL;
import MacAddress.*;

import java.sql.SQLException;

public class SignUp {
    public int status = SignCode.FILD.getCode();
    public SignUp(String macAddress, String userName, String password){
        try {
            SQL sql = new SQL();
            if(sql.signUp(new MacAddress(macAddress), userName, password)) status = SignCode.SUCCESS.getCode();
            sql.close();
        } catch (SQLException | ClassNotFoundException | MacFormatException e) {
            e.printStackTrace();
        }
    }
}
