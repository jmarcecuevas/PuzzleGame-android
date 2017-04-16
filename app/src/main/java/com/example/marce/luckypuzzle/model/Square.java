package com.example.marce.luckypuzzle.model;

import android.graphics.Bitmap;
import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by marce on 14/04/17.
 */

public class Square implements Serializable {
    private Bitmap picture;
    private int position;

    public Square(Bitmap picture,int position){
        this.picture=picture;
        this.position=position;
    }

    public int getPosition() {
        return position;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
