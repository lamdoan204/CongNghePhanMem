package com.Project.CongNghePhanMem.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity()
@Table(name = "Promotion", schema = "dbo")
public class Promotion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name="start_date")
    private Date start_date;
    @Column(name = "end_date")
    private Date end_date;
    @Column(name="discount_rate")
    private int discount_rate;
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
    public Date getStart_date() {
        return start_date;
    }
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
    public Date getEnd_date() {
        return end_date;
    }
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
    public int getDiscount_rate() {
        return discount_rate;
    }
    public void setDiscount_rate(int discount_rate) {
        this.discount_rate = discount_rate;
    }
    public Promotion(int id, String name, Date start_date, Date end_date, int discount_rate) {
        this.id = id;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.discount_rate = discount_rate;
    }
    

}
