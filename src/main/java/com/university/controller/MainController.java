package com.university.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.university.dto.StaffRegisterDto;
import com.university.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping(value="/")
	public String index() {
		return login();
	}
	
	@GetMapping(value="/main")
	public String main() {
		return "main";
	}
	
	@GetMapping(value="/user/login")
	public String login() {
		return "user/login";
	}
	
	@GetMapping(value="/totalgrade")
	public String totalGrade() {
		return "student/totalgrade";
	}
	
	@GetMapping(value="/user/staff_register")
	public String staffRegister(Model model) {
		model.addAttribute("staffRegisterDto", new StaffRegisterDto());
		return "user/staffForm";
	}
}
