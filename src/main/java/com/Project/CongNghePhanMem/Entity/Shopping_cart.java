package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name= "Shopping_cart", schema="dbo")
public class Shopping_cart {
    @EmbeddedId
    private Shopping_cart_id user_id_n_product_id;
    @Column(name = "quantity")
    private int quantity;
    public Shopping_cart_id getUser_id_n_product_id() {
        return user_id_n_product_id;
    }
    public void setUser_id_n_product_id(Shopping_cart_id user_id_n_product_id) {
        this.user_id_n_product_id = user_id_n_product_id;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Shopping_cart(Shopping_cart_id user_id_n_product_id, int quantity) {
        this.user_id_n_product_id = user_id_n_product_id;
        this.quantity = quantity;
    }
    
    

}
