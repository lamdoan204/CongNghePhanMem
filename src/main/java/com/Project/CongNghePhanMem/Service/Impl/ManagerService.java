package com.Project.CongNghePhanMem.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Brand;
import com.Project.CongNghePhanMem.Entity.Department;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.BrandRepository;
import com.Project.CongNghePhanMem.Repository.DepartmentRepository;


@Service
public class ManagerService implements com.Project.CongNghePhanMem.Service.IManagerService {

   
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    BrandRepository brandRepository;

    @Override
    public void add_Employee(User employee) {

    }

    @Override
    public String get_DepartmentName(User manager) {
        // Tìm phòng ban theo quản lý
        Department department = departmentRepository.findByManager(manager);
        if (department == null) {
            throw new RuntimeException("Không tìm thấy phòng ban cho quản lý này"+ manager.getUserId());
        }

        // Lấy ID của thương hiệu
        int brandId = department.getBrandId();

        // Tìm thương hiệu theo ID
        Brand brand = brandRepository.findByBrandId(brandId);
        if (brand == null) {
            throw new RuntimeException("Không tìm thấy thương hiệu với ID: " + brandId);
        }

        // Trả về tên thương hiệu
        return brand.getBrand();
    }

}
