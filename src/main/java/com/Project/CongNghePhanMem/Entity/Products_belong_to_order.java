package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Products_belong_to_order", schema = "dbo")
public class Products_belong_to_order {

    @EmbeddedId
    private Products_belong_to_order_id order_id_n_product_id;
    @Column(name = "content")
    private String content;

    public Products_belong_to_order_id getOrder_id_n_product_id() {
        return order_id_n_product_id;
    }

    public void setOrder_id_n_product_id(Products_belong_to_order_id order_id_n_product_id) {
        this.order_id_n_product_id = order_id_n_product_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Products_belong_to_order(Products_belong_to_order_id order_id_n_product_id, String content) {
        this.order_id_n_product_id = order_id_n_product_id;
        this.content = content;
    }

}
