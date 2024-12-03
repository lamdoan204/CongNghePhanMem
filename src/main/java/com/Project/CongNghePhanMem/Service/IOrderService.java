package com.Project.CongNghePhanMem.Service;

import java.util.List;

import com.Project.CongNghePhanMem.Entity.Order;

public interface IOrderService {
	public Order findById(int orderId);
	
	List<Order> getAllOrders();
	
	List<Order> getOrdersByStatus(int status);
	
	 List<Order> getPendingOrders() ;
	 
	 List<Order> getConfirmedOrders();
	 
	 List<Order> getInDeliveryOrders();
	 
	 List<Order> getDeliveredOrders();
	 
	 List<Order> getCancelledOrders();
	 
	 List<Order> searchOrders(String searchTerm);
	 
	 public Order getOrderDetails(int orderID);
	 
}
