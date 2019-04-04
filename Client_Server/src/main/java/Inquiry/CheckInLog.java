package Inquiry;

import DataBase.SQL;

import java.sql.SQLException;

public class CheckInLog {
    ActLog act = null;
    public CheckInLog(String userName, String password){
        if(userName != null && password != null){
            try {
                SQL sql = new SQL();
                int uid = sql.getUID(userName, password);
                if(uid != 0){

                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

class ActLog{
    public int ID;
    public String log;
}

