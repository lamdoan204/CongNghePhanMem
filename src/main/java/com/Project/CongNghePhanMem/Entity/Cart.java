package com.Project.CongNghePhanMem.Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "carts")
public class Cart implements Serializable{


	private static final long serialVersionUID = 6774827131988996424L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	// tong san pham trong gio hang
	@Min(value = 0)
	private int sum;
	
	@OneToOne()
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "cart")
	List<CartDetail> cartDetails;
	
	public Cart() {
		super();
	}

	public Cart(int id, @Min(0) int sum, User user, List<CartDetail> cartDetails) {
		super();
		this.id = id;
		this.sum = sum;
		this.user = user;
		this.cartDetails = cartDetails;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartDetail> getCartDetails() {
		return cartDetails;
	}

	public void setCartDetails(List<CartDetail> cartDetails) {
		this.cartDetails = cartDetails;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", sum=" + sum + ", user=" + user + ", cartDetails=" + cartDetails + "]";
	}
	
	
}
