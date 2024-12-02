package com.Project.CongNghePhanMem.Service.Impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.CartDetail;
import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.ProductDetail;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.CartDetailRepository;
import com.Project.CongNghePhanMem.Repository.CartRepository;
import com.Project.CongNghePhanMem.Repository.ProductDetailRepository;
import com.Project.CongNghePhanMem.Repository.ProductRepository;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IProductService;
import com.Project.CongNghePhanMem.dto.ProductDTO;

import jakarta.servlet.http.HttpSession;


@Service
public class ProductService implements IProductService {

	private final ProductRepository productRepository;
	

	private final FileService fileService;
	private final ProductDetailRepository productDetailRepository;
	private final CartRepository cartRepository;
	private final CartDetailRepository cartDetailRepository;
	private final UserRepository userRepository;
	private final UserService userService;
	
	public ProductService(ProductRepository productRepository, FileService fileService,
			ProductDetailRepository productDetailRepository, CartRepository cartRepository,
			CartDetailRepository cartDetailRepository, UserRepository userRepository, UserService userService) {
		super();
		this.productRepository = productRepository;
		this.fileService = fileService;
		this.productDetailRepository = productDetailRepository;
		this.cartRepository = cartRepository;
		this.cartDetailRepository = cartDetailRepository;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	 
	

	@Override
	public void handleAddProductToCart(String email, int id) {
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
    public ProductDTO getProductDetailById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));
        return convertToDTO(product);
    }

    @Override
    public void saveProductWithImage(ProductDTO productDTO, MultipartFile imageFile) {
        Product product = convertToEntity(productDTO);
        try {
            if (productDTO.getBrandId() == 0) {
                throw new RuntimeException("Brand ID không hợp lệ!");
            }

            if (!imageFile.isEmpty()) {
                String fileName = fileService.uploadFile(imageFile, "src/main/resources/static/images/products");
                product.setImage(fileName);
            }

            productRepository.save(product);

            ProductDetail productDetail = new ProductDetail();
            productDetail.setProduct(product);
            productDetail.setQuantity(productDTO.getQuantity());
            productDetail.setDate(LocalDate.now());

            productDetailRepository.save(productDetail);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lưu sản phẩm: " + e.getMessage());
        }
    }

    @Override
    public void updateProduct(int id, ProductDTO productDTO, MultipartFile imageFile) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));
        try {
            if (!imageFile.isEmpty()) {
                String fileName = fileService.uploadFile(imageFile, "src/main/resources/static/images/products");
                if (product.getImage() != null) {
                    fileService.deleteFile("src/main/resources/static/images/products", product.getImage());
                }
                product.setImage(fileName);
            }
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setKind(productDTO.getKind());
            product.setDescription(productDTO.getDescription());
            productRepository.save(product);

            ProductDetail productDetail = productDetailRepository.findByProduct_productID(id);
            if (productDetail == null) {
                productDetail = new ProductDetail();
                productDetail.setProduct(product);
            }
            productDetail.setQuantity(productDTO.getQuantity());
            productDetailRepository.save(productDetail);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật sản phẩm: " + e.getMessage());
        }
    }

    @Override
    public void deleteProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));
        try {
        	fileService.deleteFile("src/main/resources/static/images/products", product.getImage());
            // Xóa chi tiết sản phẩm nếu có (sử dụng cascade)
            productRepository.delete(product); // Xóa sản phẩm, JPA sẽ tự động xóa ProductDetail
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
    }


    @Override
    public void deleteProducts(List<Integer> ids) {
    	List<Product> products = productRepository.findByProductIDIn(ids);
        if (products.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm nào để xóa.");
        }
        try {
            // Duyệt qua danh sách các sản phẩm cần xóa
            for (Product product : products) {
                // Nếu có ảnh, xóa ảnh khỏi thư mục
                if (product.getImage() != null) {
                    fileService.deleteFile("src/main/resources/static/images/products", product.getImage());
                }
                productRepository.delete(product);
            }
            
            // Xóa chi tiết sản phẩm (product detail) trước khi xóa sản phẩm chính
            //productDetailRepository.deleteByProductIDs(ids);
      
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa sản phẩm: " + e.getMessage());
        }
    }

    @Override
    public Page<ProductDTO> searchProductsByBrand(int brandId, String keyword, int page, int size) {
        return productRepository.findByBrandIdAndNameContaining(brandId, keyword, PageRequest.of(page, size))
                .map(this::convertToDTO);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDetail detail = productDetailRepository.findByProduct_productID(product.getProductID());
        return new ProductDTO(
                product.getProductID(),
                product.getName(),
                product.getPrice(),
                product.getImage(),
                product.getKind(),
                product.getBrandId(),
                product.getBrand() != null ? product.getBrand().getBrand() : null,
                product.getDescription(),
                detail != null ? detail.getQuantity() : 0,
                detail != null ? detail.getDate() : null
        );
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setProductID(dto.getProductID());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setKind(dto.getKind());
        product.setDescription(dto.getDescription());
        product.setBrandId(dto.getBrandId());
        return product;
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




	@Override
	public Product findProductById(int productId) {
		return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại với ID: " + productId));
	}
	 @Override
	    public ProductDTO getNewProductDTOForManager() {
	        // Trả về một ProductDTO mặc định với các giá trị khởi tạo, ví dụ quantity = 0
	        ProductDTO productDTO = new ProductDTO();
	        productDTO.setQuantity(0); // Giá trị mặc định
	        return productDTO;
	    }
	
}
