package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.ProductDetail;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer>{
	// Sửa tên phương thức để tìm kiếm theo productID của Product
    ProductDetail findByProduct_productID(int productID);
		ProductDetail findByProduct(Product product);
	   
	   @Query("SELECT pd FROM ProductDetail pd WHERE pd.product.id = :productId")
	   ProductDetail findByProductId(@Param("productId") int productId);
	   
	   @Query("SELECT pd.quantity FROM ProductDetail pd WHERE pd.product.id = :productId")
	   Integer getQuantityByProductId(@Param("productId") int productId);
	   
	   @Query("SELECT CASE WHEN pd.quantity > 0 THEN true ELSE false END FROM ProductDetail pd WHERE pd.product.id = :productId")
	   boolean isInStock(@Param("productId") int productId);
	   
	   @Query("SELECT CASE WHEN pd.quantity >= :requiredQuantity THEN true ELSE false END FROM ProductDetail pd WHERE pd.product.id = :productId")
	   boolean hasAvailableQuantity(@Param("productId") int productId, @Param("requiredQuantity") int requiredQuantity);
}
