package com.Project.CongNghePhanMem.Controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.Project.CongNghePhanMem.Service.Impl.CartService;

@Controller
@RequestMapping("/user/update-cart")
public class CartController {

	@Autowired
	private CartService cartService;

	// Xử lý giảm số lượng sản phẩm
	@PostMapping("/remove/{id}")
	@ResponseBody
	public ResponseEntity<?> decreaseQuantity(@PathVariable("id") int cartDetailId) {
		try {
			cartService.decreaseQuantity(cartDetailId);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// Xử lý tăng số lượng sản phẩm
	@PostMapping("/add/{id}")
	@ResponseBody
	public ResponseEntity<?> increaseQuantity(@PathVariable("id") int cartDetailId) {
		try {
			cartService.increaseQuantity(cartDetailId);
			return ResponseEntity.ok().build();
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
