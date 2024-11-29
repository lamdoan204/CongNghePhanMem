package com.Project.CongNghePhanMem.Controller.user;

import java.net.http.HttpRequest;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.CartDetail;
import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IProductService;
import com.Project.CongNghePhanMem.Service.IUserService;
import com.Project.CongNghePhanMem.Service.Impl.CartService;
import com.Project.CongNghePhanMem.Service.Impl.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {
    
    @Autowired
    private IProductService productService;
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private CartService cartService;
    
    @PostMapping("/add-product-to-cart/{id}")
    public String handleAddProductToCart(@PathVariable int id, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        
        // Kiểm tra đăng nhập
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        // Lấy thông tin sản phẩm
        Product product = productService.findProductById(id);
        if (product == null) {
            return "redirect:/";
        }
        
        try {
            // Lấy giỏ hàng hiện tại
            Cart cart = cartService.getCurrentCart(session);
            // Thêm sản phẩm vào giỏ với số lượng mặc định là 1
            cartService.addProductToCart(cart, product, 1);
            
            return "redirect:/cart";
        } catch (Exception e) {
            // Log error
            return "redirect:/";
        }
    }
    
    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/login";
        }
        
        // Lấy giỏ hàng hiện tại
        Cart cart = cartService.getCurrentCart(session);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            float totalPrice = cartService.calculateTotalPrice(cart);
            
            model.addAttribute("cartDetails", cartDetails);
            model.addAttribute("totalPrice", String.format("%.2f", totalPrice));
        } else {
            model.addAttribute("cartDetails", new ArrayList<CartDetail>());
            model.addAttribute("totalPrice", "0.00");
        }
        
        return "user/shoppingCart";
    }
    
    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartDetail(@PathVariable int id, HttpServletRequest request) {
        try {
            cartService.removeCartDetail(id);
            return "redirect:/cart";
        } catch (Exception e) {
            // Log error
            return "redirect:/cart";
        }
    }
}
