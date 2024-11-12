package com.Project.CongNghePhanMem.Entity;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class Reviews_belong_to_product_id implements  Serializable{

    private int product_id;
    private int review_id;
    public int getProduct_id() {
        return product_id;
    }
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    public int getReview_id() {
        return review_id;
    }
    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }
    public Reviews_belong_to_product_id(int product_id, int review_id) {
        this.product_id = product_id;
        this.review_id = review_id;
    }
    
}
