package com.Project.CongNghePhanMem.Service.Impl;

import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Repository.OrderRepository;
import com.Project.CongNghePhanMem.Service.IOrderService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;

	public Order findById(int orderId) {
		Order order = orderRepository.findByOrderID(orderId);
		return order;
	}

	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	public List<Order> getOrdersByStatus(int status) {
		return orderRepository.findByStatus(status);
	}

	public List<Order> getPendingOrders() {
		return orderRepository.findByStatus(0);
	}

	public List<Order> getConfirmedOrders() {
		return orderRepository.findByStatus(1);
	}

	public List<Order> getInDeliveryOrders() {
		return orderRepository.findByStatus(2);
	}

	public List<Order> getDeliveredOrders() {
		return orderRepository.findByStatus(3);
	}

	public List<Order> getCancelledOrders() {
		return orderRepository.findByStatus(4);
	}

	public List<Order> searchOrders(String searchTerm) {
		if (searchTerm.startsWith("0")) {
			return orderRepository.findByUserPhoneContaining(searchTerm);
		} else {
			try {
				Integer orderID = Integer.parseInt(searchTerm);
				return orderRepository.findByOrderID(orderID);
			} catch (NumberFormatException e) {
				return new ArrayList<>();
			}
		}
	}

	public Order getOrderDetails(int orderID) {
		Order order = orderRepository.findByOrderID(orderID);
		return order;
	}
}
