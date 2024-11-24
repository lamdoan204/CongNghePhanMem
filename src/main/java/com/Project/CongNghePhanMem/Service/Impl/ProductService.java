package com.Project.CongNghePhanMem.Service.Impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.CartDetail;
import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.CartDetailRepository;
import com.Project.CongNghePhanMem.Repository.CartRepository;
import com.Project.CongNghePhanMem.Repository.ProductRepository;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IProductService;

@Service
public class ProductService implements IProductService {

	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	private final CartDetailRepository cartDetailRepository;
	private final UserRepository userRepository;

	private final UserService userService;

	public ProductService(ProductRepository productRepository, CartRepository cartRepository,
			CartDetailRepository cartDetailRepository, UserRepository userRepository, UserService userService) {
		super();
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
		this.cartDetailRepository = cartDetailRepository;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	// logic luu product vao gio hang
	@Override
	public void handleAddProductToCart(String email, int id) {
		// check user da co cart chua? neu chua -> tao moi
		User user = this.userService.getUserById(id);
		if (user != null) {
			Cart cart = this.cartRepository.findByUser(user);

			if (cart == null) {
				// tao moi cart
				Cart newCart = new Cart();
				newCart.setSum(1);
				newCart.setUser(user);
				cart = this.cartRepository.save(newCart);
			}

			// save cart detail
			// tim product
			Optional<Product> p = this.productRepository.findById(id);

			if (p.isPresent()) {
				Product realProduct = p.get();
				CartDetail cartDetail = new CartDetail();

				cartDetail.setCart(cart);
				cartDetail.setProduct(realProduct);
				cartDetail.setPrice(realProduct.getPrice());
				cartDetail.setQuantity(1);

				this.cartDetailRepository.save(cartDetail);

			}

		} else {
			throw new RuntimeException("User not found");
		}
	}
}
