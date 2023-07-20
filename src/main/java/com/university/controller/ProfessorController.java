package com.university.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.university.dto.ProfessorInfoDto;
import com.university.dto.ProfessorLectureDto;
import com.university.dto.ProfessorLectureSearchDto;
import com.university.dto.StudentInfoOfLectureDto;
import com.university.dto.UserInfoUpdateDto;
import com.university.service.LectureService;
import com.university.service.ProfessorService;
import com.university.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProfessorController {
	
	private final UserService userService;
	private final LectureService lectureService;
	private final ProfessorService professorService;
	
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
	
	// 내 강의 목록 조회 페이지(기본값 : 이번 학기)
	@GetMapping(value = "/professors/lecture/list")
	public String myLectureList(Principal principal, Model model) {
		Long professorId = Long.parseLong(principal.getName());
		List<ProfessorLectureSearchDto> professorLectureSearchDtoList = lectureService.getProfessorLectureGroupByYearAndSemester(professorId);
		List<ProfessorLectureDto> professorLectureDtoList = lectureService.getProfessorLectureList(professorId, new ProfessorLectureSearchDto());
		
		model.addAttribute("professorLectureSearchDtoList", professorLectureSearchDtoList);
		model.addAttribute("professorLectureDtoList", professorLectureDtoList);
		
		return "professor/myLectureList";
	}
	
	// 내 강의 목록 조회 페이지(검색)
	@PostMapping(value = "/professors/lecture/list")
	public String myLectureListSearch(String yearSemester, Principal principal, Model model) {
		Long professorId = Long.parseLong(principal.getName());
		List<ProfessorLectureSearchDto> professorLectureSearchDtoList = lectureService.getProfessorLectureGroupByYearAndSemester(professorId);
		
		String[] strs = yearSemester.split(",");
		Integer year = Integer.parseInt(strs[0]);
		Integer semester = Integer.parseInt(strs[1]);
		ProfessorLectureSearchDto professorLectureSearchDto = new ProfessorLectureSearchDto();
		professorLectureSearchDto.setYear(year);
		professorLectureSearchDto.setSemester(semester);
		
		List<ProfessorLectureDto> professorLectureDtoList = lectureService.getProfessorLectureList(professorId, professorLectureSearchDto);
		model.addAttribute("professorLectureSearchDtoList", professorLectureSearchDtoList);
		model.addAttribute("professorLectureDtoList", professorLectureDtoList);
		
		return "professor/myLectureList";
	}
	
	// 성적 입력 페이지 강의 리스트
	@GetMapping(value = "/professors/lecture/score")
	public String myCurrentLectureList(Principal principal, Model model) {
		Long professorId = Long.parseLong(principal.getName());
		List<ProfessorLectureDto> professorLectures = lectureService.getProfessorLectureListOfCurrentSemester(professorId);
		model.addAttribute("professorLectures", professorLectures);
		return "professor/myCurrentLectureList";
	}
	
	 
	 // 강의 학생 목록
	 @GetMapping(value = "/professors/lecture/score/{lectureId}")
	 public String studentListOfLecture(Principal principal, Model model, @PathVariable Long lectureId, RedirectAttributes redirectAttributes) {
		 Long professorId = Long.parseLong(principal.getName());
		 String lectureName = professorService.validateInputScore(professorId, lectureId);
		 
		 // Get방식이므로 url 직접변경해서 접속할 경우 본인 강의만 확인가능하도록 설정
		 if(lectureName == null) {
			 redirectAttributes.addFlashAttribute("errorMessage", "본인이 강의중인 강의가 아닙니다.");
			 return "redirect:/professors/lecture/score";
		 }
		 
		 List<StudentInfoOfLectureDto> studentInfoList = professorService.getStudentInfoList(professorId, lectureId);
		 List<StudentInfoOfLectureDto> studentInfoAndGradeList = professorService.getStudentInfoAndGradeList(professorId, lectureId);
		 model.addAttribute("lectureId", lectureId);
		 model.addAttribute("lectureName", lectureName);
		 model.addAttribute("studentInfoList", studentInfoList);
		 model.addAttribute("studentInfoAndGradeList", studentInfoAndGradeList);
		 
		 return "professor/studentListOfLecture";
	 }
	 
	 @GetMapping(value = "/professors/lecture/score/{lectureId}/{studentId}")
	 public String inputGradeForm(Model model, @PathVariable Long lectureId, @PathVariable Long studentId) {
		 
		 return "professor/inputGradeForm";
	 }
	 
}
