package com.university.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.StudentLectureScoreDto;
import com.university.dto.StudentLectureScoreInfoDto;
import com.university.dto.StudentLectureSearchDto;
import com.university.dto.YearSemesterDto;
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
	
	public List<Integer> getAllYearsOfStudentLecture(Long studentId) {
		return studentLectureRepository.getAllYearsOfStudentLecture(studentId);
	}
	
	// 해당 학기 성적 조회
	public List<StudentLectureScoreInfoDto> getSemesterStudentLectureList(StudentLectureSearchDto studentLectureSearchDto ,Long studentId) {
		Integer year = studentLectureSearchDto.getYear();
		Integer semester = studentLectureSearchDto.getSemester();
		String type = studentLectureSearchDto.getType();
		return studentLectureRepository.getStudentLectureScoreInfoList(studentId, year, semester, type);
	}
	
	// 모든 학기 성적 조회
	public List<StudentLectureScoreDto> getAllSemesterStudentScore(Long studentId) {
		List<YearSemesterDto> yearSemesterList = studentLectureRepository.getAllYearSemester(studentId);		
		List<StudentLectureScoreDto> studentLectureScoreDtoList = new ArrayList<>();
		
		for(YearSemesterDto yearSemester : yearSemesterList) {
			Integer year = yearSemester.getYear();
			Integer semester = yearSemester.getSemester();
			
			Integer totalCredit = studentLectureRepository.getTotalCreditOfStudentLecture(studentId, year, semester, "", "");
			Integer totalGetCredit = studentLectureRepository.getTotalCreditOfStudentLecture(studentId, year, semester, "", "F");
			Double totalScore = studentLectureRepository.getTotalScoreOfStudentLecture(studentId, year, semester, "");
			Double averageScore = totalScore / totalCredit;
			
			StudentLectureScoreDto studentLectureScoreDto = new StudentLectureScoreDto(year, semester, totalCredit, totalGetCredit, averageScore);
			studentLectureScoreDtoList.add(studentLectureScoreDto);
		}
		
		return studentLectureScoreDtoList;
	}
	
	// 모든 학기 성적 누계 합산
	public StudentLectureScoreDto getTotalStudentScore(Long studentId) {
		StudentLectureScoreDto studentLectureScoreDto = new StudentLectureScoreDto();
		
		Integer totalCredit = studentLectureRepository.getTotalCreditOfStudentLecture(studentId, 0, 0, "", "");
		Integer totalGetCredit = studentLectureRepository.getTotalCreditOfStudentLecture(studentId, 0, 0, "", "F");
		Double totalScore = studentLectureRepository.getTotalScoreOfStudentLecture(studentId, 0, 0, "");
		Double averageScore = totalScore / totalCredit;
		
		studentLectureScoreDto.setTotalCredit(totalCredit);
		studentLectureScoreDto.setTotalGetCredit(totalGetCredit);
		studentLectureScoreDto.setAverageScore(averageScore);
		
		return studentLectureScoreDto;
	}
}
