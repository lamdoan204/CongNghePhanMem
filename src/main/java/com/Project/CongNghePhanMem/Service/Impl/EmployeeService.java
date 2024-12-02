package com.Project.CongNghePhanMem.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.Project.CongNghePhanMem.Repository.CartRepository;
import com.Project.CongNghePhanMem.Repository.OrderRepository;
import com.google.firestore.v1.StructuredQuery.Order;

public class EmployeeService {
	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository customerRepository;

    public Customer getBestCustomer() {
        // Truy vấn khách hàng mua hàng nhiều nhất, ví dụ theo số lượng đơn hàng
        return customerRepository.findTopByOrderByTotalPurchasesDesc();
    }

    public Order getHighestBill() {
        // Truy vấn hóa đơn có giá trị cao nhất
        return orderRepository.findTopByOrderByTotalPriceDesc();
    }
}