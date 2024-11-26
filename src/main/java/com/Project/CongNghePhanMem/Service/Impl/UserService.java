package com.Project.CongNghePhanMem.Service.Impl;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IUserService;
import org.springframework.mail.javamail.JavaMailSender;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserService implements IUserService {

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
		return userRepo.findById(id);
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
}
