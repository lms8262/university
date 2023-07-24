package com.university.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.StudentInfoOfLectureDto;
import com.university.entity.GradeScore;
import com.university.entity.Lecture;
import com.university.entity.LectureRegistration;
import com.university.entity.LectureRegistrationId;
import com.university.entity.Student;
import com.university.entity.StudentLecture;
import com.university.repository.GradeScoreRepository;
import com.university.repository.LectureRegistrationRepository;
import com.university.repository.LectureRepository;
import com.university.repository.StudentLectureRepository;
import com.university.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorService {
	
	private final LectureRepository lectureRepository;
	private final LectureRegistrationRepository lectureRegistrationRepository;
	private final StudentLectureRepository studentLectureRepository;
	private final GradeScoreRepository gradeScoreRepository; 
	private final StudentRepository studentRepository;
	
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
	
	// 학생이 강의를 수강 중인지 확인
	public boolean validateRegistration(Long lectureId, Long studentId) {
		LectureRegistrationId lectureRegistrationId = new LectureRegistrationId();
		lectureRegistrationId.setLecture(lectureId);
		lectureRegistrationId.setStudent(studentId);
		LectureRegistration lectureRegistration = lectureRegistrationRepository.findById(lectureRegistrationId).orElse(null);
		
		StudentLecture studentLecture = studentLectureRepository.findByStudentIdAndLectureId(studentId, lectureId);
		
		// 수강 중이지 않은 강의인 경우(수강 신청, 수강 내역에 없는 경우)
		if(lectureRegistration == null && studentLecture == null) {
			return false;
		}
		
		return true;
	}
	
	// 강의 성적 미입력 학생 목록 불러오기
	public List<StudentInfoOfLectureDto> getStudentInfoList(Long professorId, Long lectureId) {
		return lectureRegistrationRepository.getStudentInfoList(professorId, lectureId);
	}
	
	// 강의 성적 입력 학생 목록 불러오기
	public List<StudentInfoOfLectureDto> getStudentInfoAndGradeList(Long professorId, Long lectureId) {
		return studentLectureRepository.getStudentInfoAndGradeList(professorId, lectureId);
	}
	
	// 성적 입력할 학생 정보 불러오기
	public StudentInfoOfLectureDto getStudentInfoForInputGrade(Long lectureId, Long studentId) {
		return lectureRegistrationRepository.getStudentInfoForInputGrade(lectureId, studentId);
	}
	
	// 입력 가능한 성적 등급 목록 불러오기
	public List<String> getGradeList() {
		return gradeScoreRepository.getAllGradeId();
	}
	
	// 강의를 듣는 학생 성적 입력
	@Transactional
	public void inputScore(Long lectureId, Long studentId, String grade) {
		LectureRegistrationId lectureRegistrationId = new LectureRegistrationId();
		lectureRegistrationId.setStudent(studentId);
		lectureRegistrationId.setLecture(lectureId);
		LectureRegistration lectureRegistration = lectureRegistrationRepository.findById(lectureRegistrationId).orElseThrow(EntityNotFoundException::new);
		
		Lecture lecture = lectureRegistration.getLecture();
		Student sutdent = lectureRegistration.getStudent();
		GradeScore gradeScore = gradeScoreRepository.findById(grade).orElseThrow(EntityNotFoundException::new);
		
		// 수강 내역 테이블에 저장
		StudentLecture studentLecture = StudentLecture.createStudentLecture(sutdent, lecture, gradeScore);
		studentLectureRepository.save(studentLecture);
		
		// 수강신청 테이블에서 제거
		lectureRegistrationRepository.delete(lectureRegistration);
	}
	
	// 성적 수정할 학생 정보 불러오기
	public StudentInfoOfLectureDto getStudentInfoForModifyGrade(Long lectureId, Long studentId) {
		return studentLectureRepository.getStudentInfoForModifyGrade(lectureId, studentId);
	}
	
	// 강의 내역 성적 수정
	@Transactional
	public void updateScore(Long lectureId, Long studentId, String grade) {
		StudentLecture studentLecture = studentLectureRepository.findByStudentIdAndLectureId(studentId, lectureId);
		GradeScore gradeScore = gradeScoreRepository.findById(grade).orElseThrow(EntityNotFoundException::new);
		
		// 성적 수정
		studentLecture.updateGrade(gradeScore);
	}
	
	// 강의 내역 성적 입력취소
	@Transactional
	public void deleteScore(Long lectureId, Long studentId) {
		// 수강 내역 테이블에서 삭제
		StudentLecture studentLecture = studentLectureRepository.findByStudentIdAndLectureId(studentId, lectureId);
		studentLectureRepository.delete(studentLecture);
		
		// 수강 신청 테이블에 다시 생성
		Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(EntityNotFoundException::new);
		Student student = studentRepository.findById(studentId).orElseThrow(EntityNotFoundException::new);
	
		LectureRegistration lectureRegistration = new LectureRegistration();
		lectureRegistration.setLecture(lecture);
		lectureRegistration.setStudent(student);
		
		lectureRegistrationRepository.save(lectureRegistration);
	}
}
