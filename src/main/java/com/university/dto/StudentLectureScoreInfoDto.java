package com.university.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentLectureScoreInfoDto {
	private Integer year;
	
	private Integer semester;
	
	private String lectureCodeDetail;
	
	private Long lectureId;
	
	private String lectureName;
	
	private String type;
	
	private String professorName;
	
	private Integer credit;
	
	private String grade;
	
	@QueryProjection
	public StudentLectureScoreInfoDto(Integer year, Integer semester, String lectureCodeDetail, Long lectureId,
			String lectureName, String type, String professorName, Integer credit, String grade) {
		this.year = year;
		this.semester = semester;
		this.lectureCodeDetail = lectureCodeDetail;
		this.lectureId = lectureId;
		this.lectureName = lectureName;
		this.type = type;
		this.professorName = professorName;
		this.credit = credit;
		this.grade = grade;
	}
}
