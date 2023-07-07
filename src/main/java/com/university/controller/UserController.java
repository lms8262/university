package com.university.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.university.dto.DepartmentDto;
import com.university.dto.ProfessorFormDto;
import com.university.dto.StaffFormDto;
import com.university.dto.StudentFormDto;
import com.university.exption.NotFoundException;
import com.university.service.DepartmentService;
import com.university.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	public final DepartmentService departmentService;
	public final UserService userService;
	
	// 메인 화면
	@GetMapping(value="/main")
	public String main() {
		return "main";
	}
	
	// 로그인 화면
	@GetMapping(value="/users/login")
	public String login(@RequestParam(required = false) Long userId) {
		return "user/login";
	}
	
	// 로그인 실패시
	@GetMapping(value = "/users/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		return "user/login";
	}
	
	// 교직원 회원가입 화면
	@GetMapping(value="/users/staff_register")
	public String staffRegister(Model model) {
		model.addAttribute("staffFormDto", new StaffFormDto());
		return "user/staffForm";
	}
	
	// 교직원 회원가입
	@PostMapping(value="/users/staff_register")
	public String staffRegister(@Valid StaffFormDto staffFormDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		// @Valid: 유효성을 검증하려는 객체 앞에 붙인다.
		// BindingResult: 유효성 검증 후의 결과가 들어있다.
		
		if(bindingResult.hasErrors()) {
			return "user/staffForm";
		}
		
		try {
			Long userId = userService.saveUser(staffFormDto);
			redirectAttributes.addFlashAttribute("userId", userId);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "user/staffForm";
		}
		
		return "redirect:/users/login";
	}
	
	// 교수 회원가입 화면
	@GetMapping(value="/users/professor_register")
	public String professorRegister(Model model) {
		model.addAttribute("professorFormDto", new ProfessorFormDto());
		// departmentList 가져와서 model에 추가
		List<DepartmentDto> departmentList = departmentService.findAllDepartmentList();
		model.addAttribute("departmentList", departmentList);
		return "user/professorForm";
	}
	
	// 교수 회원가입
	@PostMapping(value="/users/professor_register")
	public String staffRegister(@Valid ProfessorFormDto professorFormDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			return "user/professorForm";
		}
		
		try {
			Long userId = userService.saveUser(professorFormDto);
			redirectAttributes.addFlashAttribute("userId", userId);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "user/professorForm";
		} 
		
		return "redirect:/users/login";
	}
	
	// 학생 회원가입 화면
	@GetMapping(value="/users/student_register")
	public String studentRegister(Model model) {
		model.addAttribute("studentFormDto", new StudentFormDto());
		// departmentList 가져와서 model에 추가
		List<DepartmentDto> departmentList = departmentService.findAllDepartmentList();
		model.addAttribute("departmentList", departmentList);
		return "user/studentForm";
	}
	
	// 학생 회원가입
	@PostMapping(value="/users/student_register")
	public String staffRegister(@Valid StudentFormDto studentFormDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		
		if(bindingResult.hasErrors()) {
			return "user/studentForm";
		}
		
		try {
			Long userId = userService.saveUser(studentFormDto);
			redirectAttributes.addFlashAttribute("userId", userId);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "user/studentForm";
		}
		
		return "redirect:/users/login";
	}
}
