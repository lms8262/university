package com.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
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
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grade", nullable = false)
	private GradeScore gradeScore;
}
