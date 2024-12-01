package com.Project.CongNghePhanMem.Service.Impl;

import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Repository.OrderRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();  
    }  

    public List<Order> getOrdersByStatus(int status) {
        return orderRepository.findByStatus(status);
    }
    
    public List<Order> getPendingOrders() {
        return orderRepository.findByStatus(1);
    }
    
    public List<Order> getConfirmedOrders() {
        return orderRepository.findByStatus(2);
    }
    
    public List<Order> getInDeliveryOrders() {
        return orderRepository.findByStatus(3);
    }
    
    public List<Order> getDeliveredOrders() {
        return orderRepository.findByStatus(4);
    }
    
    public List<Order> getCancelledOrders() {
        return orderRepository.findByStatus(5);
    }    
}
