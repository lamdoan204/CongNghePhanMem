package com.Project.CongNghePhanMem.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Entity.Promotion;
import com.Project.CongNghePhanMem.Entity.User;

@Service
public interface IOrderService {

	Order createOrder(User user, Cart cart, boolean isPaidByCard, float finalPrice, Promotion promotion);

	List<Order> getOrdersByUser(User user);

	Order findById(int orderId);
	
	 // Cập nhật trạng thái đơn hàng và đồng bộ với notifications
    void updateOrderStatus(int orderId, int newStatus);

	List<Order> getOrdersByUserAndStatus(User user, int status);

	List<Order> getOrdersByStatus(int status);

	void cancelOrder(Integer orderId, String cancelReason);

}
