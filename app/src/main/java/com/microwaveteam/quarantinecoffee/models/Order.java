package com.microwaveteam.quarantinecoffee.models;

public class Order {
    private String productName;
    private int amount;
    private Boolean isFinish;


    public Order(String _productName, int _amount, Boolean _isFinish) {
        productName = _productName;
        amount = _amount;
        isFinish = _isFinish;
    }

    public Order() {
    }

    public String getProductName() { return productName;    }

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
}
