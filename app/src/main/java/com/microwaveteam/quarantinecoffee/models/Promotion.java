package com.microwaveteam.quarantinecoffee.models;

public class Promotion {
    private String promotionName;
    private int promotion;
    private String start;
    private String end;

    public Promotion(){

    }

    public Promotion(String promotionName, int promotion) {
        this.promotionName = promotionName;
        this.promotion = promotion;
    }

    public Promotion(String promotionName, int promotion, String start, String end) {
        this.promotionName = promotionName;
        this.promotion = promotion;
        this.start = start;
        this.end = end;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
