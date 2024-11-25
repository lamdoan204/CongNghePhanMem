package com.Project.CongNghePhanMem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.Project.CongNghePhanMem.Entity.Promotion;
import com.Project.CongNghePhanMem.Service.PromotionService;

@Controller
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping
    public String getAllPromotions(Model model) {
        model.addAttribute("promotions", promotionService.getAllPromotions());
        return "promotion_list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("promotion", new Promotion());
        return "promotion_form";
    }

    @PostMapping("/create")
    public String createPromotion(@ModelAttribute Promotion promotion) {
        promotionService.savePromotion(promotion);
        return "redirect:/promotions";
    }
    
    @GetMapping("/delete/{id}")
    public String deletePromotion(@PathVariable("id") int id) {
        promotionService.deletePromotionById(id);
        return "redirect:/promotions";
    }
}
