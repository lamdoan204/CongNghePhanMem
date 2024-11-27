package com.Project.CongNghePhanMem.Service.Impl;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Brand;
import com.Project.CongNghePhanMem.Entity.Department;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.BrandRepository;
import com.Project.CongNghePhanMem.Repository.DepartmentRepository;
import com.Project.CongNghePhanMem.Repository.UserRepository;

@Service
public class ManagerService implements com.Project.CongNghePhanMem.Service.IManagerService {

    @Autowired
    private BCryptPasswordEncoder bcCryptPasswordEncoder;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void add_Employee(User employee) {
        if (employee != null) {
            employee.setPassword(bcCryptPasswordEncoder.encode(employee.getPassword()));
            userRepository.save(employee);

        } else {
            throw new RuntimeException("user is null");
        }
    }

    @Override
    public String get_DepartmentName(User manager) {

        Department department = departmentRepository.findByManager(manager);
        if (department == null) {
            throw new RuntimeException("Không tìm thấy phòng ban cho quản lý này" + manager.getUserId());
        }

        int brandId = department.getBrandId();

        Brand brand = brandRepository.findByBrandId(brandId);
        if (brand == null) {
            throw new RuntimeException("Không tìm thấy thương hiệu với ID: " + brandId);
        }

        return brand.getBrand();
    }

    @Override
    public List<User> getListEmployee(User manager) {
        Department department = departmentRepository.findByManager(manager);
        if (department == null) {
            throw new RuntimeException("Không tìm thấy phòng ban");
        }
        List<User> employee = department.getEmployee();
        return employee;
    }

    @Override
    public void delete_Employee(User employee) {
        if (employee == null) {
            throw new RuntimeException("nhân viên này đang null");
        }
        userRepository.delete(employee);

    }
}