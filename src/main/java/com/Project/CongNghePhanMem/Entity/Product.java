package com.Project.CongNghePhanMem.Entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productID;

    private String name;
    private float price;
    private String kind;
    private String brand;
    private String description;

    @ManyToMany(mappedBy = "applicableProducts")
    private List<Promotion> promotions;

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
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

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Promotion> getPromotions() {
		return promotions;
	}

	public void setPromotions(List<Promotion> promotions) {
		this.promotions = promotions;
	}

	public Product(int productID, String name, float price, String kind, String brand, String description,
			List<Promotion> promotions) {
		super();
		this.productID = productID;
		this.name = name;
		this.price = price;
		this.kind = kind;
		this.brand = brand;
		this.description = description;
		this.promotions = promotions;
	}

	public Product() {
		super();
		
	}
    
    

}
