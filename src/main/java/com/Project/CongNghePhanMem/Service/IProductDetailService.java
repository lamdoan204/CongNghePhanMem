package com.Project.CongNghePhanMem.Service;

import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Product;

@Service
public interface IProductDetailService {

	boolean isInStock(Product product);

	boolean checkAvailableQuantity(Product product, int requestedQuantity);
	 void saveProductDetail(ProductDetail productDetail);



}
