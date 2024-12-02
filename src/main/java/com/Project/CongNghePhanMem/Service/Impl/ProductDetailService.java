package com.Project.CongNghePhanMem.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Product;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.ProductDetail;
import com.Project.CongNghePhanMem.Service.IProductDetailService;

@Service
public class ProductDetailService implements IProductDetailService {

	@Override
	public boolean isInStock(Product product) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkAvailableQuantity(Product product, int requestedQuantity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void saveProductDetail(ProductDetail productDetail) {
		// TODO Auto-generated method stub
		
	}





}
