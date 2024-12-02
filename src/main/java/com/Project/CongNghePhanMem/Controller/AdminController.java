package com.Project.CongNghePhanMem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Project.CongNghePhanMem.Entity.Brand;
import com.Project.CongNghePhanMem.Entity.Department;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Service.BrandService;
import com.Project.CongNghePhanMem.Service.IUserService;
import com.Project.CongNghePhanMem.Service.Impl.DepartmentService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/admin/")
public class AdminController {

	@Autowired
	private IUserService userService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private DepartmentService departmentService;

	
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public String home(Model model, @RequestParam(defaultValue = "0") int page) {
		
		Page<Department> departments = departmentService.getDepartment1(PageRequest.of(page, 10));
		List<Brand> brands = brandService.getAllBrand();
		List<Brand> brandswithoutD = brandService.getListBrandWithouD();
		model.addAttribute("brandswithoutD", brandswithoutD);
		model.addAttribute("brands", brands);
		model.addAttribute("departments", departments.getContent());
		model.addAttribute("totalPages", departments.getTotalPages());
		return "admin/home";
	}

	@PostMapping("/createManager")
	@PreAuthorize("hasRole('ADMIN')")
	public String createUser(@ModelAttribute User user, 
	                         @RequestParam("password") String password,
	                         @RequestParam("brand") int brandId, 
	                         RedirectAttributes redirectAttributes) {

	  
	    boolean emailExists = userService.checkEmail(user.getEmail());
	    if (emailExists) {
	        redirectAttributes.addFlashAttribute("msg", "Email đã tồn tại!");
	        return "redirect:/admin/";
	    }

	  
	    Brand brand = brandService.getBrandByBrandId(brandId);
	    if (brand == null) {
	        redirectAttributes.addFlashAttribute("msg", "Brand không tồn tại!");
	        return "redirect:/admin/";
	    }

	    Department existingDepartment = departmentService.getDepartmentByBrandId(brand);

	    if (existingDepartment != null && existingDepartment.getManager() != null) {
	        User currentManager = existingDepartment.getManager();

	        if ("ADMIN".equals(currentManager.getRole())) {
	            existingDepartment.setManager(null); 
	            departmentService.save(existingDepartment); 
	        } else {
	            redirectAttributes.addFlashAttribute("msg", "Brand đã có Manager quản lý!");
	            return "redirect:/admin/";
	        }
	    }

	    user.setPassword(password);
	    User createdUser = userService.CreateManager(user);

	    if (createdUser == null) {
	        redirectAttributes.addFlashAttribute("msg", "Đã xảy ra lỗi khi tạo Manager. Vui lòng thử lại.");
	        return "redirect:/admin/";
	    }

	  
	    if (existingDepartment != null) {
	        existingDepartment.setManager(createdUser);
	        departmentService.save(existingDepartment);
	    } else {
	        departmentService.insert(brand, createdUser);
	    }

	    redirectAttributes.addFlashAttribute("msg", "Thêm thành công!");
	    return "redirect:/admin/";
	}


	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/deleteManager")
	public String deleteManager(@RequestParam("managerId") int managerId, RedirectAttributes redirectAttributes) {
	    
	    User manager = userService.getUserById(managerId);
	    if (manager == null) {
	        redirectAttributes.addFlashAttribute("msg", "Không tìm thấy Manager với ID: " + managerId);
	        return "redirect:/admin/";
	    }
	    if ("ADMIN".equals(manager.getRole())) {
	        redirectAttributes.addFlashAttribute("msg", "Không thể xóa Manager có vai trò ADMIN.");
	        return "redirect:/admin/";
	    }
	    
	    
	    Department department = departmentService.getDepartmentByManager(manager);
	    if (department != null) {
	    	 if ("ADMIN".equals(department.getManager().getRole())){
	             redirectAttributes.addFlashAttribute("msg", "Không thể xóa vì ADMIN đang quản lí phòng ban.");
	             return "redirect:/admin/";
	         }
	       
	        User admin = userService.getUserCurentLogged(); 
	        department.setManager(admin); 
	        departmentService.save(department); 
	    }

	    
	    boolean isDeleted = userService.deleteManager(managerId);
	    if (isDeleted) {
	        redirectAttributes.addFlashAttribute("msg", "Manager đã được xóa thành công!");
	    } else {
	        redirectAttributes.addFlashAttribute("msg", "Đã xảy ra lỗi khi xóa Manager.");
	    }

	    return "redirect:/admin/";
	}

	@PostMapping("/createBrand")
	public String createBrand(@RequestParam("brandName") String nameBrand) {
		brandService.addBrand(nameBrand);
		return "redirect:/admin/";
	}
	@PostMapping("/deleteBrand")
	public String deleteBrand(@RequestParam("brandId") int brandId) {
		brandService.deleteBrand(brandId);
		
		return "redirect:/admin/";
	}
	
	


}
