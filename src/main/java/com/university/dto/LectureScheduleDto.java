package com.university.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureScheduleDto {
	private String departmentName;
	
	private Long lectureId;
	
	private String type;
	
	private String lectureName;
	
	private String professorName;
	
	private Integer credit;
	
	private String day;
	
	private Integer startTime;
	
	private Integer endTime;
	
	private String lectureRoomId;
	
	private Integer numOfStudent;
	
	private Integer capacity;
	
	@QueryProjection
	public LectureScheduleDto(String departmentName, Long lectureId, String type, String lectureName,
			String professorName, Integer credit, String day, Integer startTime, Integer endTime,
			String lectureRoomId, Integer numOfStudent, Integer capacity) {
		this.departmentName = departmentName;
		this.lectureId = lectureId;
		this.type = type;
		this.lectureName = lectureName;
		this.professorName = professorName;
		this.credit = credit;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.lectureRoomId = lectureRoomId;
		this.numOfStudent = numOfStudent;
		this.capacity = capacity;
	}
}
