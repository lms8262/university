package com.university.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.university.exception.OutOfCapacityException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "lecture")
@Getter
@Setter
@ToString
@DynamicInsert
public class Lecture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false)
	private Integer credit;
	
	@Column(nullable = false)
	private Integer capacity;
	
	@Column(nullable = false)
	@ColumnDefault("0")
	private Integer numOfStudent;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture_room_id", nullable = false)
	private LectureRoom lectureRoom;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "professor_id", nullable = false)
	private Professor professor;
	
	public void addNumOfStudent() {
		if(numOfStudent >= capacity) {
			throw new OutOfCapacityException("강의 정원 초과입니다.");
		}
		
		numOfStudent++;
	}
}
