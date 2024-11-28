package com.Project.CongNghePhanMem.Service.Impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.CartDetail;
import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Entity.OrderDetail;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.OrderDetailRepository;
import com.Project.CongNghePhanMem.Repository.OrderRepository;
import com.Project.CongNghePhanMem.Service.IOrderService;

@Service
public class OrderService implements IOrderService{
	@Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Override
	public Order createOrder(User user, Cart cart) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setStatus(Order.PENDING);
        
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
}
