package com.Project.CongNghePhanMem.Repository;

import com.Project.CongNghePhanMem.Entity.Order;
import com.google.api.gax.paging.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	List<Order> findByUserOrderByOrderDateDesc(User user);


	// Tìm đơn hàng theo orderID
	Order findByOrderID(int orderID);

	List<Order> findAll();

	// Tìm tất cả các đơn hàng có status
	List<Order> findByStatus(int status);

	// Tìm kiếm đơn hàng theo Mã Hóa Đơn
	List<Order> findByOrderID(Integer orderID);

	// Tìm kiếm các hóa đơn theo số điện thoại khách hàng
	List<Order> findByUserPhoneContaining(String phone);
	
    Order findByOrderID(int orderID);
    
    List<Order> findByUserAndStatusOrderByOrderDateDesc(User user, int status);
}
