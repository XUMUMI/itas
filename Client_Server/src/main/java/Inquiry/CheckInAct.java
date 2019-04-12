package Inquiry;

import DataBase.SQL;

import java.sql.*;
import java.util.ArrayList;

public class CheckInAct {
    private int status = StatusCode.UNKNOWN_ERROR.getCode();
    private ArrayList<Activity> act = new ArrayList<>();
    public CheckInAct(String userName, String password){
        if(userName != null && password != null){
            try {
                SQL sql = new SQL();
                int UID = sql.getUID(userName, password);
                if(UID != 0){
                    ResultSet resultSet = sql.getAct(UID);
                    if(resultSet != null){
                        while (resultSet.next()){
                            act.add(new Activity(
                                    resultSet.getInt      ("ID"),
                                    resultSet.getString   ("NAME"),
                                    resultSet.getString   ("LOCATION"),
                                    resultSet.getDate     ("START_DATE"),
                                    resultSet.getDate     ("END_DATE"),
                                    resultSet.getTimestamp("START_TIME"),
                                    resultSet.getTimestamp("END_TIME"),
                                    resultSet.getInt      ("REPEAT")
                            ));}
                        status = StatusCode.SUCCESS.getCode();
                    } else  status = StatusCode.FIELD.getCode();
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    static class Activity{
        private int    ID;
        private String name;
        private String location;
        private long   startDate;
        private long   endDate;
        private long   startTime;
        private long   endTime;
        private int    repeat;

        Activity(
                int       ID,
                String    name,
                String    location,
                Date      startDate,
                Date      endDate,
                Timestamp startTime,
                Timestamp endTime,
                int       repeat
        ){
            this.ID        = ID;
            this.name      = name;
            this.location  = location;
            this.startDate = startDate != null ? startDate.getTime() : 0;
            this.endDate   = endDate   != null ? endDate.getTime()   : 0;
            this.startTime = startTime != null ? startTime.getTime() : 0;
            this.endTime   = endTime   != null ? endTime.getTime()   : 0;
            this.repeat    = repeat;
        }
    }
}


