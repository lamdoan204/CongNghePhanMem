package com.Project.CongNghePhanMem.Service.Impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IUserService;
import com.Project.CongNghePhanMem.Service.JwtService;
import com.Project.CongNghePhanMem.dto.UserRequest;
import com.Project.CongNghePhanMem.configs.CustomUserDetails;

import jakarta.mail.internet.MimeMessage;

@Service
public class UserService implements IUserService {
	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public User createUser(User user, String url) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("USER");
		user.setEnabled(false);
		user.setAccounNonLocked(true);

		String verificationCode = generateRandomString(64);
		user.setVerificationCode(verificationCode);

		User us = userRepo.save(user);

		sendVerificationMail(user, url);

		return us;

	}

	private String generateRandomString(int length) {
		final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
		}
		return sb.toString();
	}

	@Override
	public boolean checkEmail(String email) {
		return userRepo.existsByEmail(email);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public boolean checkPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	@Override
	public User getUserById(int id) {
		return userRepo.findByUserId(id);
	}

	public void sendVerificationMail(User user, String url) {
		String from = "nguyenthanhan26.qngai@gmail.com";
		String to = user.getEmail();
		String subject = "Account Verification";
		String content = "Dear, [[name]],<br>" + "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "ThanhAn";
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message);

			mimeMessageHelper.setFrom(from, "4P");
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject(subject);

			content = content.replace("[[name]]", user.getFullName());

			String siteUrl = url + "/verify?code=" + user.getVerificationCode();

			content = content.replace("[[URL]]", siteUrl);

			mimeMessageHelper.setText(content, true);

			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean verifyAccount(String code) {
		User user = userRepo.findByVerificationCode(code);

		if (user != null) {
			user.setEnabled(true);
			user.setVerificationCode(code);
			userRepo.save(user);
			return true;
		}
		return false;
	}

	@Override
	public String login(UserRequest request) {
		try {
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			if (authenticate.isAuthenticated()) {
				System.out.println("Authentication successful for email: " + request.getEmail());
				return jwtService.generateToken(request.getEmail());
			}
		} catch (BadCredentialsException e) {
			System.out.println("Bad credentials for email: " + request.getEmail());
			throw new RuntimeException("Đăng nhập thất bại: Sai thông tin tài khoản hoặc mật khẩu");
		} catch (UsernameNotFoundException e) {
			System.out.println("User not found: " + request.getEmail());
			throw new RuntimeException("Đăng nhập thất bại: Người dùng không tồn tại");
		} catch (Exception e) {
			System.out.println("Unexpected error during login: " + e.getMessage());
			throw new RuntimeException("Đăng nhập thất bại: " + e.getMessage());
		}

		throw new RuntimeException("Xác thực thất bại");
	}

	@Override
	public List<User> getUserDtls() {
		return userRepo.findAll();

	}

	@Override
	public User getUserByUserId(int id) {
		return userRepo.findByUserId(id);
	}

	@Override
	public User getUserByPhone(String phone) {
		return userRepo.findByPhone(phone);
	}

	@Override
	public User getUserCurentLogged() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new RuntimeException("Người dùng chưa đăng nhập");
		}
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		int managerId = userDetails.getUserId();
		// Lấy User từ UserService
		User user = this.getUserByUserId(managerId);
		return user;
	}

	@Override
	public List<User> getUserByRole(String role) {
		return userRepo.findByRole(role);
	}

	@Override
	public void updateUser(User user) {
		userRepo.save(user);
	}

	@Override
	public User CreateManager(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("MANAGER");
		user.setEnabled(true);
		user.setAccounNonLocked(true);

		String verificationCode = generateRandomString(64);
		user.setVerificationCode(verificationCode);

		return userRepo.save(user);

	}

	@Override
	public boolean deleteManager(int managerId) {
		
		Optional<User> userOptional = userRepo.findById(managerId);
	    if (userOptional.isPresent()) {
	        userRepo.delete(userOptional.get());
	        return true;
	    }
	    return false;
		
	}

}
