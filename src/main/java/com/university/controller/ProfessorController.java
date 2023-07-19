package com.university.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.university.dto.ProfessorInfoDto;
import com.university.dto.ProfessorLectureDto;
import com.university.dto.ProfessorLectureSearchDto;
import com.university.dto.UserInfoUpdateDto;
import com.university.service.LectureService;
import com.university.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProfessorController {
	
	private final UserService userService;
	private final LectureService lectureService;
	
	// 교수 정보 화면
	@GetMapping(value="/professors/info")
	public String professorInfo(Principal principal, Model model) {
		Long id = Long.parseLong(principal.getName());
		ProfessorInfoDto professorInfoDto = userService.loadProfessorInfo(id); 
		model.addAttribute("professorInfoDto", professorInfoDto);
		return "professor/professorInfo";
	}
	
	// 교수 정보 수정 화면
	@GetMapping(value = "/professors/info/modify")
	public String modifyProfessorInfo(Principal principal, Model model) {
		Long id = Long.parseLong(principal.getName());
		UserInfoUpdateDto userInfoUpdateDto = userService.loadUserInfo(id);
		model.addAttribute("userInfoUpdateDto", userInfoUpdateDto);
		return "professor/modifyProfessorInfo";
	}
	
	// 교수 정보 수정
	@PostMapping(value = "/professors/info/modify")
	public String modifyStaffInfo(String password, Principal principal, @Valid UserInfoUpdateDto userInfoUpdateDto, BindingResult bindingResult, Model model) {
		Long id = Long.parseLong(principal.getName());
		
		if(bindingResult.hasErrors()) {
			return "professor/modifyProfessorInfo";
		}
		
		try {
			userService.updateUserInfo(id, password, userInfoUpdateDto);			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "professor/modifyProfessorInfo";
		}
		
		return "redirect:/professors/info";
	}
	
	// 이번학기 강의중인 강의 목록 페이지
	@GetMapping(value = "/professors/lecture/current")
	public String myCurrentLectureList(Principal principal, Model model) {
		Long professorId = Long.parseLong(principal.getName());
		List<ProfessorLectureDto> professorLectures = lectureService.getProfessorLectureListOfCurrentSemester(professorId);
		model.addAttribute("professorLectures", professorLectures);
		return "professor/myCurrentLectureList";
	}
	
	@GetMapping(value = "/professors/lecture/all")
	public String myLectureList(ProfessorLectureSearchDto professorLectureSearchDto, Principal principal, Model model) {
		Long professorId = Long.parseLong(principal.getName());
		List<ProfessorLectureSearchDto> professorLectureSearchDtoList = lectureService.getProfessorLectureGroupByYearAndSemester(professorId);
		List<ProfessorLectureDto> professorLectureDtoList = lectureService.getProfessorLectureList(professorId, professorLectureSearchDto);
		
		model.addAttribute("professorLectureSearchDtoList", professorLectureSearchDtoList);
		model.addAttribute("professorLectureDtoList", professorLectureDtoList);
		
		return "professor/myLectureList";
	}
	
}
