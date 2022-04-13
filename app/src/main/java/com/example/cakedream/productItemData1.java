package com.example.cakedream;

public class productItemData1 {


    public class productItemData {
        private String cake_name;
        private float cake_price;



        public productItemData(String cake_name,float cake_price) {
            this.cake_name = cake_name;
            this.cake_price = cake_price;
        }




        public String getCake_name() {
            return cake_name;
        }
        public float getCake_price(){ return cake_price;}
    }


}
