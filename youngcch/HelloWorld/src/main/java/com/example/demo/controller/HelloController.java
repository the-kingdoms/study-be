package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/")
	public String helloWorld() {
		return "Hello World";
	}
	
	@RequestMapping("/api/test")
	public String api() {
		return "{ \"test\" : \"api test success\" }";
	}
}

