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
}
