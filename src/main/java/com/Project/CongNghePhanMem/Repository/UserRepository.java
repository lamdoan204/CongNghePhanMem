package com.Project.CongNghePhanMem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    public boolean existsByEmail(String emai);

    public User findByEmail(String email);
    
    public User findByVerificationCode(String verificationCode);

    public User findByUserId(Integer userId);

    public User findByPhone(String phone);
    
    public List<User> findByRole(String role);
    
}
