package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    // Query cho doanh thu theo tuần và nhiều loại 'kind'
    @Query(value = "SELECT WEEK(o.order_date) AS period, YEAR(o.order_date) AS year, " +
            "GROUP_CONCAT(DISTINCT p.kind ORDER BY p.kind) AS kinds, " +
            "SUM(od.price * od.quantity) AS totalRevenue " +
            "FROM orders o " +
            "JOIN order_detail od ON o.orderID = od.order_id " +
            "JOIN products p ON od.product_id = p.productID " +
            "JOIN brands b ON p.brand_id = b.brand_id " +
            "WHERE b.brand_id = :brandId " +
            "GROUP BY WEEK(o.order_date), YEAR(o.order_date)", // Loại bỏ ORDER BY
            nativeQuery = true)
    List<Object[]> findRevenueByWeekAndKind(@Param("brandId") int brandId);

    // Query cho doanh thu theo tháng và nhiều loại 'kind'
    @Query(value = "SELECT MONTH(o.order_date) AS period, YEAR(o.order_date) AS year, " +
            "GROUP_CONCAT(DISTINCT p.kind ORDER BY p.kind) AS kinds, " +
            "SUM(od.price * od.quantity) AS totalRevenue " +
            "FROM orders o " +
            "JOIN order_detail od ON o.orderID = od.order_id " +
            "JOIN products p ON od.product_id = p.productID " +
            "JOIN brands b ON p.brand_id = b.brand_id " +
            "WHERE b.brand_id = :brandId " +
            "GROUP BY MONTH(o.order_date), YEAR(o.order_date)", // Loại bỏ ORDER BY
            nativeQuery = true)
    List<Object[]> findRevenueByMonthAndKind(@Param("brandId") int brandId);

    // Query cho doanh thu theo quý và nhiều loại 'kind'
    @Query(value = "SELECT QUARTER(o.order_date) AS period, YEAR(o.order_date) AS year, " +
            "GROUP_CONCAT(DISTINCT p.kind ORDER BY p.kind) AS kinds, " +
            "SUM(od.price * od.quantity) AS totalRevenue " +
            "FROM orders o " +
            "JOIN order_detail od ON o.orderID = od.order_id " +
            "JOIN products p ON od.product_id = p.productID " +
            "JOIN brands b ON p.brand_id = b.brand_id " +
            "WHERE b.brand_id = :brandId " +
            "GROUP BY QUARTER(o.order_date), YEAR(o.order_date)", // Loại bỏ ORDER BY
            nativeQuery = true)
    List<Object[]> findRevenueByQuarterAndKind(@Param("brandId") int brandId);

    // Query cho doanh thu theo năm và nhiều loại 'kind'
    @Query(value = "SELECT YEAR(o.order_date) AS period, " +
            "GROUP_CONCAT(DISTINCT p.kind ORDER BY p.kind) AS kinds, " +
            "SUM(od.price * od.quantity) AS totalRevenue " +
            "FROM orders o " +
            "JOIN order_detail od ON o.orderID = od.order_id " +
            "JOIN products p ON od.product_id = p.productID " +
            "JOIN brands b ON p.brand_id = b.brand_id " +
            "WHERE b.brand_id = :brandId " +
            "GROUP BY YEAR(o.order_date)", // Loại bỏ ORDER BY
            nativeQuery = true)
    List<Object[]> findRevenueByYearAndKind(@Param("brandId") int brandId);
}
