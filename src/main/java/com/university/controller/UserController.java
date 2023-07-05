package com.university.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.university.dto.DepartmentDto;
import com.university.dto.ProfessorFormDto;
import com.university.dto.StaffFormDto;
import com.university.dto.StudentFormDto;
import com.university.entity.Department;
import com.university.service.DepartmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	public final DepartmentService departmentService;
	
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
		model.addAttribute("staffFormDto", new StaffFormDto());
		return "user/staffForm";
	}
	
	@PostMapping(value="/users/staff_register")
	public String staffRegister(@Valid StaffFormDto staffFormDto, BindingResult bindingResult, Model model) {
		return "redirect:/";
	}
	
	@GetMapping(value="/users/professor_register")
	public String professorRegister(Model model) {
		model.addAttribute("professorFormDto", new ProfessorFormDto());
		// departmentList 가져와서 model에 추가
		List<DepartmentDto> departmentList = departmentService.findAllDepartmentList();
		model.addAttribute("departmentList", departmentList);
		return "user/professorForm";
	}
	
	@PostMapping(value="/users/professor_register")
	public String staffRegister(@Valid ProfessorFormDto professorFormDto, BindingResult bindingResult, Model model) {
		return "redirect:/";
	}
	
	@GetMapping(value="/users/student_register")
	public String studentRegister(Model model) {
		model.addAttribute("studentFormDto", new StudentFormDto());
		// departmentList 가져와서 model에 추가
		List<DepartmentDto> departmentList = departmentService.findAllDepartmentList();
		model.addAttribute("departmentList", departmentList);
		return "user/studentForm";
	}
	
	@PostMapping(value="/users/student_register")
	public String staffRegister(@Valid StudentFormDto studentFormDto, BindingResult bindingResult, Model model) {
		return "redirect:/";
	}
}
