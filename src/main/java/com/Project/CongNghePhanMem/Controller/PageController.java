package com.Project.CongNghePhanMem.Controller;

import java.security.Principal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IUserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;


@Controller
public class PageController {
	
	@Autowired
    private JavaMailSender mailSender;
	
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
    public String createUser(@ModelAttribute User user, HttpSession session, @RequestParam("password") String password, @RequestParam("password1") String password1) {
        boolean emailExists = userService.checkEmail(user.getEmail()); 
        if (!emailExists) {
        	if(password.equals(password1)) {
        		User createdUser = userService.createUser(user);
                if (createdUser != null) {
                    session.setAttribute("msg", "Register successful!");
                } else {
                    session.setAttribute("msg", "Something went wrong! Please try again.");
                }
        	}else {
        		 session.setAttribute("msg", "Mật khẩu không khớp");
        	}
        } else {
        	session.setAttribute("msg", "Email already exists!");
        }
        return "redirect:/register";
    }
    
    @GetMapping("/forgotPassword")
	public String forgotPassword(Model model, HttpSession session) {
    	String msg = (String) session.getAttribute("msg");
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
		return "forgotPassword";
	}
    
    @GetMapping("/resetPassword")
	public String resetPassword(Model model, HttpSession session) {
    	String msg = (String) session.getAttribute("msg");
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        return "resetPassword";
   	}
    
    @GetMapping("/verifyOTP")
   	public String verifyOTP(Model model, HttpSession session) {
    	String msg = (String) session.getAttribute("msg");
        if (msg != null) {
            model.addAttribute("msg", msg);
            session.removeAttribute("msg");
        }
        return "verifyOTP";
   	}
    
    @PostMapping("/verifyOTP")
    public String verifyOtp(@RequestParam("otp") int otp, HttpSession session) {
        int generatedOtp = (int) session.getAttribute("otp");
        if (otp == generatedOtp) {
            return "redirect:/resetPassword";
        } else {
        	session.setAttribute("msg","Mã OTP không đúng!");
            return "redirect:/verifyOTP";
        }
    }
    
    @PostMapping("/forgotPassword")
    public String ForgotPassword(@RequestParam("email") String email, HttpSession session) {
    	User user = userRepo.findByEmail(email);
        if (user == null) {
            session.setAttribute("msg", "Email không tồn tại trong hệ thống. Vui lòng kiểm tra lại !");
            return "redirect:/forgotPassword";
        }

        int otp = new Random().nextInt(900000) + 100000;
        session.setAttribute("otp", otp);
        session.setAttribute("email", email);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Reset Password OTP");
            helper.setText("<p>Mã OTP của bạn là: <strong>" + otp + "</strong></p>", true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            session.setAttribute("msg", "Không thể gửi email. Vui lòng thử lại sau!");
            return "redirect:/forgotPassword";
            
        }

        return "verifyOTP";
    }
    
    
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("password") String password, HttpSession session) {
        String email = (String) session.getAttribute("email");
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            userRepo.save(user);
            session.setAttribute("msg", "Mật khẩu đã được thay đổi thành công!");
            return "redirect:/login";
        } else {
            session.setAttribute("mgs", "Đã xảy ra lỗi. Vui lòng thử lại sau!");
            return "redirect:/resetPassword";
        }
    }
    
}
