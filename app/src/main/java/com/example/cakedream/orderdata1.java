package com.example.cakedream;

import java.io.Serializable;

public class orderdata1 {
    String orderid;
    String placed;
    String total;

    public orderdata1(String orderid, String placed, String total) {
        this.orderid = orderid;
        this.placed = placed;
        this.total = total;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
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
}
