package com.Project.CongNghePhanMem.Controller.user;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String decreaseQuantity(@PathVariable("id") int cartDetailId) {
        cartService.decreaseQuantity(cartDetailId);
        return "redirect:/user/cart"; // Redirect về trang giỏ hàng
    }

    // Xử lý tăng số lượng sản phẩm
    @PostMapping("/add/{id}")
    public String increaseQuantity(@PathVariable("id") int cartDetailId) {
        cartService.increaseQuantity(cartDetailId);
        return "redirect:/user/cart"; // Redirect về trang giỏ hàng
    }
}
