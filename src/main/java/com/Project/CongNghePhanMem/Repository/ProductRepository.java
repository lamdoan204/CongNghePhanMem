package com.Project.CongNghePhanMem.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	// Tính số sản phẩm theo brand_id
    long countByBrandId(int brandId);
	

    List<Product> findByProductIDIn(List<Integer> ids);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByBrandId(int brandId, Pageable pageable);
    

    Page<Product> findByBrandIdAndNameContaining(int brandId, String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (p.description = :description OR p.kind = :kind) AND p.productID != :productId")
    List<Product> findRelatedProductsByDescriptionAndKind(@Param("description") String description, 
                                                          @Param("kind") String kind, 
                                                          @Param("productId") int productId);

    @Query("SELECT p FROM Product p " +
    	       "JOIN p.brand b " +
    	       "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    	       "LOWER(p.kind) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    	       "LOWER(b.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    	       "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    	List<Product> searchProducts(@Param("keyword") String keyword);

    List<Product> findByKindIn(List<String> kinds);

}
