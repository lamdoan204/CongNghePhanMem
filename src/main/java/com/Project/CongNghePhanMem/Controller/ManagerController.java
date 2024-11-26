package com.Project.CongNghePhanMem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Project.CongNghePhanMem.Entity.Brand;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Service.IManagerService;
import com.Project.CongNghePhanMem.Service.IUserService;
import com.Project.CongNghePhanMem.Service.Impl.DepartmentService;
import com.Project.CongNghePhanMem.Service.Impl.ManagerService;
import com.Project.CongNghePhanMem.Service.Impl.UserService;
import com.Project.CongNghePhanMem.configs.CustomUserDetails;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Project.CongNghePhanMem.Entity.Department;
import com.Project.CongNghePhanMem.Service.BrandService;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    IManagerService managerService = new ManagerService();
    @Autowired
    IUserService userService = new UserService();
    @Autowired
    BrandService brandService = new BrandService();
    @Autowired
    DepartmentService departmentService = new DepartmentService();

    @GetMapping("/")
    public String getMethodName(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Người dùng chưa đăng nhập");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();
        // Lấy User từ UserService
        User manager = userService.getUserByUserId(userId);
        if (manager == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        // Gọi Service để lấy tên thương hiệu
        String brand = managerService.get_DepartmentName(manager);
        model.addAttribute("brand", brand);
        return "manager/index"; // Trả về view `manager/index`
    }

    @GetMapping("/employeeManagement")
    public String employee(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Người dùng chưa đăng nhập");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();
        // Lấy User từ UserService
        User manager = userService.getUserByUserId(userId);
        if (manager == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        // Gọi Service để lấy tên thương hiệu
        String brand = managerService.get_DepartmentName(manager);
        model.addAttribute("brand", brand);
        return "manager/employeemanagement";
    }

    @PostMapping("/employeeManagement")
    public String postMethodName(
            Model model,
            @RequestParam("fullname") String fullname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam("address") String address) {

        if (fullname.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank() || address.isBlank()) {
            model.addAttribute("error", "Vui lòng điền đầy đủ thông tin!");
            return "manager/employeeManagement"; 
        }
        
        User user = userService.getUserByEmail(email);
        if (user != null ) {
            model.addAttribute("error", "Email đã tồn tại");
            return "manager/employeeManagement"; 
        }
        user = userService.getUserByPhone(phone);
        if (user != null ) {
            model.addAttribute("error", "Số điện thoại đã tồn tại");
            return "manager/employeeManagement"; 
        }
        // thêm nhân viên vào bảng Users
        user = new User();
        user.setFullName(fullname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setAddress(address);
        user.setPassword(password);
        user.setRole("EMPLOYEE");
        user.setEnabled(true);
        managerService.add_Employee(user);

        // thêm nhân viên vào Phòng ban
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Người dùng chưa đăng nhập");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();
        // Lấy User từ UserService
        User manager = userService.getUserByUserId(userId);
        if (manager == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        // Gọi Service để lấy tên thương hiệu
        String brand_name = managerService.get_DepartmentName(manager);
        Brand brand = brandService.getBrandbyBrand(brand_name);
        
        Department department = departmentService.getDepartmentByBrandId(brand.getBrandId());
        user = userService.getUserByEmail(email);

        departmentService.addEmployeeToDepartment(department.getId(), user);
        
        return "redirect:/manager/employeeManagement";
    }

}
