package com.Project.CongNghePhanMem.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Project.CongNghePhanMem.Entity.Product;

public interface IProductService {
	void handleAddProductToCart(String email, int id);
	List<Product> fetchProducts();
	Page<Product> findAllProducts(Pageable pageable);
    Page<Product> searchProducts(String keyword, Pageable pageable);
    Product findProductById(int id);
    Product saveProduct(Product product);
    void deleteProduct(int id);
    public void deleteProductsByIds(List<Integer> ids);

}