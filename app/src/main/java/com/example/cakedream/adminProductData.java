package com.example.cakedream;

public class adminProductData {
    String design;
    String flavour;
    String image;
    String name;
    String notes;
    String price;
    String productid;
    String quantity;
    String shape;
    String size;
    String type;
    String caketype;

    public adminProductData(String design, String flavour, String image, String name, String notes, String price, String productid, String quantity, String shape, String size, String type,String caketype) {
        this.design = design;
        this.flavour = flavour;
        this.image = image;
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.productid = productid;
        this.quantity = quantity;
        this.shape = shape;
        this.size = size;
        this.type = type;
        this.caketype = caketype;
    }
    public adminProductData(){}

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
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
    public String getCaketype() {
        return caketype;
    }

    public void setCaketype(String caketype) {
        this.caketype = caketype;
    }
}
