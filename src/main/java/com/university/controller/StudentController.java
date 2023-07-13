package com.university.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.university.dto.DepartmentDto;
import com.university.dto.StudentInfoDto;
import com.university.dto.UserInfoUpdateDto;
import com.university.service.DepartmentService;
import com.university.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StudentController {
	
	private final UserService userService;
	private final DepartmentService departmentService;
	
	// 학생 정보 화면
	@GetMapping(value = "/students/info")
	public String studentInfo(Principal principal, Model model) {
		Long id = Long.parseLong(principal.getName());
		StudentInfoDto studentInfoDto = userService.loadStudentInfo(id);
		model.addAttribute("studentInfoDto", studentInfoDto);
		return "student/studentInfo";
	}
	
	// 학생 정보 수정 화면
	@GetMapping(value = "/students/info/modify")
	public String modifyStudentInfo(Principal principal, Model model) {
		Long id = Long.parseLong(principal.getName());
		UserInfoUpdateDto userInfoUpdateDto = userService.loadUserInfo(id);
		model.addAttribute("userInfoUpdateDto", userInfoUpdateDto);
		return "student/modifyStudentInfo";
	}
	
	// 학생 정보 수정
	@PostMapping(value = "/students/info/modify")
	public String modifyStudentInfo(String password, Principal principal, @Valid UserInfoUpdateDto userInfoUpdateDto, BindingResult bindingResult, Model model) {
		Long id = Long.parseLong(principal.getName());
		
		if(bindingResult.hasErrors()) {
			return "student/modifyStudentInfo";
		}
		
		try {
			userService.updateUserInfo(id, password, userInfoUpdateDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "student/modifyStudentInfo";
		}
		
		return "redirect:/students/info"; 
	}
	
	@GetMapping(value = "/students/lecture/list")
	public String lctureList(Model model) {
		List<DepartmentDto> departments = departmentService.findAllDepartmentList();
		model.addAttribute("departments", departments);
		return "student/lectureList";
	}
	
	@GetMapping(value="/totalgrade")
	public String totalGrade() {
		return "student/totalgrade";
	}
}
