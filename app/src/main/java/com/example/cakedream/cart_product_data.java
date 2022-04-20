package com.example.cakedream;

public class cart_product_data {
    String productid;
    String image;
    String name;
    String quantity;

    public cart_product_data(String productid, String image, String name, String quantity) {
        this.productid = productid;
        this.image = image;
        this.name = name;
        this.quantity = quantity;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}