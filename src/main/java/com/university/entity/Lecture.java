package com.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@TableGenerator(name = "lecture_id_generator", table = "id_sequences", initialValue = 10001, allocationSize = 1)
public class Lecture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "lecture_id_generator")
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false)
	private Integer credit;
	
	@Column(nullable = false)
	private Integer capacity;
	
	@Column(columnDefinition = "char(2)" , nullable = false)
	private String type;
	
	@Column(nullable = false)
	private Integer year;
	
	@Column(nullable = false)
	private Integer semester;
	
	@Column(columnDefinition = "char(1)" , nullable = false)
	private String day;
	
	@Column(nullable = false)
	private Integer startTime;
	
	@Column(nullable = false)
	private Integer endTime;
	
	@ManyToOne
	@JoinColumn(name = "lecture_room_id", nullable = false)
	private LectureRoom lectureRoom;
	
	@ManyToOne
	@JoinColumn(name = "professor_id", nullable = false)
	private Professor professor;
}
