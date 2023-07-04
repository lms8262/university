package com.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.university.dto.StaffRegisterDto;

@Controller
public class UserController {
	
	@GetMapping(value="/main")
	public String main() {
		return "main";
	}
	
	@GetMapping(value="/users/login")
	public String login() {
		return "user/login";
	}
	
	@GetMapping(value="/users/staff_register")
	public String staffRegister(Model model) {
		model.addAttribute("staffRegisterDto", new StaffRegisterDto());
		return "user/staffForm";
	}
}
