package com.Project.CongNghePhanMem.Repository;

import com.Project.CongNghePhanMem.Entity.ProductDetail;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
	// Sửa tên phương thức để tìm kiếm theo productID của Product
    ProductDetail findByProduct_productID(int productID);
    
  

}
