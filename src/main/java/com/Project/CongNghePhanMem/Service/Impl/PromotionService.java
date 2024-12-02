package com.Project.CongNghePhanMem.Service.Impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.Promotion;
import com.Project.CongNghePhanMem.Repository.PromotionRepository;

import io.jsonwebtoken.lang.Collections;

@Service
public class PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;
    
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public Promotion savePromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }
    
    
    
    public void deletePromotionById(int id) {
        promotionRepository.deleteById(id);
    }
    
    public Optional<Promotion> findById(Integer id) {
        return promotionRepository.findById(id);
    }
    
    public List<Integer> getPromotionProductIds(int promotionId) {
        // Lấy promotion
        Optional<Promotion> promotion = promotionRepository.findById(promotionId);
        if (promotion.isPresent()) {
            // Lấy danh sách sản phẩm từ ManyToMany relationship
            return promotion.get().getApplicableProducts()
                .stream()
                .map(Product::getProductID)
                .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    // Các phương thức khác giữ nguyên
    public Optional<Promotion> findValidPromotionByCoupon(String coupon) {
        LocalDate today = LocalDate.now();
        return promotionRepository.findByCouponAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            coupon, today, today);
    }
}