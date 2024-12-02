package com.Project.CongNghePhanMem.Service.Impl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Repository.OrderRepository;
import com.Project.CongNghePhanMem.Service.IOrderService;
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

@Service
public class OrderService implements IOrderService {
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public Order createOrder(User user, Cart cart, boolean isPaidByCard, float finalPrice, Promotion promotion) {
		Order order = new Order();
		order.setUser(user);
		order.setOrderDate(new Date(System.currentTimeMillis()));
		order.setStatus(Order.PENDING);
		order.setPaidByCard(isPaidByCard);
		order.setTotalPrice(finalPrice); // Sử dụng giá đã giảm
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
		return orderRepository.findById(orderId).orElse(null);
	}

	// Method để lấy danh sách đơn hàng của user
	@Override
	public List<Order> getOrdersByUser(User user) {
		return orderRepository.findByUserOrderByOrderDateDesc(user);
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

//    @Override
//	public List<Order> getOrdersByUserAndStatus(User user, int status) {
//        return orderRepository.findByUserAndStatusOrderByOrderDateDesc(user, status);
//    }

	@Override
	public void cancelOrder(Integer orderId, String cancelReason) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

		order.setStatus(Order.CANCELLED);
		order.setCancelReason(cancelReason);
		order.setCancelDate(LocalDateTime.now());

		orderRepository.save(order);
	}

	public Order getOrderById(int orderID) {
		// Sử dụng phương thức tìm kiếm đơn hàng kèm theo các items
		return orderRepository.findById(orderID).orElse(null);
	}
}
