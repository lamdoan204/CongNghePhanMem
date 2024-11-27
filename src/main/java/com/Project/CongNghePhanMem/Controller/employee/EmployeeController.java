package com.Project.CongNghePhanMem.Controller.employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee/")
public class EmployeeController {
	@GetMapping("/")
    public String dangNhap() {
       return "employee/home";
    }
    
    @GetMapping("/employee")
    public String home() {
        return "employee" + "/";
    }
    
    @GetMapping("/orderManagement")
    public String orderManagement() {
        return "employee/orderManagement";
    }
    
    @GetMapping("/customerSupport")
    public String customerSupport() {
        return "employee/customerSupport";
    }
}
