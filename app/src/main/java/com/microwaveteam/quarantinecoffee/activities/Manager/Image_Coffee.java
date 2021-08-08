package com.microwaveteam.quarantinecoffee.activities.Manager;

public class Image_Coffee {
    private String Name;
    private int Image;

    public Image_Coffee(String name, int image){
            Name = name;
            Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage (int image) {
        Image = image;
    }
}
