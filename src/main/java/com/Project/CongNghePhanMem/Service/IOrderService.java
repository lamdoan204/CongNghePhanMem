package com.Project.CongNghePhanMem.Service;

import java.awt.print.Pageable;
import java.util.List;

import com.Project.CongNghePhanMem.Entity.Order;
import com.google.api.gax.paging.Page;

public interface IOrderService {
	List<Order> getAllOrders();
	
	List<Order> getOrdersByStatus(int status);
	
	 List<Order> getPendingOrders() ;
	 
	 List<Order> getConfirmedOrders();
	 
	 List<Order> getInDeliveryOrders();
	 
	 List<Order> getDeliveredOrders();
	 
	 List<Order> getCancelledOrders();
	 
	 List<Order> searchOrders(String searchTerm);
import java.util.List;

import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Entity.User;

@Service
public interface IOrderService {

	Order createOrder(User user, Cart cart, boolean isPaidByCard);

	List<Order> getOrdersByUser(User user);

	Order findById(int orderId);
	
	 // Cập nhật trạng thái đơn hàng và đồng bộ với notifications
    void updateOrderStatus(int orderId, int newStatus);

	List<Order> getOrdersByUserAndStatus(User user, int status);

	List<Order> getOrdersByStatus(int status);

	void cancelOrder(Integer orderId, String cancelReason);

}
