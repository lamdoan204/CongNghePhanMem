package com.Project.CongNghePhanMem.Controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Service.IOrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/orders")
public class OrderUserController {
	@Autowired
	private IOrderService orderService;

	@GetMapping
	public String listOrders(Model model, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		List<Order> orders = orderService.getOrdersByUser(currentUser);
		model.addAttribute("orders", orders);
		model.addAttribute("currentPage", "all"); // Thêm biến này
		return "user/orders";
	}

	@GetMapping("/pending")
	public String pendingOrders(Model model, HttpSession session) {
		User user = (User) session.getAttribute("currentUser");
		List<Order> orders = orderService.getOrdersByUserAndStatus(user, Order.PENDING);
		model.addAttribute("orders", orders);
		model.addAttribute("currentPage", "pending"); // Thêm biến này
		return "user/orders";
	}

	@GetMapping("/confirmed")
	public String confirmedOrders(Model model, HttpSession session) {
		User user = (User) session.getAttribute("currentUser");
		List<Order> orders = orderService.getOrdersByUserAndStatus(user, Order.CONFIRMED);
		model.addAttribute("orders", orders);
		model.addAttribute("currentPage", "confirmed"); // Thêm biến này
		return "user/orders";
	}

	@GetMapping("/shipping")
	public String shippingOrders(Model model, HttpSession session) {
		User user = (User) session.getAttribute("currentUser");
		List<Order> orders = orderService.getOrdersByUserAndStatus(user, Order.IN_DELIVERY);
		model.addAttribute("orders", orders);
		model.addAttribute("currentPage", "shipping"); // Thêm biến này
		return "user/orders";
	}

	@GetMapping("/delivered")
	public String deliveredOrders(Model model, HttpSession session) {
		User user = (User) session.getAttribute("currentUser");
		List<Order> orders = orderService.getOrdersByUserAndStatus(user, Order.DELIVERED);
		model.addAttribute("orders", orders);
		model.addAttribute("currentPage", "delivered"); // Thêm biến này
		return "user/orders";
	}

	@GetMapping("/cancelled")
	public String cancelledOrders(Model model, HttpSession session) {
		User user = (User) session.getAttribute("currentUser");
		List<Order> orders = orderService.getOrdersByUserAndStatus(user, Order.CANCELLED);
		model.addAttribute("orders", orders);
		model.addAttribute("currentPage", "cancelled"); // Thêm biến này
		return "user/orders";
	}

	// Thêm method xử lý hủy đơn
	@PostMapping("/cancel/{id}")
	public String cancelOrder(@PathVariable("id") Integer orderId, @RequestParam("cancelReason") String cancelReason,
			HttpSession session) {
		try {
			User currentUser = (User) session.getAttribute("currentUser");
			Order order = orderService.findById(orderId);

			// Kiểm tra quyền hủy đơn
			if (order == null || order.getUser().getUserId() != currentUser.getUserId()) {
				throw new RuntimeException("Không có quyền hủy đơn hàng này");
			}

			// Kiểm tra trạng thái đơn hàng
			if (order.getStatus() != Order.PENDING && order.getStatus() != Order.CONFIRMED) {
				throw new RuntimeException("Không thể hủy đơn hàng ở trạng thái này");
			}

			// Cập nhật trạng thái và lý do hủy
			orderService.cancelOrder(orderId, cancelReason);

			return "redirect:/user/orders?cancelSuccess=true";
		} catch (Exception e) {
			return "redirect:/user/orders?cancelError=" + e.getMessage();
		}
	}
	
	
}