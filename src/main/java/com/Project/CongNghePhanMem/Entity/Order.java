package com.Project.CongNghePhanMem.Entity;


import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Order", schema = "dbo")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name= "customer_id")
    private int customer_id;
    @Column(name = "order_date")
    private Date order_date;
    @Column(name= "total_price")
    private float total_price;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public Date getOrder_date() {
        return order_date;
    }
    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
    public float getTotal_price() {
        return total_price;
    }
    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }
    public Order(int id, int customer_id, Date order_date, float total_price) {
        this.id = id;
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.total_price = total_price;
    }
    
}
