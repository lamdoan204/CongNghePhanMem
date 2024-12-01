package com.Project.CongNghePhanMem.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import jakarta.servlet.http.HttpSession;

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
                newCart.setSum(0);
                newCart.setUser(user);
                cart = this.cartRepository.save(newCart);
            }

            // save cart detail
            // tim product
            Optional<Product> p = this.productRepository.findById(id);

            if (p.isPresent()) {
                Product realProduct = p.get();
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);

                if (oldDetail == null) {
                    CartDetail cartDetail = new CartDetail();

                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(1);

                    this.cartDetailRepository.save(cartDetail);

                    cart.setSum(cart.getSum() + 1);
                    this.cartRepository.save(cart);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }

            }

        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void handleRemoveCartDetail(int cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetailOptional.isPresent()) {
            CartDetail cartDetail = cartDetailOptional.get();

            Cart currentCart = cartDetail.getCart();
            // delete cart-detail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (currentCart.getSum() > 1) {
                // update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            } else {
                // delete cart (sum = 1)
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }
        }
    }

    @Override
    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    @Override
    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> searchProducts(String keyword, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Product findProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    // Xóa sản phẩm theo danh sách productID
    public void deleteProductsByIds(List<Integer> ids) {
        productRepository.deleteAllById(ids);
    }

    @Override
    public Page<Product> searchProductsByBrand(int brandId, String keyword, Pageable pageable) {
        if (keyword != null && !keyword.isEmpty()) {
            return productRepository.findByBrandIdAndNameContaining(brandId, keyword, pageable);
        }
        return productRepository.findByBrandId(brandId, pageable);
    }

    @Override
    public List<Product> findRelatedProducts(String description, String kind, int productId) {
        return productRepository.findRelatedProductsByDescriptionAndKind(description, kind, productId);
    }

    @Override
    public List<Product> productSearch(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Keyword cannot be null or empty");
        }

        // Gọi repository để tìm sản phẩm
        List<Product> products = productRepository.searchProducts(keyword);

        return products;  // Kiểm tra xem có sản phẩm nào được trả về không
    }

}
