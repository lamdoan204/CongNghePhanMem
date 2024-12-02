package com.Project.CongNghePhanMem.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
	private User reviewer;
	
	 @ManyToOne
	 @JoinColumn(name = "product_id")  // Liên kết với bảng Product
	 private Product product;  // Sửa từ int thành đối tượng Product

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	private String content;
	
	private double rating;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Review(int id, User reviewer, String content, double rating) {
		super();
		this.id = id;
		this.reviewer = reviewer;
		this.content = content;
		this.rating = rating;
	}

	public Review() {
		super();
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", reviewer=" + reviewer + ", product=" + product + ", content=" + content
				+ ", rating=" + rating + "]";
	}
	
	
}
