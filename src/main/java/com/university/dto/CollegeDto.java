package com.university.dto;

import com.university.entity.College;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CollegeDto {
	private Long collegeId;
	
	private String collegeName;
	
	public static CollegeDto of(College college) {
		CollegeDto collegeDto = new CollegeDto();
		collegeDto.setCollegeId(college.getId());
		collegeDto.setCollegeName(college.getName());
		return collegeDto;
	}
}
