package com.university.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDto {
	private Long departmentId = 0L;
	
	private String userId = "";
}
