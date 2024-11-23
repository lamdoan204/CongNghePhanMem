package com.Project.CongNghePhanMem.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IUserService;

import jakarta.servlet.http.HttpSession;




@Controller
public class PageController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserRepository userRepo;
	
	@ModelAttribute
	private void userDetails(Model m, Principal p) {
		if(p!= null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			
			m.addAttribute("user", user);	
		}
	}
	
    @GetMapping("/")
    public String Home() {
        return "index" ;
    }
   
    @GetMapping("/it_home_dark")
    public String Home_dark() {
        return "it_home_dark" ;
    }
    
    @GetMapping("/it_home")
    public String Home_white() {
        return "it_home" ;
    }
    
    
    @GetMapping("/login")
    public String login() {
    	return "login";
    }
    
    @GetMapping("/signin")
    public String signin() {
    	return "login";
    }
    
    @GetMapping("/register")
    public String register(HttpSession session, Model model) {

        String msg = (String) session.getAttribute("msg");
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        return "register";
    }

    
    
    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user, HttpSession session) {
        boolean emailExists = userService.checkEmail(user.getEmail());
        if (emailExists) {
            session.setAttribute("msg", "Email already exists!");
        } else {
            User createdUser = userService.createUser(user);
            if (createdUser != null) {
                session.setAttribute("msg", "Register successful!");
            } else {
                session.setAttribute("msg", "Something went wrong! Please try again.");
            }
        }
        return "redirect:/register";
    }

    @GetMapping("/manager/home")
    public String getMethodName() {
        return "manager/index";
    }
    @GetMapping("/manage-employees")
    public String getMethodEmployeeManagerment() {
        return "/manager/employeemanagement";
    }
    
    

}
