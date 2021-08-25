package com.microwaveteam.quarantinecoffee.Helper;

public class FeatureHelper {
    int image, image1, image2;
    String  userName,key, productType;

    String productName, amount, table,time;
    public FeatureHelper(int image, int image1, int image2, String username) {
        this.image = image;
        this.image1 = image1;
        this.image2 = image2;
        this.userName = username;
    }

    public FeatureHelper(int image1, int image2, String userName){
        this.image1 = image1;
        this.image2 = image2;
        this.userName = userName;
    }
    public FeatureHelper(int image2,String table,String name,String amount,String productType,String key){
        this.image2 = image2;
        this.table = table;
        this.productName = name;
        this.amount = amount;
        this.key = key;
        this.productType = productType;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public String getUserName() {
        return userName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUserName(String username) {
        this.userName = username;
    }
}
