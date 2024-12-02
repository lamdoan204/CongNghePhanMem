package com.Project.CongNghePhanMem.Entity;

import java.sql.Date;
import java.time.LocalDateTime;
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
    
    public static final int PENDING = 1; // Đang chờ xác nhận
    public static final int CONFIRMED = 2; // Đã xác nhận
    public static final int IN_DELIVERY = 3; // Đang giao
    public static final int DELIVERED = 4; // Đã giao
    public static final int CANCELLED = 5; // Đã hủy
    
    @Column(name = "is_paid_by_card")
    private Boolean isPaidByCard = false; // true: thanh toán bằng thẻ, false: thanh toán khi nhận hàng
    
    @Column
    private String cancelReason; // lý do hủy
    
    @Column
    private LocalDateTime cancelDate; // ngày hủy
    
    
    private float totalPrice;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    
    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion appliedPromotion;  // Thêm trường này

    // Thêm getter/setter
    public Promotion getAppliedPromotion() {
        return appliedPromotion;
    }

    public void setAppliedPromotion(Promotion appliedPromotion) {
        this.appliedPromotion = appliedPromotion;
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


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	


	public boolean isPaidByCard() {
		return isPaidByCard;
	}

	public void setPaidByCard(boolean isPaidByCard) {
		this.isPaidByCard = isPaidByCard;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	
	public LocalDateTime getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(LocalDateTime cancelDate) {
		this.cancelDate = cancelDate;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	

	public Order(int orderID, Date orderDate, int status, float totalPrice, List<OrderDetail> orderDetails, User user) {
		super();
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.status = status;
		this.totalPrice = totalPrice;
		this.orderDetails = orderDetails;
		this.user = user;
	}

    
}
