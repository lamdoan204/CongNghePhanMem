package com.Project.CongNghePhanMem.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.User;

@Service
public interface IManagerService {
    public void add_Employee(User employee);
    public void delete_Employee(User employee);
    public String get_DepartmentName(User manager);
    public List<User> getListEmployee(User manager);
    public int get_DepartmentBrandId(User manager);
    //public String get_ManagerName(User manager);

}
