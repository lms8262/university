package com.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
	
	@GetMapping(value="/totalgrade")
	public String totalGrade() {
		return "student/totalgrade";
	}
}
