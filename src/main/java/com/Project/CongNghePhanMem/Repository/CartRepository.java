package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Cart;
import com.Project.CongNghePhanMem.Entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
	public Cart findByUser(User user);
}
