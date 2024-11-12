package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Method_payment", schema = "dbo")
public class Method_payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name= "name_method")
    private String name_method;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName_method() {
        return name_method;
    }
    public void setName_method(String name_method) {
        this.name_method = name_method;
    }
    public Method_payment(int id, String name_method) {
        this.id = id;
        this.name_method = name_method;
    }
    

}
