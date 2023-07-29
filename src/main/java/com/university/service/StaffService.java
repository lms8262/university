package com.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.ProfessorInfoDto;
import com.university.dto.StudentInfoDto;
import com.university.dto.UserSearchDto;
import com.university.repository.ProfessorRepository;
import com.university.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StaffService {
	
	private final StudentRepository studentRepository;
	private final ProfessorRepository professorRepository; 
	
	public Page<StudentInfoDto> getStudentInfoList(UserSearchDto userSearchDto, Pageable pageable) {
		return studentRepository.getStudentInfoList(userSearchDto, pageable);
	}
	
	public Page<ProfessorInfoDto> getProfessorInfoList(UserSearchDto userSearchDto, Pageable pageable) {
		return professorRepository.getProfessorInfoList(userSearchDto, pageable);
	}
}
