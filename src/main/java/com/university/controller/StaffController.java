package com.university.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.university.dto.StaffInfoDto;
import com.university.dto.UserInfoUpdateDto;
import com.university.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StaffController {
	
	private final UserService userService;
	
	// 스태프 정보 화면
	@GetMapping(value="/staffs/info")
	public String staffInfo(Principal principal, Model model) {
		Long id = Long.parseLong(principal.getName());
		StaffInfoDto staffInfoDto = userService.loadStaffInfo(id);
		model.addAttribute("staffInfoDto", staffInfoDto);
		return "staff/staffInfo";
	}
	
	// 스태프 정보 수정 화면
	@GetMapping(value = "/staffs/info-modify")
	public String modifyStaffInto(Principal principal, Model model) {
		Long id = Long.parseLong(principal.getName());
		UserInfoUpdateDto userInfoUpdateDto = userService.loadUserInfo(id);
		model.addAttribute("userInfoUpdateDto", userInfoUpdateDto);
		return "staff/modifyStaffInfo";
	}
	
	// 스태프 정보 수정
	@PostMapping(value = "/staffs/info-modify")
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
}
