package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="Reviews_belong_to_product", schema = "dbo")
public class Reviews_belong_to_product {

    @EmbeddedId
    private Reviews_belong_to_product_id review_id_n_product_id;

    public Reviews_belong_to_product_id getReview_id_n_product_id() {
        return review_id_n_product_id;
    }

    public void setReview_id_n_product_id(Reviews_belong_to_product_id review_id_n_product_id) {
        this.review_id_n_product_id = review_id_n_product_id;
    }

    public Reviews_belong_to_product(Reviews_belong_to_product_id review_id_n_product_id) {
        this.review_id_n_product_id = review_id_n_product_id;
    }
    

}
