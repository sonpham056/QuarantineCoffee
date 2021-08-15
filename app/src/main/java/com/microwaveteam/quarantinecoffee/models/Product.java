package com.microwaveteam.quarantinecoffee.models;

public class Product {
    private String productName;
    private long price;
    private int amount;
    private String category;
    private String image;
    private boolean isDone;

    public Product(String productName, long price, int amount, String category) {
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.category = category;
    }

    public Product() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}

