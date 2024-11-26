package com.Project.CongNghePhanMem.Controller.Manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.Project.CongNghePhanMem.Entity.Promotion;
import com.Project.CongNghePhanMem.Service.Impl.PromotionService;

@Controller
@RequestMapping("/manager")
public class ManagerController {
    
    @Autowired
    private PromotionService promotionService;

    // Các mapping hiện có của ManagerController
    @GetMapping("/")
    public String getMethodName() {
        return "manager/index";
    }

    @GetMapping("/home")
    public String getMethodHome() {
        return "manager/index";
    }

    @GetMapping("/manage-employees")
    public String getMethodEmployeeManagerment() {
        return "/manager/employeemanagement";
    }

    // Thêm các mapping từ PromotionController
    @GetMapping("/promotions")
    public String showPromotionList(Model model) {
        model.addAttribute("promotions", promotionService.getAllPromotions());
        return "manager/promotion-list"; // Cập nhật đường dẫn template
    }

    @GetMapping("/promotions/create")
    public String showCreateForm(Model model) {
        model.addAttribute("promotion", new Promotion());
        return "manager/promotion_form"; // Cập nhật đường dẫn template
    }

    @PostMapping("/promotions/create")
    public String createPromotion(@ModelAttribute Promotion promotion) {
        promotionService.savePromotion(promotion);
        return "redirect:/manager/promotions";
    }

    @GetMapping("/promotions/delete/{id}")
    public String deletePromotion(@PathVariable("id") int id) {
        promotionService.deletePromotionById(id);
        return "redirect:/manager/promotions";
    }
}