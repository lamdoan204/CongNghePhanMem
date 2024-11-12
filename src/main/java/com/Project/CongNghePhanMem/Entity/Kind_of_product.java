package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "kind_of_product", schema = "dbo")
public class Kind_of_product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private int id;
    @Column(name= "kind")
    private String kind;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
    public Kind_of_product(int id, String kind) {
        this.id = id;
        this.kind = kind;
    }
    
}
