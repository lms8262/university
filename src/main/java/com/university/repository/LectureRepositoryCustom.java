package com.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.university.dto.LectureScheduleDto;
import com.university.dto.LectureSearchDto;


public interface LectureRepositoryCustom {
	Page<LectureScheduleDto> getLectureScheduleList(LectureSearchDto lectureSearchDto, Pageable pageable);
	
	Page<LectureScheduleDto> getRegistrationAbleLectureList(Long departmentId, Pageable pageable);

}