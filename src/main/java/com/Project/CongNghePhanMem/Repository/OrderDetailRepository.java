package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

	@Query(value = "SELECT WEEK(o.order_date) AS period, YEAR(o.order_date) AS year, SUM(od.price * od.quantity) AS totalRevenue " +
	        "FROM orders o " +
	        "JOIN order_detail od ON o.orderID = od.order_id " +
	        "JOIN products p ON od.product_id = p.productID " +
	        "JOIN brands b ON p.brand_id = b.brand_id " +
	        "WHERE b.brand_id = :brandId " +
	        "GROUP BY WEEK(o.order_date), YEAR(o.order_date) " +
	        "ORDER BY YEAR(o.order_date), WEEK(o.order_date)", 
	        nativeQuery = true)
	List<Object[]> findRevenueByWeek(@Param("brandId") int brandId);


	@Query(value = "SELECT MONTH(o.order_date) AS period, YEAR(o.order_date) AS year, SUM(od.price * od.quantity) AS totalRevenue " +
	        "FROM orders o " +
	        "JOIN order_detail od ON o.orderID = od.order_id " +
	        "JOIN products p ON od.product_id = p.productID " +
	        "JOIN brands b ON p.brand_id = b.brand_id " +
	        "WHERE b.brand_id = :brandId " +
	        "GROUP BY MONTH(o.order_date), YEAR(o.order_date) " +
	        "ORDER BY YEAR(o.order_date), MONTH(o.order_date)", 
	        nativeQuery = true)
	List<Object[]> findRevenueByMonth(@Param("brandId") int brandId);



	@Query(value = "SELECT QUARTER(o.order_date) AS period, YEAR(o.order_date) AS year, SUM(od.price * od.quantity) AS totalRevenue " +
	        "FROM orders o " +
	        "JOIN order_detail od ON o.orderID = od.order_id " +
	        "JOIN products p ON od.product_id = p.productID " +
	        "JOIN brands b ON p.brand_id = b.brand_id " +
	        "WHERE b.brand_id = :brandId " +
	        "GROUP BY QUARTER(o.order_date), YEAR(o.order_date) " +
	        "ORDER BY YEAR(o.order_date), QUARTER(o.order_date)", 
	        nativeQuery = true)
	List<Object[]> findRevenueByQuarter(@Param("brandId") int brandId);


	@Query(value = "SELECT YEAR(o.order_date) AS period, SUM(od.price * od.quantity) AS totalRevenue " +
	        "FROM orders o " +
	        "JOIN order_detail od ON o.orderID = od.order_id " +
	        "JOIN products p ON od.product_id = p.productID " +
	        "JOIN brands b ON p.brand_id = b.brand_id " +
	        "WHERE b.brand_id = :brandId " +
	        "GROUP BY YEAR(o.order_date) " +
	        "ORDER BY YEAR(o.order_date)", 
	        nativeQuery = true)
	List<Object[]> findRevenueByYear(@Param("brandId") int brandId);


}
