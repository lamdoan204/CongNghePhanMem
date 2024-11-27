package com.Project.CongNghePhanMem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Project.CongNghePhanMem.Entity.Department;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Service.BrandService;
import com.Project.CongNghePhanMem.Service.IManagerService;
import com.Project.CongNghePhanMem.Service.IUserService;
import com.Project.CongNghePhanMem.Service.Impl.DepartmentService;
import com.Project.CongNghePhanMem.Service.Impl.ManagerService;
import com.Project.CongNghePhanMem.Service.Impl.UserService;

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

        User manager = userService.getUserCurentLogged();
        if (manager == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        // Gọi Service để lấy tên thương hiệu
        String brand = managerService.get_DepartmentName(manager);
        model.addAttribute("brand", brand);
        // lấy một số sản phẩm, nhân viên doanh thu và chương trình khuyến mãi

        return "manager/index"; // Trả về view `manager/index`
    }

    @GetMapping("/employeeManagement")
    public String employee(Model model) {

        User manager = userService.getUserCurentLogged();
        if (manager == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        // Gọi Service để lấy tên thương hiệu
        String brand = managerService.get_DepartmentName(manager);
        model.addAttribute("brand", brand);
        // lấy danh sách nhân viên
        List<User> employee = managerService.getListEmployee(manager);

        model.addAttribute("employees", employee);

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
        if (user != null) {
            model.addAttribute("error", "Email đã tồn tại");
            return "manager/employeeManagement";
        }
        user = userService.getUserByPhone(phone);
        if (user != null) {
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
        user.setAccounNonLocked(true);
        managerService.add_Employee(user);

        // thêm nhân viên vào Phòng ban
        User manager = userService.getUserCurentLogged();
        if (manager == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
       

        Department department = departmentService.getDepartmentByManager(manager);
        user = userService.getUserByEmail(email);

        departmentService.addEmployeeToDepartment(department.getId(), user);

        return "redirect:/manager/employeeManagement";
    }

    @PostMapping("/deleteEmployee")
    public String postDeleteEmployee(@RequestParam("userId") Integer userId) {
        User manager = userService.getUserCurentLogged();
        if (manager == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }

        User employee = userService.getUserById(userId);

        Department department = departmentService.getDepartmentByManager(manager);

        departmentService.deleteEmployeeToDepartment(department.getId(), employee);
        managerService.delete_Employee(employee);

        return "redirect:/manager/";
    }

}
