package com.Project.CongNghePhanMem.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.CartDetail;
import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.CartDetailRepository;
import com.Project.CongNghePhanMem.Repository.CartRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CartService {

	@Autowired
    private CartDetailRepository cartDetailRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
    // Giảm số lượng sản phẩm
    public void decreaseQuantity(int cartDetailId) {
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("CartDetail not found"));
        
        if (cartDetail.getQuantity() > 1) { // Đảm bảo không giảm dưới 1
            cartDetail.setQuantity(cartDetail.getQuantity() - 1);
            cartDetailRepository.save(cartDetail);
        }
    }

    // Tăng số lượng sản phẩm
    public void increaseQuantity(int cartDetailId) {
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("CartDetail not found"));
        
        cartDetail.setQuantity(cartDetail.getQuantity() + 1);
        cartDetailRepository.save(cartDetail);
    }
    
 // Thêm các phương thức mới cho checkout
    public Cart getCurrentCart(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return null;
        }
        
        Cart cart = cartRepository.findByUser(currentUser);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(currentUser);
            cart = cartRepository.save(cart);
        }
        
        return cart;
    }
    
    public float calculateTotalPrice(Cart cart) {

        
        float total = 0;
        for (CartDetail detail : cart.getCartDetails()) {
            total += detail.getPrice() * detail.getQuantity();
        }
        return total;
    }
    
    public void clearCart(HttpSession session) {
        Cart cart = getCurrentCart(session);
        if (cart != null) {
            // Xóa tất cả cart details
            cartDetailRepository.deleteAll(cart.getCartDetails());
            // Cập nhật cart
            cart.getCartDetails().clear();
            cartRepository.save(cart);
        }
    }
    
    public void removeCartDetail(int cartDetailId) {
        CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new RuntimeException("CartDetail not found"));
        
        Cart cart = cartDetail.getCart();
        cart.getCartDetails().remove(cartDetail);
        cartRepository.save(cart);
        cartDetailRepository.delete(cartDetail);
    }
    
    public void addProductToCart(Cart cart, Product product, int quantity) {
        CartDetail existingDetail = findCartDetailByProduct(cart, product);
        
        if (existingDetail != null) {
            existingDetail.setQuantity(existingDetail.getQuantity() + quantity);
            cartDetailRepository.save(existingDetail);
        } else {
            CartDetail newDetail = new CartDetail();
            newDetail.setCart(cart);
            newDetail.setProduct(product);
            newDetail.setQuantity(quantity);
            newDetail.setPrice(product.getPrice());
            cart.getCartDetails().add(newDetail);
            cartDetailRepository.save(newDetail);
        }
        
        cartRepository.save(cart);
    }
    
    private CartDetail findCartDetailByProduct(Cart cart, Product product) {
        return cart.getCartDetails().stream()
                .filter(detail -> detail.getProduct().getProductID() == product.getProductID())
                .findFirst()
                .orElse(null);
    }
}

