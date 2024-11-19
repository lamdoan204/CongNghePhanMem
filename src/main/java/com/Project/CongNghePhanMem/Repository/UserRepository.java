package com.Project.CongNghePhanMem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Project.CongNghePhanMem.Entity.User;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    
}
