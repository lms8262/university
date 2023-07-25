package com.university.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudentLectureScoreDto {
	private Integer year;
	
	private Integer semester;
	
	private Integer totalCredit;
	
	private Integer totalGetCredit;
	
	private Double averageScore;
	
}
