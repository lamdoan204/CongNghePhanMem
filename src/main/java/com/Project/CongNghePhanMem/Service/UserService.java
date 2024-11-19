package com.Project.CongNghePhanMem.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.CongNghePhanMem.Entity.User;
import com.Project.CongNghePhanMem.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public Optional<User> findById(int id)
    {
        return userRepository.findById(id);
    }
}
