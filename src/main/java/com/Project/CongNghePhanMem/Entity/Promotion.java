package com.Project.CongNghePhanMem.Entity;
import java.sql.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;
    private float discountRate;
    private String coupon;

    @ManyToMany
    @JoinTable(
        name = "promotion_product",
        joinColumns = @JoinColumn(name = "promotion_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> applicableProducts;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public float getDiscountRate() {
        return discountRate;
    }
    public void setDiscountRate(float discountRate) {
        this.discountRate = discountRate;
    }
    public String getCoupon() {
        return coupon;
    }
    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
    public List<Product> getApplicableProducts() {
        return applicableProducts;
    }
    public void setApplicableProducts(List<Product> applicableProducts) {
        this.applicableProducts = applicableProducts;
    }

    public Promotion(int id, String name, String description, Date startDate, Date endDate, float discountRate,
            String coupon, List<Product> applicableProducts) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountRate = discountRate;
        this.coupon = coupon;
        this.applicableProducts = applicableProducts;
    }

    public Promotion() {
        super();
    }
}