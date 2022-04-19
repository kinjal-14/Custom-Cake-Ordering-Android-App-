package com.example.cakedream;

import java.io.Serializable;

public class productData {
    String calories;
    String desc;
    String id;
    String image;
    String name;
    float price;
    String size;
    String type;

    public productData(String calories, String desc, String id, String image, String name, float price, String size, String type) {
        this.calories = calories;
        this.desc = desc;
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.size = size;
        this.type = type;
    }
    public  productData() {}

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
