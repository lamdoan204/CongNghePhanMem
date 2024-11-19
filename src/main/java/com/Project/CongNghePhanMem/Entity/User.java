package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "Users", schema="dbo")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name =  "id")
    private int id;
    @Column(name= "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "full_name")
    private String full_name;
    @Column(name= "address")
    private String address;
    @Column(name= "role_id")
    private int role_id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getFull_name() {
        return full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public int getRole_id() {
        return role_id;
    }
    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }
    public User(int id, String email, String phone, String full_name, String address, int role_id) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.full_name = full_name;
        this.address = address;
        this.role_id = role_id;
    }
}
