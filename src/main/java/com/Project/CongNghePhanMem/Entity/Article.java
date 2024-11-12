package com.Project.CongNghePhanMem.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name= "Article", schema = "dbo")
public class Article {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    @Column(name= "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name= "post_date")
    private Date post_date;
    @Column(name="content")
    private String content;
    @Column(name="shares")
    private int shares;
    public Article(int id, String title, Date post_date, String content, int shares) {
        this.id = id;
        this.title = title;
        this.post_date = post_date;
        this.content = content;
        this.shares = shares;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Date getPost_date() {
        return post_date;
    }
    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getShares() {
        return shares;
    }
    public void setShares(int shares) {
        this.shares = shares;
    }
    

}
