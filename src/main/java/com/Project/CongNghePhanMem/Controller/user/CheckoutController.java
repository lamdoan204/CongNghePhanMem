package com.Project.CongNghePhanMem.Controller.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.Project.CongNghePhanMem.Service.Impl.CartService;

import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired 
    private OrderRepository orderRepository;
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    
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
            return "redirect:/cart";
        }
        
        model.addAttribute("cartDetails", cart.getCartDetails());
        model.addAttribute("totalPrice", cartService.calculateTotalPrice(cart));
        model.addAttribute("user", currentUser);
        
        return "user/checkout";
    }
    
    @PostMapping("/place-order")
    public String placeOrder(@RequestParam String address,
                           @RequestParam String phone,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        try {
            // Lấy thông tin user và cart
            User currentUser = (User) session.getAttribute("currentUser");
            Cart cart = cartService.getCurrentCart(session);
            
            if (currentUser == null || cart == null || cart.getCartDetails().isEmpty()) {
                return "redirect:/cart";
            }
            
            // Tạo đơn hàng mới
            Order order = new Order();
            order.setUser(currentUser);
            order.setOrderDate(new Date(System.currentTimeMillis()));
            order.setStatus(Order.PENDING);
            order.setTotalPrice(cartService.calculateTotalPrice(cart));
            
            // Lưu order trước để có ID
            order = orderRepository.save(order);
            
            // Tạo chi tiết đơn hàng
            List<OrderDetail> orderDetails = new ArrayList<>();
            
            for (CartDetail cartDetail : cart.getCartDetails()) {
                OrderDetail detail = new OrderDetail();
                detail.setOrder(order);
                detail.setProduct(cartDetail.getProduct());
                detail.setQuantity(cartDetail.getQuantity());
                detail.setPrice(cartDetail.getPrice());
                orderDetails.add(detail);
                orderDetailRepository.save(detail);
            }
            
            order.setOrderDetails(orderDetails);
            orderRepository.save(order);
            
            // Xóa giỏ hàng
            cartService.clearCart(session);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Đặt hàng thành công! Mã đơn hàng của bạn là: " + order.getOrderID());
            
            return "redirect:/order/success/" + order.getOrderID();
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Có lỗi xảy ra khi đặt hàng: " + e.getMessage());
            return "redirect:/checkout";
        }
    }
}

