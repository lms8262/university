package com.university.repository;

import java.util.List;

import com.university.dto.StudentInfoOfLectureDto;
import com.university.dto.StudentLectureScoreInfoDto;
import com.university.dto.YearSemesterDto;
import com.university.entity.Lecture;
import com.university.entity.StudentLecture;

public interface StudentLectureRepositoryCustom {
	List<StudentInfoOfLectureDto> getStudentInfoAndGradeList(Long professorId, Long lectureId);
	
	StudentLecture findbyLectureCodeIdAndStudentId(Long lectureCodeId, Long studentId);
	
	List<Lecture> getCurrentRegistrationLectureListOfStudent(Long studentId, Integer year, Integer semester);
	
	StudentInfoOfLectureDto getStudentInfoForModifyGrade(Long lectureId, Long studentId);
	
	List<StudentLectureScoreInfoDto> getStudentLectureScoreInfoList(Long studentId, Integer year, Integer semester, String type);
	
	Integer getTotalCreditOfStudentLecture(Long studentId, Integer year, Integer semester, String type, String grade);
	
	Double getTotalScoreOfStudentLecture(Long studentId, Integer year, Integer semester, String type);
	
	List<Integer> getAllYearsOfStudentLecture(Long studentId);
	
	List<YearSemesterDto> getAllYearSemester(Long studentId);
}
