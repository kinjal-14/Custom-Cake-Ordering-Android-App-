package com.example.cakedream.pre_made_Adapter;


public class productItemData {
    private String cake_name;
    private float cake_price;



    public productItemData(String cake_name, float cake_price) {
        this.cake_name = cake_name;
        this.cake_price = cake_price;
    }




        public String getCake_name() {
        return cake_name;
    }
        public float getCake_price(){return cake_price;}
}

