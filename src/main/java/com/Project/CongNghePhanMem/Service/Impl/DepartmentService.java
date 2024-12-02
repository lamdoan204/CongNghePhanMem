package com.Project.CongNghePhanMem.Service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.Brand;
import com.Project.CongNghePhanMem.Entity.Department;
import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.DepartmentRepository;

@Service
public class DepartmentService  {

    @Autowired
    DepartmentRepository departmentRepository;
    

    public Page<Department> getDepartment1(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }
    public Department getDepartmentByBrandId(Brand brand){
        return departmentRepository.findByBrand(brand);
    }
    public Department getDepartmentByManager(User manager){
        return departmentRepository.findByManager( manager );
    }
    
    public void addEmployeeToDepartment(int departmentId, User user) {
        Department department = departmentRepository.findById(departmentId)
                                    .orElseThrow(() -> new RuntimeException("Department not found"));
    
        department.addEmployee(user); 
        departmentRepository.save(department);
    }


    public void deleteEmployeeToDepartment(int departmentId, User employee){
        Department department = departmentRepository.findById(departmentId)
        .orElseThrow(() -> new RuntimeException("Department not found"));
        department.deleteEmployee(employee);
        departmentRepository.save(department);
    }
    
    public List<Department> getDepartment(){
    	return departmentRepository.findAll();
    }
    
    public Department insert(Brand brand,User user) {
    	Department department = new Department();
    	department.setBrand(brand);
    	department.setManager(user);
    	return departmentRepository.save(department);
    }
    public void save(Department department) {
    	departmentRepository.save(department);
    }
}
