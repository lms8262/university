package com.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "student_lecture")
@Getter
@Setter
@ToString
public class StudentLecture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture_id", nullable = false)
	private Lecture lecture;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade", nullable = false)
	private GradeScore gradeScore;
	
	public static StudentLecture createStudentLecture(Student student, Lecture lecture, GradeScore gradeScore) {
		StudentLecture studentLecture = new StudentLecture();
		studentLecture.setStudent(student);
		studentLecture.setLecture(lecture);
		studentLecture.setGradeScore(gradeScore);
		return studentLecture;
	}
	
	public void updateGrade(GradeScore gradeScore) {
		this.gradeScore = gradeScore;
	}
}
