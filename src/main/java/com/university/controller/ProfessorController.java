package com.university.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.university.dto.UserInfoUpdateDto;
import com.university.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProfessorController {
	
	private final UserService userService;
	
	@GetMapping(value="/professors/info")
	public String professorInfo(Principal principal, Model model) {
		Long id = Long.parseLong(principal.getName());
		
		return "professor/professorInfo";
	}
	
	// 교수 정보 수정 화면
	@GetMapping(value = "/professors/info-modify")
	public String modifyProfessorInfo(Principal principal, Model model) {
		Long id = Long.parseLong(principal.getName());
		
		return "staff/modifyProfessorInfo";
	}
	
	// 교수 정보 수정
	@PostMapping(value = "/professors/info-modify")
	public String modifyStaffInfo(String password, Principal principal, @Valid UserInfoUpdateDto userInfoUpdateDto, BindingResult bindingResult, Model model) {
		
		return "redirect:/professors/info";
	}
	
}
