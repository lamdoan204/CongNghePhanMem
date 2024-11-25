package com.Project.CongNghePhanMem.Controller.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IProductService;
import com.Project.CongNghePhanMem.Service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RestController("userProductController")
public class ProductController {
	
	private final IProductService productService;
	private final IUserService userService;
	
	@Autowired
	private UserRepository userRepository;


	public ProductController(IProductService productService, IUserService userService) {
		super();
		this.productService = productService;
		this.userService = userService;
	}

	@PostMapping("/add-product-to-cart/{id}")
	public String handleAddProductToCart(@PathVariable int id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String email = (String)session.getAttribute("email");
		
		int productId = id;
		
		this.productService.handleAddProductToCart(email, id);
		
		return "redirect:/";
	}
	
	@GetMapping("/cart")
	public String shoppingCart(Model model, Principal principal) {
		// Kiểm tra xem người dùng đã đăng nhập chưa
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email);
            model.addAttribute("user", user);  // Thêm đối tượng user vào model
        } else {
            model.addAttribute("user", null);  // Nếu chưa đăng nhập, truyền user là null
        }
		
		return "user/shoppingCart";
	}
}
