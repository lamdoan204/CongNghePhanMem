package com.Project.CongNghePhanMem.Entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @Temporal(TemporalType.DATE)
    private Date orderDate;
    
    private int status;
    
    public static final int PENDING = 0; // Đang chờ xác nhận
    public static final int CONFIRMED = 1; // Đã xác nhận
    public static final int IN_DELIVERY = 2; // Đang giao
    public static final int DELIVERED = 3; // Đã giao
    public static final int CANCELLED = 4; // Đã hủy
    
    private float totalPrice;

    @Column(name = "status", columnDefinition = "INTEGER DEFAULT 0")
    private int status;
	
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    
    public Order(int orderID, Date orderDate, float totalPrice, List<OrderDetail> orderDetails, User user) {
		super();
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.orderDetails = orderDetails;
		this.user = user;
	}

	public Order() {
		super();
	}

	public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    
    
    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", orderDate=" + orderDate + ", status=" + status + ", totalPrice="
				+ totalPrice + ", orderDetails=" + orderDetails + ", user=" + user + "]";
	}
    
}
