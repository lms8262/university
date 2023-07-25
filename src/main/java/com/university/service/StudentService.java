package com.university.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.StudentLectureScoreDto;
import com.university.dto.StudentLectureScoreInfoDto;
import com.university.repository.StudentLectureRepository;
import com.university.util.SemesterUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {
	
	private final StudentLectureRepository studentLectureRepository;
	
	public List<StudentLectureScoreInfoDto> getCurrentStudentLectureList(Long studentId) {
		return studentLectureRepository.getStudentLectureScoreInfoList(studentId, SemesterUtil.CURRENT_YEAR, SemesterUtil.CURRENT_SEMESTER, "");
	}
	
	// 현재 학기 취득 학점 및 평점
	public StudentLectureScoreDto getCurrentStudentScore(Long studentId) {
		Integer year = SemesterUtil.CURRENT_YEAR;
		Integer semester = SemesterUtil.CURRENT_SEMESTER;
		// 현재 학기에 수강한 강의 총 학점
		Integer totalCredit = studentLectureRepository.getTotalCreditOfStudentLecture(studentId, year, semester, "", "");
		// F학점 제외
		Integer totalGetCredit = studentLectureRepository.getTotalCreditOfStudentLecture(studentId, year, semester, "", "F");
		Double totalScore = studentLectureRepository.getTotalScoreOfStudentLecture(studentId, year, semester, "");
		Double averageScore = totalScore / totalCredit;
		
		// 현재 학기에 수강한 강의가 없을 경우
		if(totalCredit == 0) {
			return null;
		}
		
		return new StudentLectureScoreDto(year, semester, totalCredit, totalGetCredit, averageScore);
	}
}
