package com.university.dto;

import com.university.entity.College;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollegeFormDto {
	
	private Long collegeId;
	
	@NotEmpty(message = "단과대명은 필수입력 값입니다.")
	private String collegeName;
	
	@Pattern(regexp = "[A-Z]{1}", message = "단과대 코드는 영어 대문자 1자리로 입력해주세요.")
	private String collegeCode;
	
	public static CollegeFormDto of(College college) {
		CollegeFormDto collegeFormDto = new CollegeFormDto();
		collegeFormDto.setCollegeId(college.getId());
		collegeFormDto.setCollegeName(college.getName());
		collegeFormDto.setCollegeCode(college.getCollegeCode());
		return collegeFormDto;
	}
}
