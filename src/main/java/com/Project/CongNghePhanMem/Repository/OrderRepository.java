package com.Project.CongNghePhanMem.Repository;

import com.Project.CongNghePhanMem.Entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	// Tìm đơn hàng theo orderID
	Order findByOrderID(int orderID);

	List<Order> findAll();

	// Tìm tất cả các đơn hàng có status
	List<Order> findByStatus(int status);

	// Tìm kiếm đơn hàng theo Mã Hóa Đơn
	List<Order> findByOrderID(Integer orderID);

	// Tìm kiếm các hóa đơn theo số điện thoại khách hàng
	List<Order> findByUserPhoneContaining(String phone);
	
//	// Truy vấn đơn hàng theo orderID và trạng thái khóa
//    Optional<Order> findByOrderIDAndLocked(Integer orderID, boolean locked);   
}