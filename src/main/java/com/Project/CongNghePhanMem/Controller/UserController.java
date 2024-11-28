package com.Project.CongNghePhanMem.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private BCryptPasswordEncoder passEncoder;

	@ModelAttribute
    private void userDetails(Model m, Principal p, HttpSession session) {
        if (p != null) {
            // Kiểm tra user trong session
            User currentUser = (User) session.getAttribute("currentUser");
            
            if (currentUser == null) {
                // Nếu chưa có trong session, lấy từ database và lưu vào session
                String email = p.getName();
                currentUser = userRepo.findByEmail(email);
                if (currentUser != null) {
                    session.setAttribute("currentUser", currentUser);
                }
            }
            
            // Thêm vào model để view có thể sử dụng
            m.addAttribute("user", currentUser);
        }
    }

	@GetMapping("/")
	public String home(Model model, HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
		
		
		List<Product> products = this.productService.fetchProducts();
    	model.addAttribute("products", products);
		return "user/home";
	}

	@PostMapping("/updatePassword")
    public String updatePassword(HttpSession session, Principal p, 
            @RequestParam("oldPass") String oldPass,
            @RequestParam("newPass") String newPass) {
            
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        boolean check = passEncoder.matches(oldPass, currentUser.getPassword());
        if (check) {
            currentUser.setPassword(passEncoder.encode(newPass));
            User updatedUser = userRepo.save(currentUser);
            if (updatedUser != null) {
                // Cập nhật user trong session
                session.setAttribute("currentUser", updatedUser);
                session.setAttribute("msg", "Update Password successful!");
            } else {
                session.setAttribute("msg", "Something went wrong!");
            }
        } else {
            session.setAttribute("msg", "Old Password incorrect!");
        }
        return "redirect:/user/changePass";
    }

	@GetMapping("/changePass")
	public String changePassword(HttpSession session, Model model) {
		String msg = (String) session.getAttribute("msg");
		if (msg != null) {
			model.addAttribute("msg", msg);
			session.removeAttribute("msg");
		}
		return "user/changePassword";
	}
	


}
