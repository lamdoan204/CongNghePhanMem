package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

	@Query("SELECT pd.date AS stockDate, " + "       p.name AS productName, " + "       p.kind AS productKind, "
			+ "       MAX(pd.quantity) AS totalQuantityInStock " + // Sử dụng MAX thay cho ANY_VALUE
			"FROM ProductDetail pd " + "JOIN pd.product p "
			+ "LEFT JOIN OrderDetail od ON pd.product.id = od.product.id " + "LEFT JOIN od.order o " + "JOIN p.brand b "
			+ "WHERE b.id = :brandId " + "GROUP BY pd.date, p.name, p.kind " + "ORDER BY pd.date, p.name")
	List<Object[]> findStockReportByBrandId(@Param("brandId") int brandId);

	@Query(value = "SELECT " + "DATEPART(MONTH, o.order_date) AS period, " + // Thay MONTH bằng DATEPART
			"DATEPART(YEAR, o.order_date) AS year, " + // Thay YEAR bằng DATEPART
			"p.name AS productName, " + "SUM(od.quantity) AS totalQuantitySold, "
			+ "(pd.quantity - SUM(od.quantity)) AS remainingQuantity, " + "SUM(od.price * od.quantity) AS totalRevenue "
			+ "FROM orders o " + "JOIN order_detail od ON o.orderid = od.order_id "
			+ "JOIN products p ON od.product_id = p.productid "
			+ "JOIN product_detail pd ON p.productid = pd.product_id " + "WHERE o.status = 4 "
			+ "GROUP BY DATEPART(YEAR, o.order_date), " + // Thay đổi cho GROUP BY
			"DATEPART(MONTH, o.order_date), " + "p.name, " + "pd.quantity "
			+ "ORDER BY DATEPART(YEAR, o.order_date), DATEPART(MONTH, o.order_date), p.name", nativeQuery = true)
	List<Object[]> findRevenueByMonthAndProduct(@Param("brandId") int brandId);

}