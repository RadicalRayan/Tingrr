package com.example.rayan.tingrr_1;

import android.graphics.drawable.Drawable;

/**
 * Created by Rayan Krishnan on 3/5/2018.
 */

public class CardObject {
    private String dogName;
    private String dogDes;
    private Drawable image;

    CardObject (String dogName, String dogDes, Drawable drawable){
        this.dogName = dogName;
        this.dogDes = dogDes;
        this.image = drawable;
    }

    public String getName() {
        return dogName;
    }

    public void setName(String name) {
        this.dogName = name;
    }

    public String getDes() {
        return dogDes;
    }

    public void setDes(String descrption) {
        this.dogDes = descrption;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
