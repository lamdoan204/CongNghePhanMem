package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Review", schema= "dbo")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name= "reviewer_id")
    private int reviewer_id;
    @Column(name = "content")
    private String content;
    @Column(name = "rating")
    private int rating;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getReviewer_id() {
        return reviewer_id;
    }
    public void setReviewer_id(int reviewer_id) {
        this.reviewer_id = reviewer_id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public Review(int id, int reviewer_id, String content, int rating) {
        this.id = id;
        this.reviewer_id = reviewer_id;
        this.content = content;
        this.rating = rating;
    }
    


}
