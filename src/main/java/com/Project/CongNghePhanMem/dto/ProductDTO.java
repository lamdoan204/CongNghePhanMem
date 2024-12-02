package com.Project.CongNghePhanMem.dto;


import java.time.LocalDate;

public class ProductDTO {

    private int productID;
    private String name;
    private float price;
    private String image;
    private String kind;
    private int brandId;
    private String brandName; // Tên thương hiệu (nếu cần)
    private String description;
    private int quantity; // Số lượng trong kho
    private LocalDate date; // Ngày cập nhật số lượng

    // Constructors
    public ProductDTO() {
    }

    public ProductDTO(int productID, String name, float price, String image, String kind, int brandId, String brandName,
                      String description, int quantity, LocalDate date) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.image = image;
        this.kind = kind;
        this.brandId = brandId;
        this.brandName = brandName;
        this.description = description;
        this.quantity = quantity;
        this.date = date;
    }

    // Getters and Setters
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productID=" + productID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", kind='" + kind + '\'' +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", date=" + date +
                '}';
    }
}

