package com.Project.CongNghePhanMem.Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product implements Serializable


{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6070287017178476598L;

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
    
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
    
	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getName() {
		return name;
	}


	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
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

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", name=" + name + ", price=" + price + ", kind=" + kind + ", brand="
				+ brand + ", description=" + description + ", promotions=" + promotions + ", orderDetails="
				+ orderDetails + "]";
	}
    
    

}
