package com.Project.CongNghePhanMem.Service.Impl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Repository.OrderRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.CartDetail;
import com.Project.CongNghePhanMem.Entity.OrderDetail;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.OrderDetailRepository;
import com.Project.CongNghePhanMem.Service.IOrderService;

@Service
public class OrderService implements IOrderService{
	@Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Override
	public Order createOrder(User user, Cart cart, boolean isPaidByCard) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setStatus(Order.PENDING);
        order.setPaidByCard(isPaidByCard);
        
        // Tính tổng tiền
        float totalPrice = 0;
        for (CartDetail cartDetail : cart.getCartDetails()) {
            totalPrice += cartDetail.getPrice() * cartDetail.getQuantity();
        }
        order.setTotalPrice(totalPrice);
        
        // Lưu order
        order = orderRepository.save(order);
        
        // Tạo và lưu order details
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetail cartDetail : cart.getCartDetails()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(cartDetail.getProduct());
            detail.setQuantity(cartDetail.getQuantity());
            detail.setPrice(cartDetail.getPrice());
            orderDetails.add(orderDetailRepository.save(detail));
        }
        
        order.setOrderDetails(orderDetails);
        return orderRepository.save(order);
    }
    
    
    @Override
	public Order findById(int orderId) {
        return orderRepository.findById(orderId)
            .orElse(null);
    }
    
 // Method để lấy danh sách đơn hàng của user
    @Override
	public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

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
    @Override
	public List<Order> getOrdersByStatus(int status) {
        return orderRepository.findByStatus(status);
    }
    

    @Override
	public List<Order> getOrdersByUserAndStatus(User user, int status) {
        return orderRepository.findByUserAndStatusOrderByOrderDateDesc(user, status);
    }
    
    @Override
	public void cancelOrder(Integer orderId, String cancelReason) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
            
        order.setStatus(Order.CANCELLED);
        order.setCancelReason(cancelReason);
        order.setCancelDate(LocalDateTime.now());
        
        orderRepository.save(order);
    }
}
