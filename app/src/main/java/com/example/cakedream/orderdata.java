package com.example.cakedream;

public class orderdata {
    String orderid;
    String name;
    String phone;
    String email;
    String address;
    String placed;
    String total;
    String ordertype;
    String uid;



    public orderdata(String orderid, String name,  String phone, String email, String address, String address2, String city, String state, String postalCode, String placed, String total, String ordertype,String uid) {
        this.orderid = orderid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.placed = placed;
        this.total = total;
        this.ordertype = ordertype;
        this.uid = uid;

    }
    public  orderdata() {}

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


    public String getPlaced() {
        return placed;
    }

    public void setPlaced(String placed) {
        this.placed = placed;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
