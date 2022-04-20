package com.example.cakedream;

public class adminProductData1 {
    String name;
    String design;
    String flavour;
    String shape;
    String size;
    String type;
    String quantity;
    String notes;
    String price;

    public adminProductData1(String name, String design, String flavour, String shape, String size, String type, String quantity, String notes, String price) {
        this.name = name;
        this.design = design;
        this.flavour = flavour;
        this.shape = shape;
        this.size = size;
        this.type = type;
        this.quantity = quantity;
        this.notes = notes;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
}
