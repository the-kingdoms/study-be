package com.example.demo.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping("/api/testkey")
	public String bonus(@RequestParam("key") String key) {
		try {
            int value = Integer.parseInt(key);
            JSONObject json = new JSONObject();
            json.put("key", value);
            return json.toString();
        } catch (Exception e1) {
            return "Error";
        }
	}
}

