package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Award_point", schema = "dbo")
public class Award_point {
    @Id
    @Column(name= "customer_id")
    private int customer_id;
    @Column(name = "point")
    private int point;
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public int getPoint() {
        return point;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public Award_point(int customer_id, int point) {
        this.customer_id = customer_id;
        this.point = point;
    }
    

}
