package com.Project.CongNghePhanMem.Service;



import com.Project.CongNghePhanMem.Entity.User;

public interface IUserService  {
	public User createUser(User user, String url);
	
	public boolean checkEmail(String email);
	
	User getUserByEmail(String email);

	boolean checkPassword(String rawPassword, String encodedPassword);
	
	public boolean verifyAccount(String code);
	public User getUserByUserId(int id);
}
