package com.Project.CongNghePhanMem.Service;



import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.dto.UserRequest;

public interface IUserService  {
	public User createUser(User user, String url);
	
	public boolean checkEmail(String email);
	
	User getUserById(int id);
	
	User getUserByEmail(String email);

	boolean checkPassword(String rawPassword, String encodedPassword);
	
	public boolean verifyAccount(String code);

	public String login(UserRequest request);
	
	public List<User> getUserDtls();

	public User getUserByUserId(int id);

	public User getUserByPhone(String phone);

	public void updateUser(User user);

	public User getUserCurentLogged();
	
	public List<User> getUserByRole(String role);

	@PreAuthorize("hasRole('ADMIN')")
	public User CreateManager(User user);
	
	
	@PreAuthorize("hasRole('ADMIN')")
	public boolean deleteManager(int mangerId);

}
