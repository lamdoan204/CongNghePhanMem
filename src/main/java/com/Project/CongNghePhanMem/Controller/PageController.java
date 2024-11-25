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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IUserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
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
        if (p != null) {
            String email = p.getName();
            User user = userRepo.findByEmail(email);
            m.addAttribute("user", user);
        }
    }

    @GetMapping("/")
    public String Home() {
        return "index";
    }
   
    @GetMapping("/it_home_dark")
    public String Home_dark() {
        return "it_home_dark";
    }

    @GetMapping("/it_home")
    public String Home_white() {
        return "it_home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signin")
    public String signin() {
        return "login";
    }

    @GetMapping("/notifyVerify")
    public String notifyVerify() {
        return "notifyVerify";
    }

    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("code") String code) {
        if (userService.verifyAccount(code)) {
            return "verifySuccessfull";
        } else {
            return "fail";
        }
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user, RedirectAttributes redirectAttributes,
                             @RequestParam("password") String password, @RequestParam("password1") String password1,
                             HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        url = url.replace(request.getServletPath(), "");

        boolean emailExists = userService.checkEmail(user.getEmail());
        if (!emailExists) {
            if (password.equals(password1)) {
                User createdUser = userService.createUser(user, url);
                if (createdUser != null) {
                    redirectAttributes.addFlashAttribute("msg", "Register successful!");
                    return "redirect:/notifyVerify";
                } else {
                    redirectAttributes.addFlashAttribute("msg", "Something went wrong! Please try again.");
                }
            } else {
                redirectAttributes.addFlashAttribute("msg", "Mật khẩu không khớp");
            }
        } else {
            redirectAttributes.addFlashAttribute("msg", "Email already exists!");
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

        Long otpTimestamp = (Long) session.getAttribute("otpTimestamp");
        if (otpTimestamp == null) {
            model.addAttribute("remainingTime", 0);
            return "verifyOTP";
        }

        long currentTime = System.currentTimeMillis();
        long remainingTime = (5 * 60 * 1000) - (currentTime - otpTimestamp);

        if (remainingTime < 0) remainingTime = 0;
        model.addAttribute("remainingTime", remainingTime);
        return "verifyOTP";
    }




    @PostMapping("/verifyOTP")
    public String verifyOtp(@RequestParam("otp") int otp, HttpSession session, RedirectAttributes redirectAttributes) {
        int generatedOtp = (int) session.getAttribute("otp");
        long otpTimestamp = (long) session.getAttribute("otpTimestamp");
        System.out.println("otpTimestamp in session: " + otpTimestamp);
        long currentTime = System.currentTimeMillis();

        if (currentTime - otpTimestamp > 5 * 60 * 1000) {
            redirectAttributes.addFlashAttribute("msg", "OTP đã hết hạn. Vui lòng yêu cầu mã OTP mới.");
            return "redirect:/verifyOTP";
        }

        if (otp == generatedOtp) {
            return "redirect:/resetPassword";
        } else {
            redirectAttributes.addFlashAttribute("msg", "Mã OTP không đúng!");
            return "redirect:/verifyOTP";
        }
    }

    @PostMapping("/forgotPassword")
    public String ForgotPassword(@RequestParam("email") String email, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            redirectAttributes.addFlashAttribute("msg", "Email không tồn tại trong hệ thống. Vui lòng kiểm tra lại!");
            return "redirect:/forgotPassword";
        }

        int otp = new Random().nextInt(900000) + 100000;
        long otpTimestamp = System.currentTimeMillis();
        session.setAttribute("otp", otp);
        session.setAttribute("otpTimestamp", otpTimestamp);
        session.setAttribute("email", email);
        
        System.out.println("otp: " + session.getAttribute("otp"));
        System.out.println("otpTimestamp: " + session.getAttribute("otpTimestamp"));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Reset Password OTP");
            helper.setText("<p>Mã OTP của bạn là: <strong>" + otp + "</strong></p>", true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("msg", "Không thể gửi email. Vui lòng thử lại sau!");
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
            session.setAttribute("msg", "Đã xảy ra lỗi. Vui lòng thử lại sau!");
            return "redirect:/resetPassword";
        }
    }

}