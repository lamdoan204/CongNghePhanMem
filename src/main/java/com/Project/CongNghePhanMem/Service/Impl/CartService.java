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

	@Autowired
	private ProductDetailService productDetailService;

	// Giảm số lượng sản phẩm
	public void decreaseQuantity(int cartDetailId) {
		CartDetail cartDetail = cartDetailRepository.findById(cartDetailId)
				.orElseThrow(() -> new RuntimeException("CartDetail not found"));

		if (cartDetail.getQuantity() > 1) { // Đảm bảo không giảm dưới 1
			cartDetail.setQuantity(cartDetail.getQuantity() - 1);
			cartDetailRepository.save(cartDetail);
		}
	}

	public CartDetail getCartDetail(int cartDetailId) {
		return cartDetailRepository.findById(cartDetailId)
				.orElseThrow(() -> new RuntimeException("CartDetail not found"));
	}

	// Tăng số lượng sản phẩm
	public void increaseQuantity(int cartDetailId) {
		CartDetail cartDetail = getCartDetail(cartDetailId);

		// Kiểm tra số lượng tồn kho
		if (!productDetailService.checkAvailableQuantity(cartDetail.getProduct(), cartDetail.getQuantity() + 1)) {
			throw new RuntimeException("Không đủ số lượng trong kho");
		}

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
		if (cart == null || cart.getCartDetails().isEmpty()) {
			return 0;
		}

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

		// Kiểm tra còn hàng không
		if (!productDetailService.isInStock(product)) {
			throw new RuntimeException("Sản phẩm đã hết hàng");
		}

		// Kiểm tra số lượng tồn kho
		if (!productDetailService.checkAvailableQuantity(product, quantity)) {
			throw new RuntimeException("Không đủ số lượng trong kho");
		}

		CartDetail existingDetail = findCartDetailByProduct(cart, product);

		if (existingDetail != null) {
			// Kiểm tra tổng số lượng sau khi thêm
			int newQuantity = existingDetail.getQuantity() + quantity;
			if (!productDetailService.checkAvailableQuantity(product, newQuantity)) {
				throw new RuntimeException("Số lượng yêu cầu vượt quá số lượng trong kho");
			}
			existingDetail.setQuantity(newQuantity);
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
				.filter(detail -> detail.getProduct().getProductID() == product.getProductID()).findFirst()
				.orElse(null);
	}
	
	// Thêm method kiểm tra trạng thái tồn kho cho CartDetail
    public boolean isCartDetailAvailable(CartDetail cartDetail) {
        return productDetailService.checkAvailableQuantity(
            cartDetail.getProduct(), 
            cartDetail.getQuantity()
        );
    }
}
