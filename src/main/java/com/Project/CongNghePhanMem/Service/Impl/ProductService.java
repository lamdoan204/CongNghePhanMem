package com.Project.CongNghePhanMem.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Repository.ProductRepository;
import com.Project.CongNghePhanMem.Service.IProductService;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

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
}
