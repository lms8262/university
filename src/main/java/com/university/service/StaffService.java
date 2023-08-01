package com.university.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.CollegeFormDto;
import com.university.dto.DepartmentFormDto;
import com.university.dto.LectureRoomDto;
import com.university.dto.LectureRoomFormDto;
import com.university.dto.ProfessorInfoDto;
import com.university.dto.StudentInfoDto;
import com.university.dto.UserSearchDto;
import com.university.entity.College;
import com.university.entity.Department;
import com.university.entity.LectureRoom;
import com.university.entity.Professor;
import com.university.entity.Student;
import com.university.entity.User;
import com.university.exception.OverlapException;
import com.university.repository.CollegeRepository;
import com.university.repository.DepartmentRepository;
import com.university.repository.LectureRepository;
import com.university.repository.LectureRoomRepository;
import com.university.repository.ProfessorRepository;
import com.university.repository.StudentRepository;
import com.university.repository.UserRepository;

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
	private final UserRepository userRepository;
	
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
	
	// 단과대 삭제(관련된 하위 데이터 모두 삭제됨)
	@Transactional
	public void deleteCollege(Long collegeId) {
		College college = collegeRepository.findById(collegeId).orElseThrow(EntityNotFoundException::new);
		
		// 연관관계 없는 user 테이블 데이터 직접 삭제
		List<Long> userIdList = new ArrayList<>();
		List<Department> departmentList = departmentRepository.findByCollege_Id(collegeId);
		for(Department department : departmentList) {
			for(Professor professor : professorRepository.findByDepartment_Id(department.getId())) {
				userIdList.add(professor.getId());
			}
			for(Student student : studentRepository.findByDepartment_Id(department.getId())) {
				userIdList.add(student.getId());
			}
		}
		userRepository.deleteAllById(userIdList);
		
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
	
	// 학과 삭제(관련된 하위 데이터 모두 삭제됨)
	@Transactional
	public void deleteDepartment(Long departmentId) {
		Department department = departmentRepository.findById(departmentId).orElseThrow(EntityNotFoundException::new);
		
		// 연관관계 없는 user 테이블 데이터 직접 삭제
		List<Long> userIdList = new ArrayList<>();
		for(Professor professor : professorRepository.findByDepartment_Id(department.getId())) {
			userIdList.add(professor.getId());
		}
		for(Student student : studentRepository.findByDepartment_Id(department.getId())) {
			userIdList.add(student.getId());
		}
		userRepository.deleteAllById(userIdList);
		
		departmentRepository.delete(department);
	}
	
	// 강의실 리스트 조회
	public Page<LectureRoomDto> getLectureRoomList(Pageable pageable) {				
		return lectureRoomRepository.getLectureRoomList(pageable);
	}
	
	// 강의실 중복 여부 체크
	private boolean checkEqualLectureRoom(String lectureRoomId) {
		LectureRoom lectureRoom = lectureRoomRepository.findById(lectureRoomId).orElse(null);
		
		// 이미 존재하는 강의실 일때
		if(lectureRoom != null) {
			return false;
		}
		
		return true;
	}
	
	// 신규 강의실 생성
	@Transactional
	public void createLectureRoom(LectureRoomFormDto lectureRoomFormDto) {
		College college = collegeRepository.findById(lectureRoomFormDto.getCollegeId()).orElseThrow(EntityNotFoundException::new);
		String lectureRoomId = college.getCollegeCode() + lectureRoomFormDto.getLectureRoomNumber();
		
		if(!checkEqualLectureRoom(lectureRoomId)) {
			throw new OverlapException("이미 존재하는 강의실입니다.");
		}
		
		LectureRoom lectureRoom = new LectureRoom();
		lectureRoom.setId(lectureRoomId);
		lectureRoom.setCollege(college);
		
		lectureRoomRepository.save(lectureRoom);
	}
	
	// 강의실 정보 수정시 기존 정보 가져오기
	public LectureRoomFormDto findLectureRoomInfoById(String lectureRoomId) {
		LectureRoom lectureRoom = lectureRoomRepository.findById(lectureRoomId).orElseThrow(EntityNotFoundException::new);
		return LectureRoomFormDto.of(lectureRoom);
	}
}
