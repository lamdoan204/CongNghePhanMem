package com.Project.CongNghePhanMem.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserRepository userRepo;

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

	@GetMapping("/")
	public String home() {
		return "user/home";
	}

	@PostMapping("/updatePassword")
	public String UpdatePassword(HttpSession session, Principal p, @RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass) {
		String email = p.getName();

		User user = userRepo.findByEmail(email);

		boolean check = passEncoder.matches(oldPass, user.getPassword());

		if (check) {
			user.setPassword(passEncoder.encode(newPass));
			User u = userRepo.save(user);

			if (u != null) {
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
