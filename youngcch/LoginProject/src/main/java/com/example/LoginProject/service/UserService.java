package com.example.LoginProject.service;

import com.example.LoginProject.Repository.UserRepository;
import com.example.LoginProject.model.UserDto;
import com.example.LoginProject.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean register(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            return false; // 이미 존재하는 사용자
        }

        String encryptedPassword = PasswordEncryptor.encryptPassword(userDto.getPassword());
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setPassword(encryptedPassword);
        userRepository.save(userEntity);
        return true;
    }

    public boolean login(UserDto userDto) {
        UserEntity userEntity = userRepository.findByUsername(userDto.getUsername());
        if (userEntity != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword());
        }
        return false;
    }
}
