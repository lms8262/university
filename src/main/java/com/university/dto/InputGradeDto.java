package com.university.dto;

import lombok.Getter;

// 점수 입력시 ajax requestbody로 받을 dto 
@Getter
public class InputGradeDto {
	private Long lectureId;
	private Long studentId;
	private String grade;
}
