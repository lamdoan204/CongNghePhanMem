package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.Brand;
import com.Project.CongNghePhanMem.Entity.Department;
import com.Project.CongNghePhanMem.Entity.User;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{
	
    public Department findByManager(User manager);
    
    public Department findByBrand(Brand brand);
    

}
