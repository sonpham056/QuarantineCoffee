package com.microwaveteam.quarantinecoffee.Helper;

public class FeatureHelper {
    int image, image1, image2;
    String  userName;

    String productName, amount;
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

    public FeatureHelper(int image1, int image2, String productName,String amount){
        this.image1 = image1;
        this.image2 = image2;
        this.productName = productName;
        this.amount = amount;
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

    public void setUserName(String username) {
        this.userName = username;
    }
}
