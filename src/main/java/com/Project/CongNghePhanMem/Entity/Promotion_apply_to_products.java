package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "Promotion_apply_to_products", schema="dbo")
public class Promotion_apply_to_products {
    @EmbeddedId
    private Promotion_apply_to_products_id promo_id_n_product_id;

    public Promotion_apply_to_products_id getPromo_id_n_product_id() {
        return promo_id_n_product_id;
    }

    public void setPromo_id_n_product_id(Promotion_apply_to_products_id promo_id_n_product_id) {
        this.promo_id_n_product_id = promo_id_n_product_id;
    }

    public Promotion_apply_to_products(Promotion_apply_to_products_id promo_id_n_product_id) {
        this.promo_id_n_product_id = promo_id_n_product_id;
    }
    
}
