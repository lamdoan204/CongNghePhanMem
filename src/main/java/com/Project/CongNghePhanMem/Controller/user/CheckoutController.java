package com.Project.CongNghePhanMem.Controller.user;


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
import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Entity.User;
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
			@RequestParam String address, @RequestParam(required = false) String note, HttpSession session, @RequestParam boolean isPaidByCard,
			RedirectAttributes redirectAttributes) {
		try {
			// Lấy thông tin user và cart
			User currentUser = (User) session.getAttribute("currentUser");
			Cart cart = cartService.getCurrentCart(session);

			if (currentUser == null || cart == null || cart.getCartDetails().isEmpty()) {
				return "redirect:/user/cart";
			}

			// Cập nhật thông tin user nếu cần
			currentUser.setFullName(fullName);
			currentUser.setPhone(phone);
			currentUser.setEmail(email);

			// Tạo đơn hàng
			Order order = orderService.createOrder(currentUser, cart, isPaidByCard);

			// Xóa giỏ hàng sau khi đặt hàng thành công
			// cartService.clearCart(session);
			
			return "/user/orders";


		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi đặt hàng: " + e.getMessage());
			return "redirect:/user/checkout";
		}
	}

	@GetMapping("/order/success/{orderId}")
	public String orderSuccess(@PathVariable("orderId") int orderId, Model model) {
		Order order = orderService.findById(orderId);
		if (order != null) {
			model.addAttribute("order", order);
			//return "user/orderSuccess";
			return "/user/orders";
		}
		return "redirect:/user";
	}

	
}
