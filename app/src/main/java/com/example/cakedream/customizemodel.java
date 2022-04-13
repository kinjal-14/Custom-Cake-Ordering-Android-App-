package com.example.cakedream;

public class customizemodel {
    String type;
    String shape;
    String size;
    String flavour;
    String Design;

    String mnotes;
    String mImage;

    public customizemodel(String type, String shape, String size, String flavour, String design, String mnotes, String mImage) {
        this.type = type;
        this.shape = shape;
        this.size = size;
        this.flavour = flavour;
        Design = design;
        this.mnotes = mnotes;
        this.mImage = mImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getDesign() {
        return Design;
    }

    public void setDesign(String design) {
        Design = design;
    }

    public String getMnotes() {
        return mnotes;
    }

    public void setMnotes(String mnotes) {
        this.mnotes = mnotes;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}
