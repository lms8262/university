package com.university.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentLectureSearchDto {
	private Integer year = 0;
	
	private Integer semester = 0;
	
	private String type = "";
}
