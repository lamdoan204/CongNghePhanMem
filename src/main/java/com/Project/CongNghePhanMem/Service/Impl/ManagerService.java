package com.Project.CongNghePhanMem.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;
import com.Project.CongNghePhanMem.Service.IUserService;

public class ManagerService implements com.Project.CongNghePhanMem.Service.IManagerService {

    @Autowired 
    UserRepository userRepository;
    @Autowired 
    IUserService userService = new UserService();
    @Override
    
    public void add_Employee(User employee) {
        
        
    }
    
}
