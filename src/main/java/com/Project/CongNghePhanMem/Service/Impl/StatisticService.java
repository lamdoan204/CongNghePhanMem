package com.Project.CongNghePhanMem.Service.Impl;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.RevenueStatistic;
import com.Project.CongNghePhanMem.Repository.OrderDetailRepository;
import com.Project.CongNghePhanMem.Service.IStatisticService;
import com.Project.CongNghePhanMem.dto.StockReport;

@Service
public class StatisticService implements IStatisticService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    

    @Override
    public List<RevenueStatistic> getRevenueByMonthAndProduct(int brandId) {
    	List<Object[]> results = orderDetailRepository.findRevenueByMonthAndProduct(brandId);
        List<RevenueStatistic> revenueStatistics = new ArrayList<>();

        for (Object[] row : results) {
            // Lấy các giá trị từ Object[]
            int period = (Integer) row[0];  // Tháng (period)
            int year = (Integer) row[1];    // Năm (year)
            String productName = (String) row[2];  // Tên sản phẩm (productName)
         // Khởi tạo biến orderDate
            LocalDate orderDate = null;
            Object value = row[3];

            // Kiểm tra kiểu và chuyển đổi đối tượng
            if (value instanceof java.sql.Date) {
                orderDate = ((java.sql.Date) value).toLocalDate(); // Chuyển java.sql.Date thành LocalDate
            } else if (value instanceof LocalDate) {
                orderDate = (LocalDate) value; // Nếu đã là LocalDate, không cần chuyển đổi
            } else {
                // Xử lý trường hợp nếu không phải kiểu mong đợi
                orderDate = LocalDate.now(); // Giá trị mặc định nếu không có giá trị hợp lệ
            }
              // Ngày đầu tiên của đơn hàng (orderDate)
            // Chuyển từ BigDecimal sang int hoặc double một cách đúng đắn
            int totalQuantitySold = (row[4] instanceof BigDecimal) ? ((BigDecimal) row[4]).intValue() : 0;  // totalQuantitySold
            int remainingQuantity = (row[5] instanceof BigDecimal) ? ((BigDecimal) row[5]).intValue() : 0;  // remainingQuantity
            double totalRevenue = 0.0;
            if (row[6] != null) {
                if (row[6] instanceof BigDecimal) {
                    totalRevenue = ((BigDecimal) row[6]).doubleValue();
                } else if (row[6] instanceof Double) {
                    totalRevenue = (Double) row[6];
                } else if (row[6] instanceof Integer) {
                    totalRevenue = ((Integer) row[6]).doubleValue();
                } else {
                    System.out.println("Unexpected type: " + row[6].getClass().getName());
                }
            }
            totalRevenue = Math.round(totalRevenue * 100.0) / 100.0;



            // Tạo đối tượng RevenueStatistic và thêm vào danh sách
            RevenueStatistic stat = new RevenueStatistic(period, year, productName, orderDate, totalQuantitySold, remainingQuantity, totalRevenue);
            revenueStatistics.add(stat);
        }

        return revenueStatistics;
    }

    
    public List<StockReport> getStockReport(int brandId) {
        List<Object[]> results =  orderDetailRepository.findStockReportByBrandId(brandId);
        List<StockReport> stockReports = new ArrayList<>();

        for (Object[] row : results) {
            LocalDate stockDate = (LocalDate) row[0];
            String productName = (String) row[1];
            String productKind = (String) row[2];
         // Kiểm tra kiểu của row[3] và ép kiểu Long thành Integer
            Long quantityInStock = (Long) row[3];  // Giả sử row[3] là Long
            int totalQuantityInStock = quantityInStock.intValue();  // Chuyển Long thành int
            
            StockReport stockReport = new StockReport(stockDate, productName, productKind, totalQuantityInStock);
            stockReports.add(stockReport);
        }
		return stockReports;
    
    }


}
