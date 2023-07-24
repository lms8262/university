package com.university.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.LectureScheduleDto;
import com.university.dto.LectureSearchDto;
import com.university.dto.ProfessorLectureDto;
import com.university.dto.ProfessorLectureSearchDto;
import com.university.entity.Student;
import com.university.repository.LectureRepository;
import com.university.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {
	
	private final LectureRepository lectureRepository;
	private final StudentRepository studentRepository;
	
	// 강의 시간표 출력(이번 학기)
	public Page<LectureScheduleDto> getLectureScheduleList(LectureSearchDto lectureSearchDto, Pageable pageable) {
		return lectureRepository.getLectureScheduleList(lectureSearchDto, pageable);
	}
	
	// 수강 신청 가능 강의 목록 출력
	public Page<LectureScheduleDto> getRegistrationAbleLectureList(Long userId, Pageable pageable) {
		Student student = studentRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
		Long departmentId = student.getDepartment().getId();
		return lectureRepository.getRegistrationAbleLectureList(departmentId, pageable);
	}
	
	// 이번 학기 강의중인 강의 목록 출력(교수) 
	public List<ProfessorLectureDto> getProfessorLectureListOfCurrentSemester(Long professorId) {
		return lectureRepository.getProfessorLectureListOfCurrentSemester(professorId);
	}
	
	// 강의가 존재하는 년도 및 학기 출력(교수)
	public List<ProfessorLectureSearchDto> getProfessorLectureGroupByYearAndSemester(Long professorId) {
		return lectureRepository.getProfessorLectureGroupByYearAndSemester(professorId);
	}
	
	// 학기별 강의 목록 출력(교수)
	public List<ProfessorLectureDto> getProfessorLectureList(Long professorId ,ProfessorLectureSearchDto professorLectureSearchDto) {
		return lectureRepository.getProfessorLectureList(professorId, professorLectureSearchDto);
	}
}
