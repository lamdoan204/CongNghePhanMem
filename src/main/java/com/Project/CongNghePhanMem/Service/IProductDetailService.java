package com.Project.CongNghePhanMem.Service;

import com.Project.CongNghePhanMem.Entity.ProductDetail;

import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Product;

@Service
public interface IProductDetailService {
	void saveProductDetail(ProductDetail productDetail);

	boolean isInStock(Product product);

	boolean checkAvailableQuantity(Product product, int requestedQuantity);

}

