package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Pay_for_order", schema = "dbo")
public class Pay_for_order {

    @EmbeddedId
    private Pay_for_order_id id;
    public Pay_for_order_id getId() {
        return id;
    }

    public void setId(Pay_for_order_id id) {
        this.id = id;
    }
    public Pay_for_order(Pay_for_order_id id){
        this.id =id;
    }   
}
