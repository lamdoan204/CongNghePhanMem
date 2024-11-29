package com.Project.CongNghePhanMem.Repository;

import com.Project.CongNghePhanMem.Entity.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Order;
import com.Project.CongNghePhanMem.Entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
	List<Order> findByUserOrderByOrderDateDesc(User user);


    // Tìm đơn hàng theo orderID
    Order findByOrderID(int orderID);

    // Tìm tất cả các đơn hàng có status
    List<Order> findByStatus(int status);
}
