package com.university.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.CollegeFormDto;
import com.university.dto.DepartmentFormDto;
import com.university.dto.ProfessorInfoDto;
import com.university.dto.StudentInfoDto;
import com.university.dto.UserSearchDto;
import com.university.entity.College;
import com.university.entity.Department;
import com.university.exception.OverlapException;
import com.university.repository.CollegeRepository;
import com.university.repository.DepartmentRepository;
import com.university.repository.LectureRepository;
import com.university.repository.LectureRoomRepository;
import com.university.repository.ProfessorRepository;
import com.university.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StaffService {
	
	private final StudentRepository studentRepository;
	private final ProfessorRepository professorRepository; 
	private final CollegeRepository collegeRepository;
	private final DepartmentRepository departmentRepository;
	private final LectureRoomRepository lectureRoomRepository;
	private final LectureRepository lectureRepository;
	
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
	
	// 단과대명 중복 체크
	private boolean checkEqualCollegeName(String collegeName) {
		College college = collegeRepository.findByName(collegeName);
		
		// 이미 사용중인 단과대명일때
		if(college != null) {
			return false;
		}
		
		return true;
	}
	
	// 단과대 코드 중복 체크
	private boolean checkEqualCollegeCode(String collegeCode) {
		College college = collegeRepository.findByCollegeCode(collegeCode);
		
		// 이미 사용중인 코드일때
		if(college != null) {
			return false;
		}
		
		return true;
	}
	
	// 신규 단과대 생성
	@Transactional
	public void createCollege(CollegeFormDto collegeFormDto) {
		if(!checkEqualCollegeName(collegeFormDto.getCollegeName())) {
			throw new OverlapException("이미 사용중인 단과대명입니다.");
		}
		
		if(!checkEqualCollegeCode(collegeFormDto.getCollegeCode())) {
			throw new OverlapException("이미 사용중인 단과대 코드입니다.");
		}
		
		College college = College.createCollege(collegeFormDto.getCollegeName(), collegeFormDto.getCollegeCode());
		collegeRepository.save(college);
	}
	
	// 단과대 정보 수정시 기존 정보 가져오기
	public CollegeFormDto findCollegeInfoById(Long collegeId) {
		College college = collegeRepository.findById(collegeId).orElseThrow(EntityNotFoundException::new);
		return CollegeFormDto.of(college);
	}
	
	// 단과대 정보 수정
	@Transactional
	public void updateCollege(CollegeFormDto collegeFormDto) {
		if(collegeRepository.findByNameAndIdNot(collegeFormDto.getCollegeName(), collegeFormDto.getCollegeId()) != null) {
			throw new OverlapException("이미 사용중인 단과대명입니다.");
		}
		
		if(collegeRepository.findByCollegeCodeAndIdNot(collegeFormDto.getCollegeCode(), collegeFormDto.getCollegeId()) != null) {
			throw new OverlapException("이미 사용중인 단과대 코드입니다.");
		}
		
		College college = collegeRepository.findById(collegeFormDto.getCollegeId()).orElseThrow(EntityNotFoundException::new);
		college.updateCollege(collegeFormDto.getCollegeName(), collegeFormDto.getCollegeCode());
	}
	
	// 단과대 삭제(단과대 테이블 참조중인 테이블 fk null로 바꿔주기)
	@Transactional
	public void deleteCollege(Long collegeId) {
		College college = collegeRepository.findById(collegeId).orElseThrow(EntityNotFoundException::new);
		
		// College 테이블 참조중인 테이블 fk null로 바꾸기
		departmentRepository.setCollegeNull(college);
		lectureRoomRepository.setLectureRoomNull(college);
		
		collegeRepository.delete(college);
	}
	
	// 학과 리스트 조회
	public List<DepartmentFormDto> getDepartmentList() {
		return departmentRepository.getDepartmentList();
	}
	
	// 학과명 중복 체크
	private boolean checkEqualDepartmentName(String departmentName) {
		Department department = departmentRepository.findByName(departmentName);
		
		// 이미 사용중인 학과명일때
		if(department != null) {
			return false;
		}
		
		return true;
	}
	
	// 신규 학과 생성
	@Transactional
	public void createDepartment(DepartmentFormDto departmentFormDto) {
		if(!checkEqualDepartmentName(departmentFormDto.getDepartmentName()) ) {
			throw new OverlapException("이미 사용중인 학과명입니다.");
		}
		
		College college = collegeRepository.findById(departmentFormDto.getCollegeId()).orElseThrow(EntityNotFoundException::new);
		
		Department department = Department.createDepartment(departmentFormDto.getDepartmentName(), college);
		departmentRepository.save(department);
	}
	
	// 학과 정보 수정시 기존 정보 가져오기
	public DepartmentFormDto findDepartmentInfoById(Long departmentId) {
		return departmentRepository.findDepartmentInfoById(departmentId);
	}
	
	// 학과 정보 수정
	@Transactional
	public void updateDepartment(DepartmentFormDto departmentFormDto) {
		if(departmentRepository.findByNameAndIdNot(departmentFormDto.getDepartmentName(), departmentFormDto.getDepartmentId()) != null) {
			throw new OverlapException("이미 사용중인 학과명입니다.");
		}
		
		College college = collegeRepository.findById(departmentFormDto.getCollegeId()).orElseThrow(EntityNotFoundException::new);
		Department department = departmentRepository.findById(departmentFormDto.getDepartmentId()).orElseThrow(EntityNotFoundException::new);
		department.updateDepartment(departmentFormDto.getDepartmentName(), college);
	}
	
	// 학과 삭제(학과 테이블 참조중인 테이블 fk null로 바꿔주기)
	@Transactional
	public void deleteDepartment(Long departmentId) {
		Department department = departmentRepository.findById(departmentId).orElseThrow(EntityNotFoundException::new);
		
		// Department 테이블 참조중인 테이블 fk null로 바꾸기
		studentRepository.setDepartmentNull(department);
		professorRepository.setDepartmentNull(department);
		lectureRepository.setDepartmentNull(department);
		
		departmentRepository.delete(department);
	}
}
