package com.Project.CongNghePhanMem.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/profile")
    public ResponseEntity<User> getUserInfo(Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepo.findByEmail(email);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/")
    public String home(
            Model model, 
            HttpSession session,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        
        // Lấy sản phẩm có phân trang
        Page<Product> productPage = productService.findAllProducts(PageRequest.of(page, size));
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        
     
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", productPage.getNumber());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        
        return "user/home";
    }

	@PostMapping("/updatePassword")
    public String updatePassword(HttpSession session, Principal p, 
            @RequestParam("oldPass") String oldPass,
            @RequestParam("newPass") String newPass, RedirectAttributes redirectAttributes) {
            
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
                session.setAttribute("msg", "as went wrong!");
            }
        } else {
            session.setAttribute("msg", "Old Password incorrect!");
        }

        if (passEncoder.matches(oldPass, currentUser.getPassword())) {
            if (!isValidPassword(newPass)) {
                redirectAttributes.addFlashAttribute("msg", "Password must be at least 8 characters and contain special characters, digits, and uppercase letters!");
                return "redirect:/user/changePass";
            }

            currentUser.setPassword(passEncoder.encode(newPass));
            userRepo.save(currentUser);
            redirectAttributes.addFlashAttribute("msg", "Update Password successful!");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Old Password incorrect!");
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
	
	 private boolean isValidPassword(String password) {
	        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
	        return password.matches(regex);
	    }
	
	 
	 

}
