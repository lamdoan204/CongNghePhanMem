package com.Project.CongNghePhanMem.Entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Pay_for_order_id implements Serializable{

    private int payment_id;
    private int order_id;
    public int getPayment_id() {
        return payment_id;
    }
    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public Pay_for_order_id(int payment_id, int order_id) {
        this.payment_id = payment_id;
        this.order_id = order_id;
    }
}
