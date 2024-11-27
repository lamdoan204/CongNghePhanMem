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

    // Xác nhận đơn hàng
    public Order confirmOrder(int orderID) {
        // Tìm đơn hàng theo orderID
        Order order = orderRepository.findByOrderID(orderID);

        if (order != null) {
            // Thay đổi trạng thái đơn hàng thành "Đã xác nhận"
            order.setStatus(1); // Giả sử trạng thái "1" là "Đã xác nhận"
            // Lưu đơn hàng sau khi thay đổi trạng thái
            orderRepository.save(order);
            return order;
        }

        return null; // Nếu không tìm thấy đơn hàng
    }

    // Hủy đơn hàng
    public Order cancelOrder(int orderID) {
        // Tìm đơn hàng theo orderID
        Order order = orderRepository.findByOrderID(orderID);

        if (order != null) {
            // Thay đổi trạng thái đơn hàng thành "Đã hủy"
            order.setStatus(2); // Giả sử trạng thái "2" là "Đã hủy"
            // Lưu đơn hàng sau khi thay đổi trạng thái
            orderRepository.save(order);
            return order;
        }

        return null; // Nếu không tìm thấy đơn hàng
    }

    // Lấy danh sách đơn hàng theo trạng thái
    public List<Order> getOrdersByStatus(int status) {
        return orderRepository.findByStatus(status);
    }
}
