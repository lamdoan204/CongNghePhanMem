package com.Project.CongNghePhanMem.Repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    Optional<Promotion> findByCouponAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
        String coupon, LocalDate startDate, LocalDate endDate);
}
