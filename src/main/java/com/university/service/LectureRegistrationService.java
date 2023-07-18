package com.university.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.university.dto.LectureScheduleDto;
import com.university.entity.Lecture;
import com.university.entity.LectureRegistration;
import com.university.entity.LectureRegistrationId;
import com.university.entity.Student;
import com.university.exception.OutOfCreditException;
import com.university.exception.TimeOverlapException;
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
		
		// 학생id로 수강신청 목록에서 총 신청 학점 뽑아서, 기존 학점 + 신청과목 학점 = 18점 초과하는지 확인
		Integer totalCredit = lectureRegistrationRepository.getTotalCreaditByStudentId(studentId);
		if(totalCredit + lecture.getCredit() > 18) {
			throw new OutOfCreditException("최대 신청 학점 초과입니다. 현재 총 신청 학점은" + totalCredit + " 점 입니다.");
		}
		
		// 학생id로 수강신청 목록에서 리스트 뽑아서 신청하려는 강의하고 요일, 시간 겹치는지 확인
		String day = lecture.getDay();
		Integer startTime = lecture.getStartTime();
		Integer endTime = lecture.getEndTime();
		
		List<Lecture> registrationLectureListOfStudent = lectureRegistrationRepository.getRegistrationLectureListOfStudent(studentId);
		for(Lecture lec : registrationLectureListOfStudent) {
			if(StringUtils.equals(lec.getDay(), day)) {
				if((startTime <= lec.getStartTime() && lec.getStartTime() < endTime)
						||(startTime < lec.getEndTime() && lec.getEndTime() < endTime)) {
					throw new TimeOverlapException("이전에 신청한 강의와 시간이 중복됩니다.");
				}
			}
		}
		
		LectureRegistration lectureRegistration = LectureRegistration.createLectureRegistration(student, lecture);

		lectureRegistrationRepository.save(lectureRegistration);
	}
	
	// 수강신청 내역 목록
	public List<LectureScheduleDto> getLectureRegistrationHistory(Long studentId) {
		return lectureRegistrationRepository.getLectureRegistrationHistory(studentId);
	}
	
	// 수강신청 취소
	@Transactional
	public void cancelLectureRegistration(Long lectureId, Long studentId) {
		LectureRegistrationId lectureRegistrationId = new LectureRegistrationId();
		lectureRegistrationId.setLecture(lectureId);
		lectureRegistrationId.setStudent(studentId);
		LectureRegistration lectureRegistration = lectureRegistrationRepository.findById(lectureRegistrationId).orElseThrow(EntityNotFoundException::new);
		
		lectureRegistration.getLecture().removeNumOfStudent();
		lectureRegistrationRepository.delete(lectureRegistration);
	}
}
