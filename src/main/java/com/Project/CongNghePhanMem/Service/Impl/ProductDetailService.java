package com.Project.CongNghePhanMem.Service.Impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.ProductDetail;
import com.Project.CongNghePhanMem.Repository.ProductDetailRepository;
import com.Project.CongNghePhanMem.Service.IProductDetailService;


@Service 
public class ProductDetailService implements IProductDetailService {
	@Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public void saveProductDetail(ProductDetail productDetail) {
        // Lưu ProductDetail vào cơ sở dữ liệu
        productDetailRepository.save(productDetail);
    }
	@Override
	public boolean checkAvailableQuantity(Product product, int requestedQuantity) {
		ProductDetail detail = productDetailRepository.findByProduct(product);
		return detail != null && detail.getQuantity() >= requestedQuantity;
	}

	@Override
	public boolean isInStock(Product product) {
		ProductDetail detail = productDetailRepository.findByProduct(product);
		return detail != null && detail.getQuantity() > 0;
	}
	
}
