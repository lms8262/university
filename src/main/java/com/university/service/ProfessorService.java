package com.university.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.StudentInfoOfLectureDto;
import com.university.entity.Lecture;
import com.university.repository.LectureRegistrationRepository;
import com.university.repository.LectureRepository;
import com.university.repository.StudentLectureRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorService {
	
	private final LectureRepository lectureRepository;
	private final LectureRegistrationRepository lectureRegistrationRepository;
	private final StudentLectureRepository studentLectureRepository;
	
	// 교수 본인이 강의하는 강의가 맞는지 확인 -> 맞으면 강의명 리턴
	public String validateInputScore(Long professorId, Long lectureId) {
		Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(EntityNotFoundException::new);
		
		// 아닐 경우
		if(!professorId.equals(lecture.getProfessor().getId())) {
			return null;
		}
		
		// 맞을 경우
		return lecture.getName();
	}
	
	// 강의 성적 미입력 학생 목록 불러오기
	public List<StudentInfoOfLectureDto> getStudentInfoList(Long professorId, Long lectureId) {
		return lectureRegistrationRepository.getStudentInfoList(professorId, lectureId);
	}
	
	// 강의 성적 입력 학생 목록 불러오기
	public List<StudentInfoOfLectureDto> getStudentInfoAndGradeList(Long professorId, Long lectureId) {
		return studentLectureRepository.getStudentInfoAndGradeList(professorId, lectureId);
	}
	
}
