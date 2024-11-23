package com.Project.CongNghePhanMem.Entity;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    private float totalPrice;

    @ElementCollection
    @CollectionTable(name = "order_products", 
                     joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> products = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }
}
