package com.microwaveteam.quarantinecoffee.models;

public class Order {
    private String table;
    private String productName;
    private String amount;
    private Boolean isFinish;
    private String dateTime;

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Order(String table, String productName, String amount, Boolean isFinish, String dateTime) {
        this.table = table;
        this.productName = productName;
        this.amount = amount;
        this.isFinish = isFinish;
        this.dateTime = dateTime;
    }

    public Order() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Boolean getFinish() {
        return isFinish;
    }

    public void setFinish(Boolean finish) {
        isFinish = finish;
    }
}
