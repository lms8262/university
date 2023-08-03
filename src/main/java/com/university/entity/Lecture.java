package com.university.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "lecture")
@Getter
@Setter
@ToString
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
	@JoinColumn(name = "department_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Department department;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture_room_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private LectureRoom lectureRoom;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "professor_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Professor professor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lecture_code_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private LectureCode lectureCode;
	
	// 수강신청시 수강인원 추가
	public void addNumOfStudent() {
		if(numOfStudent >= capacity) {
			throw new OutOfCapacityException("강의 정원 초과입니다.");
		}
		
		numOfStudent++;
	}
	
	// 수강신청 취소시 수강인원 제거 
	public void removeNumOfStudent() {
		numOfStudent--;
	}

	public void updateLecture(String name, Integer credit, Integer capacity, String type,
			Integer year, Integer semester, String day, Integer startTime, Integer endTime, Department department,
			LectureRoom lectureRoom, Professor professor, LectureCode lectureCode) {
		this.name = name;
		this.credit = credit;
		this.capacity = capacity;
		this.type = type;
		this.year = year;
		this.semester = semester;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.department = department;
		this.lectureRoom = lectureRoom;
		this.professor = professor;
		this.lectureCode = lectureCode;
	}
	
}
