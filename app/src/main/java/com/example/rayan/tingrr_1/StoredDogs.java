package com.example.rayan.tingrr_1;

import android.app.Application;

public class StoredDogs extends Application {
    private String[] storedDogs;

    public String[] getStoredDogs() {
        return storedDogs;
    }

    public void setStoredDogs(String[] dogList) {
        this.storedDogs = dogList;
    }
}
