package Inquiry;

import DataBase.SQL;

import java.sql.*;
import java.util.ArrayList;

public class CheckInLog {
    private int status = StatusCode.UNKNOWN_ERROR.getCode();
    private ArrayList<ActLog> actLog = new ArrayList<>();
    public CheckInLog(String userName, String password){
        if(userName != null && password != null){
            try {
                SQL sql = new SQL();
                int UID = sql.getUID(userName, password);
                if(UID != 0){
                    ResultSet resultSet = sql.getActLog(UID);
                    while (resultSet.next()){
                        actLog.add(new ActLog(
                                resultSet.getInt("ACTIVITY_ID"),
                                resultSet.getString("CHECK_IN"),
                                resultSet.getTimestamp("TIME")
                        ));}}
                status = StatusCode.SUCCESS.getCode();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    static class ActLog{
        private int ID;
        private String log;
        private long time;

        ActLog(int ID, String log, Timestamp time){
            this.ID = ID;
            this.log = log;
            this.time = time != null ? time.getTime() : 0;
        }
    }
}


