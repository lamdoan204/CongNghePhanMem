package com.Project.CongNghePhanMem.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Department;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.DepartmentRepository;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    public Department getDepartmentByBrandId(int brandId){
        return departmentRepository.findByBrandId(brandId);
    }
    public void addEmployeeToDepartment(int departmentId, User user) {
        Department department = departmentRepository.findById(departmentId)
                                    .orElseThrow(() -> new RuntimeException("Department not found"));
    
        department.addEmployee(user); // Thêm employee vào department
        departmentRepository.save(department); // Lưu cập nhật
    }
}
