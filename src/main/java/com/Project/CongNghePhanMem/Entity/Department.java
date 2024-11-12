package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "Department", schema = "dbo")
public class Department {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    @Column(name= "id")
    private int id;
    @Column(name= "manager_id")
    private int manager_id;
    @Column(name = "brand_managerment")
    private String brand_managerment;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getManager_id() {
        return manager_id;
    }
    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }
    public String getBrand_managerment() {
        return brand_managerment;
    }
    public void setBrand_managerment(String brand_managerment) {
        this.brand_managerment = brand_managerment;
    }
    public Department(int id, int manager_id, String brand_managerment) {
        this.id = id;
        this.manager_id = manager_id;
        this.brand_managerment = brand_managerment;
    }
}
