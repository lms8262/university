package com.university.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.DepartmentDto;
import com.university.entity.Department;
import com.university.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {
	
	private final ModelMapper modelMapper;
	private final DepartmentRepository departmentRepository;
	
	// 전체 학과 리스트 불러오기
	public List<DepartmentDto> findAllDepartmentList() {
		List<Department> departmentEntityList = departmentRepository.findAll();
		List<DepartmentDto> departmentDtoList = new ArrayList<>();
		for(Department department : departmentEntityList) {
			DepartmentDto departmentDto = modelMapper.map(department, DepartmentDto.class);
			departmentDtoList.add(departmentDto);
		}
		return departmentDtoList;
	}
}
