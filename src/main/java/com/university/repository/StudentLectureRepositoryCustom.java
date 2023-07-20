package com.university.repository;

import java.util.List;

import com.university.dto.StudentInfoOfLectureDto;

public interface StudentLectureRepositoryCustom {
	List<StudentInfoOfLectureDto> getStudentInfoAndGradeList(Long professorId, Long lectureId);
}
