package com.Project.CongNghePhanMem.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.ProductDetail;
import com.Project.CongNghePhanMem.Repository.ProductDetailRepository;
import com.Project.CongNghePhanMem.Service.IProductDetailService;

import jakarta.transaction.Transactional;

@Service 
public class ProductDetailService implements IProductDetailService {
	@Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public void saveProductDetail(ProductDetail productDetail) {
        // Lưu ProductDetail vào cơ sở dữ liệu
        productDetailRepository.save(productDetail);
    }

    

    

}
