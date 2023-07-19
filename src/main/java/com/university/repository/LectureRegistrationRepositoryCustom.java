package com.university.repository;

import java.util.List;

import com.university.dto.LectureScheduleDto;
import com.university.entity.Lecture;
import com.university.entity.LectureRegistration;

public interface LectureRegistrationRepositoryCustom {
	Integer getTotalCreaditByStudentId(Long studentId);
	
	List<Lecture> getRegistrationLectureListOfStudent(Long studentId);
	
	List<LectureScheduleDto> getLectureRegistrationHistory(Long studentId);
	
	LectureRegistration findbyLectureCodeIdAndStudentId(Long lectureCodeId, Long studentId);
}
