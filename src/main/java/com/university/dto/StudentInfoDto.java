package com.university.dto;

import java.time.LocalDate;

import com.querydsl.core.annotations.QueryProjection;
import com.university.entity.Student;
import com.university.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentInfoDto {
	
	// entity -> Dto 변환
	public StudentInfoDto(User user, Student student) {
		this.id = student.getId();
		this.entranceDate = student.getEntranceDate();
		this.name = user.getName();
		this.birthDate = user.getBirthDate();
		this.gender = user.getGender();
		this.tel = user.getTel();
		this.email = user.getEmail();
		this.address = user.getAddress();
		this.departmentName = student.getDepartment().getName();
		this.grade = student.getGrade();
		this.semester = student.getSemester();
	}
	
	private Long id;
	
	private LocalDate entranceDate;
	
	private String name;
	
	private LocalDate birthDate;
	
	private String gender;
	
	private String tel;
	
	private String email;
	
	private String address;
	
	private String departmentName;
	
	private Integer grade;
	
	private Integer semester;
	
	@QueryProjection
	public StudentInfoDto(Long id, LocalDate entranceDate, String name, LocalDate birthDate, String gender, String tel,
			String email, String address, String departmentName, Integer grade, Integer semester) {
		this.id = id;
		this.entranceDate = entranceDate;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.tel = tel;
		this.email = email;
		this.address = address;
		this.departmentName = departmentName;
		this.grade = grade;
		this.semester = semester;
	}
	
}
