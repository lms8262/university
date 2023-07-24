package com.university.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.university.dto.LectureScheduleDto;
import com.university.dto.LectureSearchDto;
import com.university.dto.ProfessorLectureDto;
import com.university.dto.ProfessorLectureSearchDto;


public interface LectureRepositoryCustom {
	Page<LectureScheduleDto> getLectureScheduleList(LectureSearchDto lectureSearchDto, Pageable pageable);
	
	Page<LectureScheduleDto> getRegistrationAbleLectureList(Long departmentId, Pageable pageable);
	
	List<ProfessorLectureDto> getProfessorLectureListOfCurrentSemester(Long professorId);
	
	List<ProfessorLectureSearchDto> getProfessorLectureGroupByYearAndSemester(Long professorId);
	
	List<ProfessorLectureDto> getProfessorLectureList(Long professorId, ProfessorLectureSearchDto professorLectureSearchDto);
}
