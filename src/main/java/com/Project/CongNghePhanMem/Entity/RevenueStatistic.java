package com.Project.CongNghePhanMem.Entity;

import java.time.LocalDate;


public class RevenueStatistic {
    private int period;             // Tuần, Tháng, Quý hoặc Năm
    private int year;
    private String productName;     // Tên sản phẩm
    private LocalDate orderDate;         // Ngày đầu tiên của đơn hàng trong nhóm
    private int totalQuantitySold;  // Tổng số lượng bán
    private int remainingQuantity;  // Số lượng còn lại
    private double totalRevenue;    // Tổng doanh thu

    public RevenueStatistic(int period, int year, String productName,
                            LocalDate orderDate, int totalQuantitySold,
                            int remainingQuantity, double totalRevenue) {
        this.period = period;
        this.year = year;
        this.productName = productName;
        this.orderDate = orderDate;
        this.totalQuantitySold = totalQuantitySold;
        this.remainingQuantity = remainingQuantity;
        this.totalRevenue = totalRevenue;
    }

    // Getters và Setters
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalQuantitySold() {
        return totalQuantitySold;
    }

    public void setTotalQuantitySold(int totalQuantitySold) {
        this.totalQuantitySold = totalQuantitySold;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
