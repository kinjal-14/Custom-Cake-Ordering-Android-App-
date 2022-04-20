package com.example.cakedream;

public class cart_model {
    String productid;
    String image;
    String name;
    String size;
    String quantity;
    String price;
    String caketype;
    String notes;
    String flavour;
    String design;
    String shape;
    String type;

    public cart_model() {     }

    public cart_model(String productid, String image, String name, String size, String quantity, String caketype, String notes, String flavour, String design, String shape, String type, String price) {
        this.productid = productid;
        this.image = image;
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.caketype = caketype;
        this.notes = notes;
        this.flavour = flavour;
        this.design = design;
        this.shape = shape;
        this.type = type;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCaketype() {
        return caketype;
    }

    public void setCaketype(String caketype) {
        this.caketype = caketype;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}