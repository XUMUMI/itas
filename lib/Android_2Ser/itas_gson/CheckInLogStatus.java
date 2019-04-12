package com.example.a27707.mycall.itas_Gson;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Teoan
 * @title: CheckInLogStatus
 * @projectName MyCall
 * @description: 描述
 * @date 2019/4/714:59
 */
public class CheckInLogStatus implements Serializable {
    private int status=-1;
    private ArrayList<CheckInLog> actLog = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setActLogs(ArrayList<CheckInLog> actLogs) {
        this.actLog = actLogs;
    }

    public ArrayList<CheckInLog> getActLogs() {
        return actLog;
    }

    public static class CheckInLog {
        /**
         * ID : 2
         * log : 1
         * time : 1554382871000
         */

        private int ID;
        private int log;
        private long time;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getLog() {
            return log;
        }

        public void setLog(int log) {
            this.log = log;
        }

        public String getTime(String pattern) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(new Date(time));
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}
