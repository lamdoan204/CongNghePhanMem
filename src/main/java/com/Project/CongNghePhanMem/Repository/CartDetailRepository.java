package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.CartDetail;


@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
	
}