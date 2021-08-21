package com.microwaveteam.quarantinecoffee.models;

import java.io.Serializable;

public class Order implements Serializable {
    private String table;
    private String productName;
    private String productType;
    private int amount;
    private long price;
    private Boolean isFinish;
    private String dateTime;
    private boolean isBill;
    private String key;

    public Order(String table, String productName, int amount, Boolean isFinish, String dateTime) {
        this.table = table;
        this.productName = productName;
        this.amount = amount;
        this.isFinish = isFinish;
        this.dateTime = dateTime;

    }

    public Order() {
    }


    public String getProductName() { return productName;    }

    public Order(String name) {
        productName = name;
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


    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Boolean getFinish() {
        return isFinish;
    }

    public void setFinish(Boolean finish) {
        isFinish = finish;
    }

    public boolean isBill() {
        return isBill;
    }

    public void setBill(boolean bill) {
        isBill = bill;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
