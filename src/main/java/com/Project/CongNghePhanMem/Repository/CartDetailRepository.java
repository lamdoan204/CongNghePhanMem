package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.CartDetail;
import com.Project.CongNghePhanMem.Entity.Product;


@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
	boolean existsByCartAndProduct(Cart cart, Product product);
	CartDetail findByCartAndProduct(Cart cart, Product product);

}
