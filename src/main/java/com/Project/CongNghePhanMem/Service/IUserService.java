package com.Project.CongNghePhanMem.Service;



import java.util.List;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.dto.UserRequest;

public interface IUserService  {
	public User createUser(User user, String url);
	
	public boolean checkEmail(String email);
	
	User getUserByEmail(String email);

	boolean checkPassword(String rawPassword, String encodedPassword);
	
	public boolean verifyAccount(String code);
	
	public String login(UserRequest request);
	
	public List<User> getUserDtls();
}
