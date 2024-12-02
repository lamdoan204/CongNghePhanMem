package com.Project.CongNghePhanMem.Service.Impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Promotion;
import com.Project.CongNghePhanMem.Repository.PromotionRepository;

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
    
    
    public Optional<Promotion> findValidPromotionByCoupon(String coupon) {
        LocalDate today = LocalDate.now();
        return promotionRepository.findByCouponAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            coupon, today, today);
    }
}