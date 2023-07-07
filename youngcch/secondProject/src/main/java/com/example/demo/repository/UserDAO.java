package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.UserDTO;

public interface UserDAO {
	
	List<UserDTO> selectAllUsers();
    UserDTO selectUser(String id);
    void insertUser(UserDTO user);
    void updateUser(UserDTO user);
    void deleteUser(String id);
}
