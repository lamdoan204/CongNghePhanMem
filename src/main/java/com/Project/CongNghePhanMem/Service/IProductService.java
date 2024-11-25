package com.Project.CongNghePhanMem.Service;

import java.util.List;

import com.Project.CongNghePhanMem.Entity.Product;

public interface IProductService {
	void handleAddProductToCart(String email, int id);
	List<Product> fetchProducts();


}
