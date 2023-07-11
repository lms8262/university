package com.university.dto;

import java.time.LocalDate;

import com.university.entity.Professor;
import com.university.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfessorInfoDto {
	
	// entity -> Dto 변환
	public ProfessorInfoDto(User user, Professor professor) {
		this.id = professor.getId();
		this.hireDate = professor.getHireDate();
		this.name = user.getName();
		this.birthDate = user.getBirthDate();
		this.gender = user.getGender();
		this.tel = user.getTel();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.departmentName = professor.getDepartment().getName();
	}
	
	private Long id;

	private LocalDate hireDate;
	
	private String name;
	
	private LocalDate birthDate;
	
	private String gender;
	
	private String tel;
	
	private String email;
	
	private String address;
	
	private String departmentName;
}
