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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.university.dto.CollegeFormDto;
import com.university.dto.DepartmentDto;
import com.university.dto.DepartmentFormDto;
import com.university.dto.LectureRoomDto;
import com.university.dto.LectureRoomFormDto;
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
	public String collegeModifyForm(Model model, @PathVariable Long collegeId, RedirectAttributes redirectAttributes) {
		
		try {
			CollegeFormDto collegeFormDto = staffService.findCollegeInfoById(collegeId);
			model.addAttribute("collegeFormDto", collegeFormDto);			
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "단과대 정보를 가져오는데 문제가 발생했습니다.");
			return "redirect:/staffs/management/list/college";
		}
		
		return "staff/collegeModify";
	}
	
	// 단과대 정보 수정
	@PostMapping(value = "/staffs/management/modify/college/{collegeId}")
	public String collegeModify(@Valid CollegeFormDto collegeFormDto, BindingResult bindingResult, @PathVariable Long collegeId, Model model) {
		if(bindingResult.hasErrors()) {
			return "staff/collegeModify";
		}
		
		try {
			staffService.updateCollege(collegeFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "staff/collegeModify";
		}
		
		return "redirect:/staffs/management/list/college";
	}
	
	// 단과대 삭제
	@DeleteMapping(value = "/staffs/management/delete/college/{collegeId}")
	public @ResponseBody ResponseEntity collegeDelete(@PathVariable Long collegeId) {
		
		try {
			staffService.deleteCollege(collegeId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("단과대 삭제 중 문제가 발생했습니다.", HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Long>(collegeId, HttpStatus.OK);
	}
	
	// 학과 목록
	@GetMapping(value = "/staffs/management/list/department")
	public String departmentList(Model model) {
		List<DepartmentFormDto> departmentList = staffService.getDepartmentList();
		
		model.addAttribute("departmentList", departmentList);
		return "staff/departmentMgmt";
	}
	
	// 학과 신규등록 페이지
	@GetMapping(value = "/staffs/management/register/department")
	public String departmentRegisterForm(Model model) {
		List<CollegeFormDto> collegeList = staffService.getCollegeList();
		
		model.addAttribute("collegeList", collegeList);
		model.addAttribute("departmentFormDto", new DepartmentFormDto());
		return "staff/departmentRegister";
	}
	
	// 학과 신규 등록
	@PostMapping(value = "/staffs/management/register/department")
	public String departmentRegister(@Valid DepartmentFormDto departmentFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			List<CollegeFormDto> collegeList = staffService.getCollegeList();
			model.addAttribute("collegeList", collegeList);
			return "staff/departmentRegister";
		}
		
		try {
			staffService.createDepartment(departmentFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			List<CollegeFormDto> collegeList = staffService.getCollegeList();
			model.addAttribute("collegeList", collegeList);
			return "staff/departmentRegister";
		}
				
		return "redirect:/staffs/management/list/department";
	}
	
	// 학과 정보 수정 페이지
	@GetMapping(value = "/staffs/management/modify/department/{departmentId}")
	public String departmentModifyForm(Model model, @PathVariable Long departmentId, RedirectAttributes redirectAttributes) {
		List<CollegeFormDto> collegeList = staffService.getCollegeList();
		model.addAttribute("collegeList", collegeList);
		
		try {
			DepartmentFormDto departmentFormDto = staffService.findDepartmentInfoById(departmentId);
			model.addAttribute("departmentFormDto", departmentFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "학과 정보를 가져오는데 문제가 발생했습니다.");
			return "redirect:/staffs/management/list/department"; 
		}
		
		return "staff/departmentModify";
	}
	
	// 학과 정보 수정
	@PostMapping(value = "/staffs/management/modify/department/{departmentId}")
	public String departmentModify(@Valid DepartmentFormDto departmentFormDto, BindingResult bindingResult, @PathVariable Long departmentId, Model model) {
		if(bindingResult.hasErrors()) {
			List<CollegeFormDto> collegeList = staffService.getCollegeList();
			model.addAttribute("collegeList", collegeList);
			return "staff/departmentModify";
		}
		
		try {
			staffService.updateDepartment(departmentFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			List<CollegeFormDto> collegeList = staffService.getCollegeList();
			model.addAttribute("collegeList", collegeList);
			return "staff/departmentModify";
		}
		
		return "redirect:/staffs/management/list/department"; 
	}
	
	// 학과 삭제
	@DeleteMapping(value = "/staffs/management/delete/department/{departmentId}")
	public @ResponseBody ResponseEntity departmentDelete(@PathVariable Long departmentId) {
		
		try {
			staffService.deleteDepartment(departmentId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("학과 삭제 중 문제가 발생했습니다.", HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<Long>(departmentId, HttpStatus.OK);
	}
	
	// 강의실 목록
	@GetMapping(value = {"/staffs/management/list/lectureRoom", "/staffs/management/list/lectureRoom/{page}"})
	public String lectureRoomList(@PathVariable("page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
		Page<LectureRoomDto> lectureRooms = staffService.getLectureRoomList(pageable);
		
		model.addAttribute("lectureRooms", lectureRooms);
		model.addAttribute("maxPage", 5);
		return "staff/lectureRoomMgmt";
	}
	
	// 강의실 신규등록 페이지
	@GetMapping(value = "/staffs/management/register/lectureRoom")
	public String lectureRoomRegisterForm(Model model) {
		List<CollegeFormDto> collegeList = staffService.getCollegeList();
		model.addAttribute("collegeList", collegeList);
		
		model.addAttribute("lectureRoomFormDto", new LectureRoomFormDto());
		return "staff/lectureRoomRegister";
	}
	
	// 강의실 신규 등록
	@PostMapping(value = "/staffs/management/register/lectureRoom")
	public String lectureRoomRegister(@Valid LectureRoomFormDto lectureRoomFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			List<CollegeFormDto> collegeList = staffService.getCollegeList();
			model.addAttribute("collegeList", collegeList);
			return "staff/lectureRoomRegister";
		}
		
		try {
			staffService.createLectureRoom(lectureRoomFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			List<CollegeFormDto> collegeList = staffService.getCollegeList();
			model.addAttribute("collegeList", collegeList);
			return "staff/lectureRoomRegister";
		}
		
		return "redirect:/staffs/management/list/lectureRoom";
	}
	
	// 강의실 정보 수정 페이지
	@GetMapping(value = "/staffs/management/modify/lectureRoom/{lectureRoomId}")
	public String lectureRoomModifyForm(Model model, @PathVariable String lectureRoomId, RedirectAttributes redirectAttributes) {
		List<CollegeFormDto> collegeList = staffService.getCollegeList();
		model.addAttribute("collegeList", collegeList);
		
		try {
			LectureRoomFormDto lectureRoomFormDto = staffService.findLectureRoomInfoById(lectureRoomId);
			model.addAttribute("lectureRoomFormDto", lectureRoomFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "강의실 정보를 가져오는데 문제가 발생했습니다.");
			return "redirect:/staffs/management/list/lectureRoom";
		}
		
		return "staff/lectureRoomModify";
	}
	
	// 강의실 정보 수정
	@PostMapping(value = "/staffs/management/modify/lectureRoom/{lectureRoomId}")
	public String lectureRoomModify(@Valid LectureRoomFormDto lectureRoomFormDto, BindingResult bindingResult, @PathVariable String lectureRoomId, Model model) {
		if(bindingResult.hasErrors()) {
			List<CollegeFormDto> collegeList = staffService.getCollegeList();
			model.addAttribute("collegeList", collegeList);
			return "staff/lectureRoomModify";
		}
		
		try {
			// 업데이트 메소드(삭제도 해야함)
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			List<CollegeFormDto> collegeList = staffService.getCollegeList();
			model.addAttribute("collegeList", collegeList);
			return "staff/lectureRoomModify";
		}
		
		return "redirect:/staffs/management/list/lectureRoom";
	}
	
}
