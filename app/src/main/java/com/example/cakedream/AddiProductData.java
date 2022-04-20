package com.example.cakedream;

import java.io.Serializable;

public class AddiProductData implements Serializable {

    String desc;
    String id;
    String image;
    String name;
    String price;

    public  AddiProductData(){};

    public AddiProductData(String desc, String id, String image, String name, String price) {
        this.desc = desc;
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
