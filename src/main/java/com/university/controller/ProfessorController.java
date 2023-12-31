package com.university.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import com.university.dto.InputGradeDto;
import com.university.dto.ProfessorInfoDto;
import com.university.dto.ProfessorLectureDto;
import com.university.dto.ProfessorLectureSearchDto;
import com.university.dto.StudentInfoOfLectureDto;
import com.university.dto.UserInfoUpdateDto;
import com.university.service.LectureService;
import com.university.service.ProfessorService;
import com.university.service.UserService;
import com.university.util.SemesterUtil;

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
	public String myLectureList(@RequestParam(required = false) String yearSemester, Principal principal, Model model) {
		Long professorId = Long.parseLong(principal.getName());
		List<ProfessorLectureSearchDto> professorLectureSearchDtoList = lectureService.getProfessorLectureGroupByYearAndSemester(professorId);
		
		Integer year;
		Integer semester;
		
		// 처음 페이지 들어왔을때(이번 학기 강의)
		if(yearSemester == null) {
			year = SemesterUtil.CURRENT_YEAR;
			semester = SemesterUtil.CURRENT_SEMESTER;
			yearSemester = year + "," + semester; 
			
		} else { //조회 버튼 눌렀을때
			if(StringUtils.equals(yearSemester, ",")) { // 전체 학기 강의 조회
				year = null;
				semester = null;
			} else { // 그 외 특정 학기 강의 조회
				String[] strs = yearSemester.split(",");
				year = Integer.parseInt(strs[0]);
				semester = Integer.parseInt(strs[1]);
			}
			
		}
		
		List<ProfessorLectureDto> professorLectureDtoList = lectureService.getProfessorLectureList(professorId, new ProfessorLectureSearchDto(year, semester));
		
		if(professorLectureDtoList.size() == 0) { // 이번 학기 강의가 없을때
			yearSemester = ",";
		}
		
		model.addAttribute("professorLectureSearchDtoList", professorLectureSearchDtoList);
		model.addAttribute("professorLectureDtoList", professorLectureDtoList);
		model.addAttribute("yearSemester", yearSemester);
		
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
	 
	 // 강의 학생 성적입력 페이지
	 @GetMapping(value = "/professors/lecture/score/input/{lectureId}/{studentId}")
	 public String inputGradeForm(Principal principal, Model model, @PathVariable Long lectureId, @PathVariable Long studentId, RedirectAttributes redirectAttributes) {
		 Long professorId = Long.parseLong(principal.getName());
		 String lectureName = professorService.validateInputScore(professorId, lectureId);
		 
		 if(lectureName == null) {
			 redirectAttributes.addFlashAttribute("errorMessage", "본인이 강의중인 강의가 아닙니다.");
			 return "redirect:/professors/lecture/score";
		 }
		 
		 if(!professorService.validateRegistration(lectureId, studentId)) {
			 redirectAttributes.addFlashAttribute("errorMessage", "강의를 수강 중인 학생이 아닙니다.");
			 return "redirect:/professors/lecture/score";
		 }
		 
		 List<String> gradeList = professorService.getGradeList();
		 StudentInfoOfLectureDto studentInfo = professorService.getStudentInfoForInputGrade(lectureId, studentId);
		 
		 model.addAttribute("lectureId", lectureId);
		 model.addAttribute("lectureName", lectureName);
		 model.addAttribute("gradeList", gradeList);
		 model.addAttribute("studentInfo", studentInfo);
		 
		 return "professor/inputGradeForm";
	 }
	 
	 // 강의 학생 성적 입력
	 @PostMapping(value = "/professors/lecture/score/new")
	 public @ResponseBody ResponseEntity inputGrade(@RequestBody InputGradeDto inputGradeDto) {
		 Long lectureId = inputGradeDto.getLectureId();
		 Long studentId = inputGradeDto.getStudentId();
		 String grade = inputGradeDto.getGrade();
		 
		 try {
		 professorService.inputScore(lectureId, studentId, grade);
		 } catch (Exception e) {
			 e.printStackTrace();
			 return new ResponseEntity<String>("성적 입력 중 문제가 발생했습니다.", HttpStatus.FORBIDDEN);
		 }
		
		 return new ResponseEntity<Long>(lectureId, HttpStatus.OK);
	 }
	 
	 // 강의 학생 성적 수정 페이지
	 @GetMapping(value = "/professors/lecture/score/modify/{lectureId}/{studentId}")
	 public String modifyGradeForm(Principal principal, Model model, @PathVariable Long lectureId, @PathVariable Long studentId, RedirectAttributes redirectAttributes) {
		 Long professorId = Long.parseLong(principal.getName());
		 String lectureName = professorService.validateInputScore(professorId, lectureId);
		 
		 if(lectureName == null) {
			 redirectAttributes.addFlashAttribute("errorMessage", "본인이 강의중인 강의가 아닙니다.");
			 return "redirect:/professors/lecture/score";
		 }
		 
		 if(!professorService.validateRegistration(lectureId, studentId)) {
			 redirectAttributes.addFlashAttribute("errorMessage", "강의를 수강 중인 학생이 아닙니다.");
			 return "redirect:/professors/lecture/score";
		 }
		 
		 List<String> gradeList = professorService.getGradeList();
		 StudentInfoOfLectureDto studentInfo = professorService.getStudentInfoForModifyGrade(lectureId, studentId);
		 
		 model.addAttribute("lectureId", lectureId);
		 model.addAttribute("lectureName", lectureName);
		 model.addAttribute("gradeList", gradeList);
		 model.addAttribute("studentInfo", studentInfo);
		 
		 return "professor/modifyGradeForm";
	 }
	 
	 // 강의 학생 성적 수정
	 @PostMapping(value = "/professors/lecture/score/update")
	 public @ResponseBody ResponseEntity updateGrade(@RequestBody InputGradeDto inputGradeDto) {
		 Long lectureId = inputGradeDto.getLectureId();
		 Long studentId = inputGradeDto.getStudentId();
		 String grade = inputGradeDto.getGrade();
		 
		 try {
			 professorService.updateScore(lectureId, studentId, grade);
		 } catch (Exception e) {
			 e.printStackTrace();
			 return new ResponseEntity<String>("성적 수정 중 문제가 발생했습니다.", HttpStatus.FORBIDDEN);
		 }
		 
		 return new ResponseEntity<Long>(lectureId, HttpStatus.OK);
	 }
	 
	 // 강의 학생 성적 입력취소
	 @DeleteMapping(value = "/professors/lecture/score/delete/{lectureId}/{studentId}")
	 public @ResponseBody ResponseEntity deleteGrade(Principal principal, @PathVariable Long lectureId, @PathVariable Long studentId) {
		 Long professorId = Long.parseLong(principal.getName());
		 
		 if(professorService.validateInputScore(professorId, lectureId) == null) {
			 return new ResponseEntity<String>("본인이 강의중인 강의가 아닙니다.", HttpStatus.FORBIDDEN);
		 }
		 
		 if(!professorService.validateRegistration(lectureId, studentId)) {
			 return new ResponseEntity<String>("강의를 수강 중인 학생이 아닙니다.", HttpStatus.FORBIDDEN);
		 }
		 
		 try {
			 professorService.deleteScore(lectureId, studentId);			
		 } catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("성적 삭제 중 문제가 발생했습니다.", HttpStatus.FORBIDDEN);
		 }
		 
		 return new ResponseEntity<Long>(lectureId, HttpStatus.OK);
	 }
}
