package com.university.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.entity.Lecture;
import com.university.entity.LectureRegistration;
import com.university.entity.LectureRegistrationId;
import com.university.entity.Student;
import com.university.repository.LectureRegistrationRepository;
import com.university.repository.LectureRepository;
import com.university.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureRegistrationService {
	private final LectureRegistrationRepository lectureRegistrationRepository;
	private final LectureRepository lectureRepository;
	private final StudentRepository studentRepository;
	
	// 이미 수강신청 했던 과목인지 검사
	public boolean checkLectureRegistration(Long lectureId, Long studentId) {
		LectureRegistrationId lectureRegistrationId = new LectureRegistrationId();
		lectureRegistrationId.setLecture(lectureId);
		lectureRegistrationId.setStudent(studentId);
		LectureRegistration lectureRegistration = lectureRegistrationRepository.findById(lectureRegistrationId).orElse(null);
		
		// 이미 신청한 과목인 경우
		if(lectureRegistration != null) {
			return false;
		}
		
		return true;
	}
	
	// 수강신청
	@Transactional
	public void lectureRegistration(Long lectureId, Long studentId) {
		Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(EntityNotFoundException::new);
		Student student = studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);
		
		// 학생id로 수강신청 목록에서 리스트 뽑아서 신청하려는 강의시간하고 겹치는지 확인
		// 학생id로 수강신청 목록에서 총 신청 학점 뽑아서 18점 이상인지 확인
		
		LectureRegistration lectureRegistration = LectureRegistration.createLectureRegistration(student, lecture);
		
		lectureRegistrationRepository.save(lectureRegistration);
	}
}
