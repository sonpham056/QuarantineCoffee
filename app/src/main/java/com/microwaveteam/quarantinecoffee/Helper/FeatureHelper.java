package com.microwaveteam.quarantinecoffee.Helper;

public class FeatureHelper {
    int image, image1, image2;
    String  username;

    public FeatureHelper(int image, int image1, int image2, String username) {
        this.image = image;
        this.image1 = image1;
        this.image2 = image2;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
