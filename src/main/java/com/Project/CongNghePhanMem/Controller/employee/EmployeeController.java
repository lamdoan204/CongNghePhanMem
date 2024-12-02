package com.Project.CongNghePhanMem.Controller.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Repository.OrderRepository;
import com.Project.CongNghePhanMem.Service.IOrderService;

@Controller
@RequestMapping("/employee/")
public class EmployeeController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private IOrderService orderService;

	@GetMapping("/")
	public String dangNhap() {
		return "employee/home";
	}

	@GetMapping("/employee")
	public String home() {
		return "employee" + "/";
	}

	@GetMapping("/orderManagement")
	public String ordermanagement(Model model) {
		model.addAttribute("allOrders", orderService.getAllOrders());
		model.addAttribute("pendingOrders", orderService.getPendingOrders());
		model.addAttribute("confirmedOrders", orderService.getConfirmedOrders());
		model.addAttribute("inDeliveryOrders", orderService.getInDeliveryOrders());
		model.addAttribute("deliveredOrders", orderService.getDeliveredOrders());
		model.addAttribute("cancelledOrders", orderService.getCancelledOrders());
		return "employee/orderManagement";
	}

	// Update the order status based on user selection
	@PostMapping("/updateOrderStatus/{orderID}")
	public String updateOrderStatus(@PathVariable("orderID") int orderId, @RequestParam("status") int status) {
		Order order = orderRepository.findById(orderId).orElse(null);
		if (order != null) {
			// Set the status using the constants from the Order class
			switch (status) {
			case 0:
				order.setStatus(Order.PENDING);
				break;
			case 1:
				order.setStatus(Order.CONFIRMED);
				break;
			case 2:
				order.setStatus(Order.IN_DELIVERY);
				break;
			case 3:
				order.setStatus(Order.DELIVERED);
				break;
			case 4:
				order.setStatus(Order.CANCELLED);
				break;
			default:
				throw new IllegalArgumentException("Invalid status value");
			}
			orderRepository.save(order);
		}
		return "redirect:/employee/orderManagement"; // Redirect back to the orders management page
	}

	// Tìm kiếm đơn hàng
	@GetMapping("/searchOrders")
	public String searchOrders(@RequestParam("searchTerm") String searchTerm, Model model) {
		List<Order> searchResults = orderService.searchOrders(searchTerm); // Tìm kiếm đơn hàng

		if (searchResults == null || searchResults.isEmpty()) {
			model.addAttribute("message", "Không có kết quả"); // Thêm thông báo nếu không có kết quả
		} else {
			model.addAttribute("orders", searchResults); // Thêm danh sách đơn hàng vào model
		}

		// In ra console danh sách hóa đơn (hoặc đơn hàng)
		System.out.println("Danh sách hóa đơn tìm được:");
		for (Order order : searchResults) {
			System.out.println(order); // In toàn bộ thông tin của từng đơn hàng
		}

//			model.addAttribute("allOrders", searchResults); // Cập nhật lại kết quả tìm kiếm vào model
		return "employee/orderManagement"; // Trả lại trang đơn hàng với kết quả tìm kiếm
	}

	@GetMapping("/orderDetails/{orderID}")
	public String getOrderDetails(@PathVariable int orderID, Model model) {
		Order order = orderService.getOrderById(orderID);
		System.out.println(order); 
		model.addAttribute("order", order);
		return "/employee/orderDetail";
	}

	@GetMapping("/customerSupport")
	public String customersupport() {
		return "employee/customerSupport";
	}

	@GetMapping("/chat/{customerId}")
	public String showChatBox(@PathVariable String customerId, Model model) {
		// Truyền customerId vào view để hiển thị
		model.addAttribute("customerId", customerId);
		return "employee/chatbox"; // Trang HTML cho chatbox
	}

}
