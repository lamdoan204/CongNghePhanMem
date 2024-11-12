package com.Project.CongNghePhanMem.Entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Products_belong_to_order_id implements Serializable{
    private int order_id;
    private int product_id;
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public Products_belong_to_order_id(int order_id, int product_id) {
        this.order_id = order_id;
        this.product_id = product_id;
    }
    
}
