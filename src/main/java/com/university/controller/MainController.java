package com.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
	
	@GetMapping(value="/")
	public String main() {
		return "main";
	}
	
	@GetMapping(value="/login")
	public String login() {
		return "user/login";
	}
	
	@PostMapping(value="/login")
	public String loginOK() {
		return "redirect:/";
	}
	
	@GetMapping(value="/totalgrade")
	public String totalGrade() {
		return "student/totalgrade";
	}
}
