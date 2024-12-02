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
import com.Project.CongNghePhanMem.Repository.ProductRepository;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IProductService;
import com.Project.CongNghePhanMem.dto.ProductDTO;

import jakarta.servlet.http.HttpSession;


@Service
public class ProductService implements IProductService {



	 
	


    
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
	public List<Product> findProductsByKinds(List<String> kinds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDTO getProductDetailById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveProductWithImage(ProductDTO productDTO, MultipartFile imageFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProduct(int productID, ProductDTO productDTO, MultipartFile imageFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<ProductDTO> searchProductsByBrand(int brandId, String keyword, int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void handleAddProductToCart(String email, int id) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public List<Product> fetchProducts() {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Page<Product> findAllProducts(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void deleteProductById(int id) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void deleteProducts(List<Integer> ids) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public Cart fetchByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void handleRemoveCartDetail(int cartDetailId, HttpSession session) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public List<Product> findRelatedProducts(String description, String kind, int productId) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public List<Product> productSearch(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public Product findProductById(int productId) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public ProductDTO getNewProductDTOForManager() {
		// TODO Auto-generated method stub
		return null;
	}

}
