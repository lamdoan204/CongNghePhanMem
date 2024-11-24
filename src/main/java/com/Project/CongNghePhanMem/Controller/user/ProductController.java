package com.Project.CongNghePhanMem.Controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.Project.CongNghePhanMem.Service.Impl.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}


	@PostMapping("/add-product-to-cart/{id}")
	public String handleAddProductToCart(@PathVariable int id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String email = (String)session.getAttribute("email");
		
		int productId = id;
		
		this.productService.handleAddProductToCart(email, id);
		
		return "redirect:/";
	}
}
