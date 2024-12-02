package com.Project.CongNghePhanMem.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.dto.ProductDTO;

import jakarta.servlet.http.HttpSession;

public interface IProductService {
	void handleAddProductToCart(String email, int id);
	List<Product> fetchProducts();
	Page<Product> findAllProducts(Pageable pageable);
	// Lấy thông tin chi tiết sản phẩm theo ID
    ProductDTO getProductDetailById(int id);

    // Tạo mới sản phẩm
    void saveProductWithImage(ProductDTO productDTO, MultipartFile imageFile);

    // Cập nhật sản phẩm
    void updateProduct(int productID, ProductDTO productDTO, MultipartFile imageFile);

    // Xóa sản phẩm theo ID
    void deleteProductById(int id);

    // Xóa nhiều sản phẩm theo danh sách ID
    void deleteProducts(List<Integer> ids);

    // Tìm kiếm sản phẩm theo từ khóa và thương hiệu (với phân trang)
    Page<ProductDTO> searchProductsByBrand(int brandId, String keyword, int page, int size);
	Cart fetchByUser(User user);
	void handleRemoveCartDetail(int cartDetailId, HttpSession session);
	List<Product> findRelatedProducts(String description, String kind, int productId);
	
	List<Product> productSearch(String keyword);
	
	List<Product> findProductsByKinds(List<String> kinds);
	Product findProductById(int productId);
	
	 // Phương thức tạo ProductDTO mới cho quản lý sản phẩm
    ProductDTO getNewProductDTOForManager();

}
