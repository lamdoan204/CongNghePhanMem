package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Product", schema = "dbo")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "id")
    private int id;
    @Column(name= "name")
    private String name;
    @Column(name = "price")
    private float price;
    @Column(name = "kind")
    private int kind;
    @Column(name="brand")
    private int brand;
    @Column(name ="description")
    private String description;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public int getKind() {
        return kind;
    }
    public void setKind(int kind) {
        this.kind = kind;
    }
    public int getBrand() {
        return brand;
    }
    public void setBrand(int brand) {
        this.brand = brand;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Product(int id, String name, float price, int kind, int brand, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.kind = kind;
        this.brand = brand;
        this.description = description;
    }

}
