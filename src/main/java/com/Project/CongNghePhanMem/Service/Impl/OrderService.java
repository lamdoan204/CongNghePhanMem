package com.Project.CongNghePhanMem.Service.Impl;

import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Repository.OrderRepository;
import com.Project.CongNghePhanMem.Service.IOrderService;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;

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
	        // Nếu `searchTerm` bắt đầu bằng số 0, tìm kiếm theo số điện thoại
	        return orderRepository.findByUserPhoneContaining(searchTerm); // Tìm hóa đơn theo số điện thoại
	    } else {
	        try {
	            // Nếu `searchTerm` không bắt đầu bằng 0, thử tìm kiếm theo mã hóa đơn (orderID)
	            Integer orderID = Integer.parseInt(searchTerm);
	            return orderRepository.findByOrderID(orderID); // Tìm hóa đơn theo mã hóa đơn
	        } catch (NumberFormatException e) {
	            // Nếu `searchTerm` không phải là số (và không bắt đầu bằng 0), không có kết quả
	            return new ArrayList<>(); // Trả về danh sách rỗng nếu không tìm thấy kiểu hợp lệ
	        }
	    }
	}

}
