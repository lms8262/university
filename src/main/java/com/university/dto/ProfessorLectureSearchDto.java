package com.university.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorLectureSearchDto {
	private Integer year;

	private Integer semester; 
	
	@QueryProjection
	public ProfessorLectureSearchDto(Integer year, Integer semester) {
		this.year = year;
		this.semester = semester;
	}
}
