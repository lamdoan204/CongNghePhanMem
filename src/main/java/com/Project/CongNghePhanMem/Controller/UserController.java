package com.Project.CongNghePhanMem.Controller;

import java.security.Principal;
import java.util.List;

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

import com.Project.CongNghePhanMem.Entity.Article;
import com.Project.CongNghePhanMem.Entity.Product;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.ReviewRepository;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IProductService;
import com.Project.CongNghePhanMem.Service.Impl.ArticleService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired 
	private ArticleService articleService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private IProductService productService;
	

	

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
	
	 @GetMapping("/article")
	 public String article(Model model) {
		 List<Article> article = articleService.getAllArticles();
		 model.addAttribute("articles", article);
		 
		 return "user/article";
	 }
	 

}
