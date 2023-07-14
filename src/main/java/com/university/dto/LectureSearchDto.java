package com.university.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureSearchDto {
	
	private String type;
	
	private Long departmentId;
	
	private String lectureName = "";
	
}
