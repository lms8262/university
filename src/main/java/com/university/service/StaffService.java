package com.university.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.CollegeFormDto;
import com.university.dto.ProfessorInfoDto;
import com.university.dto.StudentInfoDto;
import com.university.dto.UserSearchDto;
import com.university.entity.College;
import com.university.exception.OverlapException;
import com.university.repository.CollegeRepository;
import com.university.repository.ProfessorRepository;
import com.university.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StaffService {
	
	private final StudentRepository studentRepository;
	private final ProfessorRepository professorRepository; 
	private final CollegeRepository collegeRepository;
	
	// 학생 명단 조회
	public Page<StudentInfoDto> getStudentInfoList(UserSearchDto userSearchDto, Pageable pageable) {
		return studentRepository.getStudentInfoList(userSearchDto, pageable);
	}
	
	// 교수 명단 조회
	public Page<ProfessorInfoDto> getProfessorInfoList(UserSearchDto userSearchDto, Pageable pageable) {
		return professorRepository.getProfessorInfoList(userSearchDto, pageable);
	}
	
	// 단과대 리스트 조회
	public List<CollegeFormDto> getCollegeList() {
		List<College> collegeList = collegeRepository.findAll();
		List<CollegeFormDto> collegeFromDtoList = new ArrayList<>();
		for(College college : collegeList) {
			CollegeFormDto collegeFormDto = CollegeFormDto.of(college);
			collegeFromDtoList.add(collegeFormDto);
		}
		return collegeFromDtoList;
	}
	
	// 단과대 코드 중복 체크
	private boolean checkEqualCollegeCode(String collegeCode) {
		College college = collegeRepository.findByCollegeCode(collegeCode);
		
		// 이미 등록된 코드일때
		if(college != null) {
			return false;
		}
		
		return true;
	}
	
	// 신규 단과대 생성
	@Transactional
	public void createCollege(CollegeFormDto collegeFormDto) {
		if(!checkEqualCollegeCode(collegeFormDto.getCollegeCode())) {
			throw new OverlapException("이미 사용중인 단과대 코드입니다.");
		}
		
		College college = College.createCollege(collegeFormDto.getCollegeName(), collegeFormDto.getCollegeCode());
		collegeRepository.save(college);
	}
	
}
