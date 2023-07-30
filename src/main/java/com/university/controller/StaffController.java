package com.university.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.university.dto.CollegeFormDto;
import com.university.dto.DepartmentDto;
import com.university.dto.ProfessorInfoDto;
import com.university.dto.StaffInfoDto;
import com.university.dto.StudentInfoDto;
import com.university.dto.UserInfoUpdateDto;
import com.university.dto.UserSearchDto;
import com.university.service.DepartmentService;
import com.university.service.StaffService;
import com.university.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StaffController {
	
	private final UserService userService;
	private final DepartmentService departmentService;
	private final StaffService staffService;
	
	// 스태프 정보 화면
	@GetMapping(value="/staffs/info")
	public String staffInfo(Principal principal, Model model) {
		Long id = Long.parseLong(principal.getName());
		StaffInfoDto staffInfoDto = userService.loadStaffInfo(id);
		model.addAttribute("staffInfoDto", staffInfoDto);
		return "staff/staffInfo";
	}
	
	// 스태프 정보 수정 화면
	@GetMapping(value = "/staffs/info/modify")
	public String modifyStaffInto(Principal principal, Model model) {
		Long id = Long.parseLong(principal.getName());
		UserInfoUpdateDto userInfoUpdateDto = userService.loadUserInfo(id);
		model.addAttribute("userInfoUpdateDto", userInfoUpdateDto);
		return "staff/modifyStaffInfo";
	}
	
	// 스태프 정보 수정
	@PostMapping(value = "/staffs/info/modify")
	public String modifyStaffInto(String password, Principal principal, @Valid UserInfoUpdateDto userInfoUpdateDto, BindingResult bindingResult, Model model) {
		Long id = Long.parseLong(principal.getName());
		
		if(bindingResult.hasErrors()) {
			return "staff/modifyStaffInfo";
		}
		
		try {
			userService.updateUserInfo(id, password, userInfoUpdateDto);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "staff/modifyStaffInfo";
		}
		
		return "redirect:/staffs/info";
	}
	
	// 학생 목록
	@GetMapping(value = {"/staffs/management/list/student", "/staffs/management/list/student/{page}"})
	public String studentList(UserSearchDto userSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		List<DepartmentDto> departments = departmentService.findAllDepartmentList();
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<StudentInfoDto> studentInfos = staffService.getStudentInfoList(userSearchDto, pageable);
		
		model.addAttribute("departments", departments);
		model.addAttribute("studentInfos", studentInfos);
		model.addAttribute("userSearchDto", userSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "staff/studentList";
	}
	
	// 교수 목록
	@GetMapping(value = {"/staffs/management/list/professor", "/staffs/management/list/professor/{page}"})
	public String professorList(UserSearchDto userSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		List<DepartmentDto> departments = departmentService.findAllDepartmentList();
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<ProfessorInfoDto> professorInfos = staffService.getProfessorInfoList(userSearchDto, pageable);
		
		model.addAttribute("departments", departments);
		model.addAttribute("professorInfos", professorInfos);
		model.addAttribute("userSearchDto", userSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "staff/professorList";
	}
	
	// 단과대 목록
	@GetMapping(value = "/staffs/management/list/college")
	public String collegeList(Model model) {
		List<CollegeFormDto> collegeList = staffService.getCollegeList();
		
		model.addAttribute("collegeList", collegeList);
		return "staff/collegeMgmt";
	}
	
	// 단과대 신규 등록 페이지
	@GetMapping(value = "/staffs/management/register/college")
	public String collegeRegisterForm(Model model) {
		model.addAttribute("collegeFormDto", new CollegeFormDto());
		return "staff/collegeRegister";
	}
	
	// 단과대 신규 등록
	@PostMapping(value = "/staffs/management/register/college")
	public String collegeRegister(@Valid CollegeFormDto collegeFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "staff/collegeRegister";
		}
		
		try {
			staffService.createCollege(collegeFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "staff/collegeRegister";
		}
		
		return "redirect:/staffs/management/list/college";
	}
	
	// 단과대 정보 수정 페이지
	@GetMapping(value = "/staffs/management/modify/college/{collegeId}")
	public String collegeModifyForm(Model model, @PathVariable Long collegeId) {
		return "staff/collegeModify";
	}
}
