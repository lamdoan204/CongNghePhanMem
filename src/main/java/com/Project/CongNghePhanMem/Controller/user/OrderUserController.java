package com.Project.CongNghePhanMem.Controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Service.IOrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/orders")
public class OrderUserController {
	@Autowired
	private IOrderService orderService;

	@GetMapping
	public String listOrders(Model model, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		List<Order> orders = orderService.getOrdersByUser(currentUser);
		model.addAttribute("orders", orders);
		return "user/orders";
	}
	

	   @GetMapping("/pending")
	   public String pendingOrders(Model model, HttpSession session) {
	       User user = (User) session.getAttribute("currentUser");
	       List<Order> orders = orderService.getOrdersByUserAndStatus(user, Order.PENDING);
	       model.addAttribute("orders", orders);
	       return "user/orders";
	   }

	   @GetMapping("/confirmed") 
	   public String confirmedOrders(Model model, HttpSession session) {
	       User user = (User) session.getAttribute("currentUser");
	       List<Order> orders = orderService.getOrdersByUserAndStatus(user, Order.CONFIRMED);
	       model.addAttribute("orders", orders);
	       return "user/orders";
	   }

	   @GetMapping("/shipping")
	   public String shippingOrders(Model model, HttpSession session) {
	       User user = (User) session.getAttribute("currentUser");
	       List<Order> orders = orderService.getOrdersByUserAndStatus(user, Order.IN_DELIVERY);
	       model.addAttribute("orders", orders);
	       return "user/orders"; 
	   }

	   @GetMapping("/delivered")
	   public String deliveredOrders(Model model, HttpSession session) {
	       User user = (User) session.getAttribute("currentUser");
	       List<Order> orders = orderService.getOrdersByUserAndStatus(user, Order.DELIVERED);
	       model.addAttribute("orders", orders);
	       return "user/orders";
	   }

	   @GetMapping("/cancelled")
	   public String cancelledOrders(Model model, HttpSession session) {
	       User user = (User) session.getAttribute("currentUser");
	       List<Order> orders = orderService.getOrdersByUserAndStatus(user, Order.CANCELLED);
	       model.addAttribute("orders", orders);
	       return "user/orders";
	   }
}
