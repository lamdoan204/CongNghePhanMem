package com.Project.CongNghePhanMem.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Entity.User;

@Service
public interface IOrderService {

	Order createOrder(User user, Cart cart);

	List<Order> getOrdersByUser(User user);

	Order findById(int orderId);
	
	 // Cập nhật trạng thái đơn hàng và đồng bộ với notifications
    void updateOrderStatus(int orderId, int newStatus);

}
