package com.example.demo.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.repository.UserDAO;


@RestController
public class UserController {

	UserDAO dao;
	public void setDao(UserDAO dao) {
		this.dao = dao;
	}

	@RequestMapping("/userInsert")
	public String userInsert() {
		return "userInsert";
	}
}
