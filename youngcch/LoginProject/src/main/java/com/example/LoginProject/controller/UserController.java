package com.example.LoginProject.controller;

import com.example.LoginProject.model.UserDto;
import org.springframework.ui.Model;
import com.example.LoginProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "loginpage";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,@RequestParam Long id, Model model) {
        UserDto userDto = new UserDto(username, password, id);
        if (userService.login(userDto)) {
            model.addAttribute("username", username);
            return "login-success";
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "loginpage";
        }
    }

    @GetMapping("/signup")
    public String showRegisterPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam Long id, Model model) {
        UserDto userDto = new UserDto(username, password, id);
        if (userService.register(userDto)) {
            model.addAttribute("username", username);
            return "register-success";
        } else {
            model.addAttribute("error", "Username already exists.");
            return "signup";
        }
    }
}
