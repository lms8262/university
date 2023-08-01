package com.university.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "lecture_registration")
@Getter
@Setter
@ToString
@IdClass(LectureRegistrationId.class)
public class LectureRegistration {
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Student student;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Lecture lecture;
	
	// 수강신청용 정적 팩토리 메소드
	public static LectureRegistration createLectureRegistration(Student student, Lecture lecture) {
		LectureRegistration lectureRegistration = new LectureRegistration();
		lectureRegistration.setLecture(lecture);
		lectureRegistration.setStudent(student);
		
		lecture.addNumOfStudent();
		
		return lectureRegistration;
	}
}
