package com.Project.CongNghePhanMem.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    private void userDetails(Model m, Principal p) {
        if (p != null) {
            String email = p.getName();
            User user = userRepo.findByEmail(email);
            if (user != null) {
                m.addAttribute("user", user);
            }
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
	public String home(Model model) {
		List<Product> products = this.productService.fetchProducts();
    	model.addAttribute("products", products);
		return "user/home";
	}

    @PostMapping("/updatePassword")
    public String UpdatePassword(Principal p, 
                                 @RequestParam("oldPass") String oldPass, 
                                 @RequestParam("newPass") String newPass, 
                                 RedirectAttributes redirectAttributes) {

        if (p == null) {
            redirectAttributes.addFlashAttribute("msg", "Please log in first!");
            return "redirect:/login";
        }

        String email = p.getName();
        User user = userRepo.findByEmail(email);

        if (user == null) {
            redirectAttributes.addFlashAttribute("msg", "User not found!");
            return "redirect:/user/changePass";
        }

        if (passEncoder.matches(oldPass, user.getPassword())) {
            if (!isValidPassword(newPass)) {
                redirectAttributes.addFlashAttribute("msg", "Password must be at least 8 characters and contain special characters, digits, and uppercase letters!");
                return "redirect:/user/changePass";
            }

            user.setPassword(passEncoder.encode(newPass));
            userRepo.save(user);
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
