package com.Project.CongNghePhanMem.Service;

import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.User;

@Service
public interface IManagerService {
    public void add_Employee(User employee);
    public String get_DepartmentName(User manager);
    
}
