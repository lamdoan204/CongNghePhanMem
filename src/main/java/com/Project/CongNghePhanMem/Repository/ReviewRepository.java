package com.Project.CongNghePhanMem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.Review;
public interface ReviewRepository extends JpaRepository<Review, Integer>{

	List<Review> findByProduct(Product product);
}