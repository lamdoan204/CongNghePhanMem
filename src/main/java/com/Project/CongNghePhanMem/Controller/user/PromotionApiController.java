package com.Project.CongNghePhanMem.Controller.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Project.CongNghePhanMem.Entity.Promotion;
import com.Project.CongNghePhanMem.Service.Impl.PromotionService;

@RestController
@RequestMapping("/api/promotions")
public class PromotionApiController {
    
    @Autowired
    private PromotionService promotionService;
    
    @GetMapping("/validate") 
public ResponseEntity<?> validateCoupon(@RequestParam String coupon) {
    try {
        Optional<Promotion> promotion = promotionService.findValidPromotionByCoupon(coupon);
        
        if (promotion.isPresent()) {
            Promotion validPromotion = promotion.get();
            Map<String, Object> response = new HashMap<>();
            response.put("valid", true);
            response.put("promotionId", validPromotion.getId());
            response.put("discountRate", validPromotion.getDiscountRate());
            
            // Thêm danh sách sản phẩm được áp dụng
            List<Integer> applicableProductIds = promotionService.getPromotionProductIds(validPromotion.getId());
            response.put("applicableProducts", applicableProductIds);
            
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("valid", false);
            return ResponseEntity.ok(response);
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                           .body("Error validating coupon");
    }
}
        try {
            Optional<Promotion> promotion = promotionService.findValidPromotionByCoupon(coupon);
            
            if (promotion.isPresent()) {
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("promotionId", promotion.get().getId());
                response.put("discountRate", promotion.get().getDiscountRate());
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("valid", false);
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Error validating coupon");
        }
    }
}
