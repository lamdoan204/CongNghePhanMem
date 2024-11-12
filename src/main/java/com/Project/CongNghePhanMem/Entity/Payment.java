package com.Project.CongNghePhanMem.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Payment", schema= "dbo")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name="customer_id")
    private String customer_id;
    @Column(name = "payment_date")
    private Date payment_date;
    @Column(name= "total_payment")
    private float total_payment;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
    public Date getPayment_date() {
        return payment_date;
    }
    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }
    public float getTotal_payment() {
        return total_payment;
    }
    public void setTotal_payment(float total_payment) {
        this.total_payment = total_payment;
    }
    public Payment(int id, String customer_id, Date payment_date, float total_payment) {
        this.id = id;
        this.customer_id = customer_id;
        this.payment_date = payment_date;
        this.total_payment = total_payment;
    }
    


}
