package com.Project.CongNghePhanMem.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserRepository userRepo;

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

    @GetMapping("/")
    public String home() {
        return "user/home";
    }

    @GetMapping("/changePass")
    public String changePassword() {
        return "user/changePassword";
    }
}
