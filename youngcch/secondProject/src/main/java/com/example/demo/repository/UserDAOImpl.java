package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.UserDTO;
import org.apache.ibatis.session.SqlSession;

public class UserDAOImpl implements UserDAO{

	private SqlSession ss;
	@Override
	public List<UserDTO> selectAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO selectUser(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertUser(UserDTO user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(UserDTO user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		
	}

}
