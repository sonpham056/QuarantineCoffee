package com.microwaveteam.quarantinecoffee.models;

public class TimeKeeper {
    private String userName;

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String timeStart, timeEnd;

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public TimeKeeper(String userName, String timeStart, String timeEnd) {
        this.userName = userName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public TimeKeeper() {
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
