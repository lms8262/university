package com.university.util;

import java.time.LocalDate;

public class SemesterUtil {
	public static Integer CURRENT_YEAR;
	public static Integer CURRENT_SEMESTER;
	
	static {
		LocalDate date = LocalDate.now();
		CURRENT_YEAR = date.getYear();
		int month = date.getMonthValue();
		CURRENT_SEMESTER = 1; // 3월 ~ 8월 1학기
		
		// 9월 ~ 2월 2학기
		if(month <= 2) {
			CURRENT_YEAR--;
			CURRENT_SEMESTER = 2;
		} else if(month >= 9) {
			CURRENT_SEMESTER = 2;
		}
	}
	
}
