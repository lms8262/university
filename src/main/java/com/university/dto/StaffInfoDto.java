package com.university.dto;

import java.time.LocalDate;

import com.university.entity.Staff;
import com.university.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffInfoDto {
	
	// entity -> Dto 변환
	public StaffInfoDto(User user, Staff staff) {
		this.id = staff.getId();
		this.hireDate = staff.getHireDate();
		this.name = user.getName();
		this.birthDate = user.getBirthDate();
		this.gender = user.getGender();
		this.tel = user.getTel();
		this.email = user.getEmail();
		this.address = user.getAddress();
	}
	
	private Long id;
	
	private LocalDate hireDate;
	
	private String name;
	
	private LocalDate birthDate;
	
	private String gender;
	
	private String tel;
	
	private String email;
	
	private String address;
}
