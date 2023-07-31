package com.university.dto;

import com.querydsl.core.annotations.QueryProjection;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentFormDto {
	private Long departmentId;
	
	@NotEmpty(message = "학과명은 필수입력 값입니다.")
	private String departmentName;
	
	private Long collegeId;
	
	private String collegeName;
	
	@QueryProjection
	public DepartmentFormDto(Long departmentId, String departmentName,
			Long collegeId, String collegeName) {
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.collegeId = collegeId;
		this.collegeName = collegeName;
	}
	
}
