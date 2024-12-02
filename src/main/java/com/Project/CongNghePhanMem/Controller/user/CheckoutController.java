package com.Project.CongNghePhanMem.Controller.user;


import java.util.List;
import java.util.Optional;

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
import com.Project.CongNghePhanMem.Entity.Promotion;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Service.IOrderService;
import com.Project.CongNghePhanMem.Service.Impl.CartService;
import com.Project.CongNghePhanMem.Service.Impl.PromotionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/checkout")
public class CheckoutController {

	@Autowired
	private CartService cartService;

	@Autowired
	private IOrderService orderService;
	
	@Autowired
    private PromotionService promotionService;


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
	public String placeOrder(
	        @RequestParam String fullName, 
	        @RequestParam String phone, 
	        @RequestParam String email,
	        @RequestParam String address, 
	        @RequestParam(required = false) String note,
	        @RequestParam boolean isPaidByCard,
	        @RequestParam(required = false) Integer promotionId,
	        HttpSession session,
	        RedirectAttributes redirectAttributes) {
	    try {
	        User currentUser = (User) session.getAttribute("currentUser");
	        Cart cart = cartService.getCurrentCart(session);
	        
	        if (currentUser == null || cart == null || cart.getCartDetails().isEmpty()) {
	            return "redirect:/user/cart";
	        }

	        // Cập nhật thông tin user
	        currentUser.setFullName(fullName);
	        currentUser.setPhone(phone);
	        currentUser.setEmail(email);

	        // Tính tổng tiền ban đầu
	        float finalPrice = cart.getTotalPrice();

	        // Xử lý khuyến mãi nếu có
	        if (promotionId != null) {
	            Optional<Promotion> promotion = promotionService.findById(promotionId);
	            if (promotion.isPresent()) {
	                // Lấy danh sách sản phẩm được áp dụng khuyến mãi
	                List<Integer> applicableProductIds = promotionService.getPromotionProductIds(promotionId);
	                float totalDiscount = 0;
	                
	                // Tính giảm giá cho từng sản phẩm
	                for (CartDetail detail : cart.getCartDetails()) {
	                    if (applicableProductIds.contains(detail.getProduct().getProductID())) {
	                        double itemTotal = detail.getPrice() * detail.getQuantity();
	                        double discount = itemTotal * promotion.get().getDiscountRate();
	                        totalDiscount += discount;
	                    }
	                }
	                
	                // Trừ tổng giảm giá vào giá cuối
	                finalPrice -= totalDiscount;
	            }
	        }

	        // Tạo đơn hàng với giá đã giảm
	        Order order = orderService.createOrder(currentUser, cart, isPaidByCard, finalPrice);

	        // Xóa giỏ hàng
	        cartService.clearCart(session);
	        
	        return "redirect:/user/orders";
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
