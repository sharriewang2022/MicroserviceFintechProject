package com.hibernate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibernate.model.UserEntity;

@Service
public class UserService {
   

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> findAll() {
   
        return userRepository.findAll();
    }

    public UserEntity save(UserEntity user) {
   
        return userRepository.save(user);
    }

    public void delete(Long id) {
   
        userRepository.deleteById(id);
    }
}
