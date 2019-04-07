package com.example.a27707.mycall.itas_Gson;

import android.annotation.SuppressLint;
import android.provider.ContactsContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Teoan
 * @title: ActinfoStatus
 * @projectName MyCall
 * @description: 描述
 * @date 2019/4/521:41
 */
public class ActinfoStatus {
    private int status =-1;
    private List<ActInfo> act =null;
    private  HashMap<Integer,ActInfo> actInfoHashMap =new HashMap<>() ;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ActInfo> getAct() {
        return act;
    }

    public void setAct(List<ActInfo> act) {
        this.act = act;
    }

    public HashMap<Integer, ActInfo> getActInfoHashMap() {
        if (actInfoHashMap.isEmpty())
        {
            if (act !=null)
            {
                for(ActInfo actInfo: act){
                    actInfoHashMap.put(actInfo.getID(), actInfo);
                }
                return actInfoHashMap;
            }
            return null;

       }
        return actInfoHashMap;
    }

    public static class ActInfo {
        /**
         * ID : 2
         * name : test2
         * startDate : 0
         * endDate : 0
         * startTime : 10800000
         * endTime : 25200000
         * repeat : 0
         */

        private int ID;
        private String name;
        private long startDate;
        private long endDate;
        private long startTime;
        private long endTime;
        private long repeat;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        //获取各种格式时间 pattern ="yyyy-MM-dd hh:mm:ss"
        public String getStartDate(String pattern) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df=new SimpleDateFormat(pattern);
            return df.format(new Date(startDate));
        }

        public void setStartDate(int startDate) {
            this.startDate = startDate;
        }

        public String getEndDate(String pattern) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df=new SimpleDateFormat(pattern);
            return df.format(new Date(endDate));
        }

        public void setEndDate(int endDate) {
            this.endDate = endDate;
        }

        public String getStartTime(String pattern) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(new Date(startTime));
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public String getEndTime(String pattern) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(new Date(endTime)) ;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public long getRepeat() {
            return repeat;
        }

        public void setRepeat(long repeat) {
            this.repeat = repeat;
        }
    }
}
