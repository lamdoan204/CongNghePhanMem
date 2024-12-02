package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
	
	@Query("SELECT pd.date AS stockDate, " +
		       "       p.name AS productName, " +
		       "       p.kind AS productKind, " +
		       "       ANY_VALUE(pd.quantity) AS totalQuantityInStock " + // Thay đổi từ SUM sang ANY_VALUE
		       "FROM ProductDetail pd " +
		       "JOIN pd.product p " +
		       "LEFT JOIN OrderDetail od ON pd.product.id = od.product.id " +
		       "LEFT JOIN od.order o " +
		       "JOIN p.brand b " +
		       "WHERE b.id = :brandId " +
		       "GROUP BY pd.date, p.name, p.kind " +
		       "ORDER BY pd.date, p.name")
		List<Object[]> findStockReportByBrandId(@Param("brandId") int brandId);

		@Query(value = "SELECT " +
	               "MONTH(o.order_date) AS period, " +
	               "YEAR(o.order_date) AS year, " +
	               "p.name AS productName, " +
	               "SUM(od.quantity) AS totalQuantitySold, " +  // Tổng số lượng sản phẩm đã bán
	               "(pd.quantity - SUM(od.quantity)) AS remainingQuantity, " +  // Trừ đi tổng số lượng đã bán
	               "SUM(od.price * od.quantity) AS totalRevenue " +
	               "FROM orders o " +
	               "JOIN order_detail od ON o.orderid = od.order_id " +
	               "JOIN products p ON od.product_id = p.productid " +
	               "JOIN product_detail pd ON p.productid = pd.product_id " +
	               "WHERE o.status = 3 " +  // Chỉ tính các đơn hàng đã hoàn thành
	               "GROUP BY MONTH(o.order_date), " +
	               "YEAR(o.order_date), " +
	               "p.name, " +
	               "pd.quantity " +
	               "ORDER BY YEAR(o.order_date), MONTH(o.order_date), p.name", 
	       nativeQuery = true)
	List<Object[]> findRevenueByMonthAndProduct(@Param("brandId") int brandId);



}