package com.example.asus_cp.dongmanbuy.model;

/**
 * Created by asus-cp on 2016-05-20.
 */
public class AboutMeModel {
    private int img;
    private String name;

    public AboutMeModel(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public AboutMeModel() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
