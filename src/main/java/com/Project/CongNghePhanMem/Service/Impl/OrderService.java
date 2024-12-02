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
import com.Project.CongNghePhanMem.Entity.Notification;
import com.Project.CongNghePhanMem.Entity.OrderDetail;
import com.Project.CongNghePhanMem.Entity.Promotion;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.NotificationRepository;
import com.Project.CongNghePhanMem.Repository.OrderDetailRepository;

import com.Project.CongNghePhanMem.Service.IOrderService;

@Service
public class OrderService implements IOrderService{
	@Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
    @Autowired
	private NotificationRepository notificationRepository;
    
    @Override
    public Order createOrder(User user, Cart cart, boolean isPaidByCard, float finalPrice, Promotion promotion) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setStatus(Order.PENDING);
        order.setPaidByCard(isPaidByCard);
        order.setTotalPrice(finalPrice); 
        order.setAppliedPromotion(promotion);

        // Tạo order details
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartDetail cartDetail : cart.getCartDetails()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartDetail.getProduct());
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(cartDetail.getPrice());
            orderDetails.add(orderDetail);
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
    public void updateOrderStatus(int orderId, int newStatus) {
        // Tìm đơn hàng theo ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        // Cập nhật trạng thái mới
        order.setStatus(newStatus);
        orderRepository.save(order); // Lưu thay đổi vào database

        // Xây dựng thông báo dựa trên trạng thái
        String statusMessage;
        switch (newStatus) {
            case 0:
                statusMessage = "Đang chờ xác nhận";
                break;
            case 1:
                statusMessage = "Đã xác nhận";
                break;
            case 2:
                statusMessage = "Đang giao hàng";
                break;
            case 3:
                statusMessage = "Đã giao hàng";
                break;
            case 4:
                statusMessage = "Đã hủy";
                break;
            default:
                statusMessage = "Trạng thái không xác định";
        }

        String message = "Đơn hàng #" + orderId + " " + statusMessage;

        // Tạo thông báo mới
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setOrder(order);
        notification.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));

        // Lưu thông báo vào bảng notifications
        notificationRepository.save(notification);
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
