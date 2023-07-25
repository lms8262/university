package com.university.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.university.dto.DepartmentDto;
import com.university.dto.LectureScheduleDto;
import com.university.dto.LectureSearchDto;
import com.university.dto.StudentInfoDto;
import com.university.dto.StudentLectureScoreDto;
import com.university.dto.StudentLectureScoreInfoDto;
import com.university.dto.UserInfoUpdateDto;
import com.university.service.DepartmentService;
import com.university.service.LectureRegistrationService;
import com.university.service.LectureService;
import com.university.service.StudentService;
import com.university.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StudentController {
	
	private final UserService userService;
	private final DepartmentService departmentService;
	private final LectureService lectureService;
	private final LectureRegistrationService lectureRegistrationService;
	private final StudentService studentService; 
	
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
	
	// 강의 시간표 페이지
	@GetMapping(value = {"/students/lecture/list", "/students/lecture/list/{page}"})
	public String lectureList(LectureSearchDto lectureSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		List<DepartmentDto> departments = departmentService.findAllDepartmentList();
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<LectureScheduleDto> lectureSchedules = lectureService.getLectureScheduleList(lectureSearchDto, pageable);
		
		model.addAttribute("departments", departments);
		model.addAttribute("lectureSchedules", lectureSchedules);
		model.addAttribute("lectureSearchDto", lectureSearchDto);
		model.addAttribute("maxPage", 5);
		
		return "student/lectureList";
	}
	
	// 수강신청 화면
	@GetMapping(value = {"/students/lecture/registration/list", "/students/lecture/registration/list/{page}"})
	public String lectureRegistrationPage(Principal principal, @PathVariable Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Long userId = Long.parseLong(principal.getName());
		Page<LectureScheduleDto> lectureSchedules = lectureService.getRegistrationAbleLectureList(userId, pageable);
		
		model.addAttribute("lectureSchedules", lectureSchedules);
		model.addAttribute("page", pageable.getPageNumber());
		model.addAttribute("maxPage", 5);
		
		return "student/lectureRegistration";
	}
	
	// 수강신청
	@PostMapping(value = "/students/lecture/registration/{lectureId}")
	public @ResponseBody ResponseEntity lectureRegistration(@PathVariable Long lectureId, Principal principal) {
		Long studentId = Long.parseLong(principal.getName());
		
		try {
			lectureRegistrationService.lectureRegistration(lectureId, studentId);			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Long>(lectureId, HttpStatus.OK);
	}
	
	// 수강신청 이력 페이지
	@GetMapping(value = "/students/lecture/registration/history")
	public String lectureRegistrationHistory(Principal principal, Model model) {
		Long studentId = Long.parseLong(principal.getName());
		List<LectureScheduleDto> lectureRegistrationHistory = lectureRegistrationService.getLectureRegistrationHistory(studentId);
		
		model.addAttribute("lectureRegistrationHistory", lectureRegistrationHistory);
		return "student/lectureRegistrationList";
	}
	
	// 수강신청 취소
	@DeleteMapping("/students/lecture/registration/{lectureId}/cancel")
	public @ResponseBody ResponseEntity cancelLectureRegistration(@PathVariable Long lectureId, Principal principal) {
		Long studentId = Long.parseLong(principal.getName());
		
		try {
			lectureRegistrationService.cancelLectureRegistration(lectureId, studentId);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Long>(lectureId, HttpStatus.OK);
	}
	
	@GetMapping(value = "/students/score/current")
	public String currentScore(Principal principal, Model model) {
		Long studentId = Long.parseLong(principal.getName());
		List<StudentLectureScoreInfoDto> studentLectureList = studentService.getCurrentStudentLectureList(studentId);
		StudentLectureScoreDto studentLectureScore = studentService.getCurrentStudentScore(studentId);
		
		model.addAttribute("studentLectureList", studentLectureList);
		model.addAttribute("studentLectureScore", studentLectureScore);
		return "student/currentScore";
	}
	
	@GetMapping(value = "/students/score/semester")
	public String semesterScore() {
		return "student/semesterScore";
	}
	
	@GetMapping(value = "/students/score/total")
	public String totalScore() {
		return "student/totalScore";
	}
}
