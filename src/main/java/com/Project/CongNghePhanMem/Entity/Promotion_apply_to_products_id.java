package com.Project.CongNghePhanMem.Entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Promotion_apply_to_products_id implements  Serializable{

    private int promo_id;
    private int product_id;
    public int getPromo_id() {
        return promo_id;
    }
    public void setPromo_id(int promo_id) {
        this.promo_id = promo_id;
    }
    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public Promotion_apply_to_products_id(int promo_id, int product_id) {
        this.promo_id = promo_id;
        this.product_id = product_id;
    }
    

}
