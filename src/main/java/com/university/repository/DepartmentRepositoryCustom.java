package com.university.repository;

import java.util.List;

import com.university.dto.DepartmentFormDto;

public interface DepartmentRepositoryCustom {
	List<DepartmentFormDto> getDepartmentList();
	
	DepartmentFormDto findDepartmentInfoById(Long departmentId);
}
