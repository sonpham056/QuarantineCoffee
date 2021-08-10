package com.microwaveteam.quarantinecoffee.models;

public class Promotion {
    private String promotionName;
    private int promotion;

    public Promotion(){

    }

    public Promotion(String promotionName, int promotion) {
        this.promotionName = promotionName;
        this.promotion = promotion;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return promotionName + '\t' + promotion;
    }
}
