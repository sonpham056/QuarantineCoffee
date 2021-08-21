package com.microwaveteam.quarantinecoffee.models;

public class Bill {
    private String date;
    private long sum;
    private String waiterName;

    public Bill() {
    }

    public Bill(String date, long sum, String waiterName) {
        this.date = date;
        this.sum = sum;
        this.waiterName = waiterName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public String getWaiterName() {
        return waiterName;
    }

    public void setWaiterName(String waiterName) {
        this.waiterName = waiterName;
    }
}
