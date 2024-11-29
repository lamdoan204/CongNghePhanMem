package com.Project.CongNghePhanMem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Project.CongNghePhanMem.Entity.Brand;
import com.Project.CongNghePhanMem.Entity.Department;
import com.Project.CongNghePhanMem.Service.BrandService;
import com.Project.CongNghePhanMem.Service.Impl.DepartmentService;

@Controller
@RequestMapping("/admin/")
public class AdminController {
	
	@Autowired
    private BrandService brandService;
	
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping("/")
	private String home(Model model) {
		List<Department> deparment = departmentService.getDepartment();
		List<Brand> brands = brandService.getAllBrand();
		
		 model.addAttribute("brands", brands); 
		 model.addAttribute("departments", deparment); 
		return "admin/home";
	}
}
