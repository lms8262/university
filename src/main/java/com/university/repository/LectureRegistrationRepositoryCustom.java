package com.university.repository;

import java.util.List;

import com.university.entity.Lecture;

public interface LectureRegistrationRepositoryCustom {
	Integer getTotalCreaditByStudentId(Long studentId);
	
	List<Lecture> getRegistrationLectureListOfStudent(Long studentId);
}
