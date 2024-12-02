package com.Project.CongNghePhanMem.dto;

import java.time.LocalDate;

public class StockReport {
   
    private LocalDate stockDate;            // Ngày nhập kho
    private String productName;             // Tên sản phẩm
    private String productKind;             // Loại sản phẩm
    private int totalQuantityInStock;      // Số lượng nhập kho

    public StockReport(LocalDate stockDate, String productName, String productKind, int totalQuantityInStock) {
        this.stockDate = stockDate;
        this.productName = productName;
        this.productKind = productKind;
        this.totalQuantityInStock = totalQuantityInStock;
    }

    // Getters và Setters
    public LocalDate getStockDate() {
        return stockDate;
    }

    public void setStockDate(LocalDate stockDate) {
        this.stockDate = stockDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductKind() {
        return productKind;
    }

    public void setProductKind(String productKind) {
        this.productKind = productKind;
    }

    public int getTotalQuantityInStock() {
        return totalQuantityInStock;
    }

    public void setTotalQuantityInStock(int totalQuantityInStock) {
        this.totalQuantityInStock = totalQuantityInStock;
    }
}
