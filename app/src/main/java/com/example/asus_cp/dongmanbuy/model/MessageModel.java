package com.example.asus_cp.dongmanbuy.model;

/**
 * 消息下拉列表的实体
 * Created by asus-cp on 2016-05-18.
 */
public class MessageModel {
    private int image;
    private String name;

    public MessageModel(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public MessageModel() {

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
