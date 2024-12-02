package com.Project.CongNghePhanMem.Service;

import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.ProductDetail;

@Service
public interface IProductDetailService {

	boolean isInStock(Product product);

	boolean checkAvailableQuantity(Product product, int requestedQuantity);
	 void saveProductDetail(ProductDetail productDetail);



}
