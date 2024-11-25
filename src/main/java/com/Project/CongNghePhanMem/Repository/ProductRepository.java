package com.Project.CongNghePhanMem.Repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Tìm kiếm theo tên sản phẩm (hoặc các tiêu chí khác nếu cần)
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    
}
