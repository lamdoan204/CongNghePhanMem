package com.Project.CongNghePhanMem.Controller.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.CartDetail;
import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Entity.OrderDetail;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.OrderDetailRepository;
import com.Project.CongNghePhanMem.Repository.OrderRepository;
import com.Project.CongNghePhanMem.Service.IOrderService;
import com.Project.CongNghePhanMem.Service.Impl.CartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/checkout")
public class CheckoutController {

	@Autowired
	private CartService cartService;

	@Autowired
	private IOrderService orderService;

	@GetMapping
	public String showCheckoutPage(Model model, HttpSession session) {
		// Lấy user hiện tại từ session
		User currentUser = (User) session.getAttribute("currentUser");
		if (currentUser == null) {
			return "redirect:/login";
		}

		// Lấy giỏ hàng hiện tại
		Cart cart = cartService.getCurrentCart(session);
		if (cart == null || cart.getCartDetails().isEmpty()) {
			return "redirect:/user/cart";
		}

		// Thêm thông tin vào model
		model.addAttribute("cartDetails", cart.getCartDetails());
		model.addAttribute("totalPrice", cartService.calculateTotalPrice(cart));
		model.addAttribute("user", currentUser);

		// Pre-fill thông tin user vào form
		model.addAttribute("fullName", currentUser.getFullName());
		model.addAttribute("email", currentUser.getEmail());
		model.addAttribute("phone", currentUser.getPhone());

		return "user/checkout";
	}

	@PostMapping("/place-order")
	public String placeOrder(@RequestParam String fullName, @RequestParam String phone, @RequestParam String email,
			@RequestParam String address, @RequestParam(required = false) String note, HttpSession session,
			RedirectAttributes redirectAttributes) {
		try {
			// Lấy thông tin user và cart
			User currentUser = (User) session.getAttribute("currentUser");
			Cart cart = cartService.getCurrentCart(session);

			if (currentUser == null || cart == null || cart.getCartDetails().isEmpty()) {
				return "redirect:/cart";
			}

			// Cập nhật thông tin user nếu cần
			currentUser.setFullName(fullName);
			currentUser.setPhone(phone);
			currentUser.setEmail(email);

			// Tạo đơn hàng
			Order order = orderService.createOrder(currentUser, cart);

			// Xóa giỏ hàng sau khi đặt hàng thành công
			// cartService.clearCart(session);

			// Thông báo thành công
			redirectAttributes.addFlashAttribute("successMessage",
					"Đặt hàng thành công! Mã đơn hàng của bạn là: " + order.getOrderID());

			return "redirect:/order/success/" + order.getOrderID();

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi đặt hàng: " + e.getMessage());
			return "redirect:/checkout";
		}
	}

	@GetMapping("/order/success/{orderId}")
	public String orderSuccess(@PathVariable("orderId") int orderId, Model model) {
		Order order = orderService.findById(orderId);
		if (order != null) {
			model.addAttribute("order", order);
			return "user/orderSuccess";
		}
		return "redirect:/";
	}

	@PostMapping("/process-payment")
	public String processPayment(@RequestParam String cardName, @RequestParam String cardNumber,
			@RequestParam String expiryDate, @RequestParam String cvv, HttpSession session,
			RedirectAttributes redirectAttributes) {
		try {
			// Lấy thông tin từ session
			User currentUser = (User) session.getAttribute("currentUser");
			Cart cart = cartService.getCurrentCart(session);

			if (currentUser == null || cart == null) {
				return "redirect:/cart";
			}

			// Giả lập xử lý thanh toán
			boolean paymentSuccess = processPaymentWithBank(cardNumber, expiryDate, cvv, 987.99);

			if (paymentSuccess) {
				// Tạo đơn hàng
				Order order = orderService.createOrder(currentUser, cart);

				// Xóa giỏ hàng
				cartService.clearCart(session);

				// Chuyển đến trang quản lý đơn hàng thay vì trang success
				redirectAttributes.addFlashAttribute("successMessage",
						"Thanh toán thành công! Mã đơn hàng của bạn là: " + order.getOrderID());

				return "redirect:/user/orders"; // Chuyển đến trang quản lý đơn hàng

			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "Thanh toán thất bại!");
				return "redirect:/checkout";
			}

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage",
					"Có lỗi xảy ra trong quá trình thanh toán: " + e.getMessage());
			return "redirect:/checkout";
		}
	}

	// Phương thức giả lập xử lý thanh toán
	private boolean processPaymentWithBank(String cardNumber, String expiryDate, String cvv, double amount) {
		// Đây là nơi tích hợp với cổng thanh toán thật
		// Hiện tại return true để test
		return true;
	}
}
