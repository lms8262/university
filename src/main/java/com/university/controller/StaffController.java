package com.university.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StaffController {
	
	@GetMapping(value="/staffs/info")
	public String staffInfo() {
		return "staff/staffInfo";
	}
}
