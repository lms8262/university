package com.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class LectureRegistration {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "lecture_id")
	private Lecture lecture;
}
