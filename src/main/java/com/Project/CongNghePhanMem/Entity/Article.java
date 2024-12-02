package com.Project.CongNghePhanMem.Entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int articleId;
    
    private String title;
    
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    
    private String content;
    
    private String image;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    
    public void setAuthor(User author) {
		this.author = author;
	}

	private int likes;
    
    private int shares;

    // Getters and setters

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

	public int getLikes() {
		return likes;
	}

	public void setLikes(int like) {
		this.likes = like;
	}

    public void setLike(int like) {
        this.likes = like;
    }

    public int getShare() {
        return shares;
    }

    public void setShare(int share) {
        this.shares = share;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Article(int articleId, String title, Date orderDate, String content, User author, int like, int share, String image) {
        super();
        this.articleId = articleId;
        this.title = title;
        this.orderDate = orderDate;
        this.content = content;
        this.author = author;
        this.likes = like;
        this.shares = share;
        this.image = image;
    }

    public Article() {
        super();
    }
}
