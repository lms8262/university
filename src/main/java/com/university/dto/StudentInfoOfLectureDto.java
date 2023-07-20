package com.university.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentInfoOfLectureDto {
	private Long studentId;
	
	private String studentName;
	
	private String departmentName;
	
	private String grade;
	
	@QueryProjection
	public StudentInfoOfLectureDto(Long studentId, String studentName, String departmentName, String grade) {
		this(studentId, studentName, departmentName);
		this.grade = grade;
	}
	
	@QueryProjection
	public StudentInfoOfLectureDto(Long studentId, String studentName, String departmentName) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.departmentName = departmentName;
	}
}
