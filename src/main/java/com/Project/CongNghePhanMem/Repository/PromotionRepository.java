package com.Project.CongNghePhanMem.Repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Project.CongNghePhanMem.Entity.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
	
	Optional<Promotion> findByCouponAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
		    String coupon, LocalDate startDate, LocalDate endDate);
	
}
