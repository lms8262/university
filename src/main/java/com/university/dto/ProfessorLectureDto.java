package com.university.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorLectureDto {
	private Long lectureId;
	
	private String lectureCodeDetail;
	
	private String type;

	private String lectureName;

	private Integer credit;

	private String day;

	private Integer startTime;

	private Integer endTime;

	private String lectureRoomId;

	private Integer numOfStudent;

	private Integer capacity;
	
	@QueryProjection
	public ProfessorLectureDto(Long lectureId, String lectureCodeDetail, String type, String lectureName, Integer credit, String day, Integer startTime, Integer endTime, String lectureRoomId, Integer numOfStudent, Integer capacity) {
		this.lectureId = lectureId;
		this.lectureCodeDetail = lectureCodeDetail;
		this.type = type;
		this.lectureName = lectureName;
		this.credit = credit;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.lectureRoomId = lectureRoomId;
		this.numOfStudent = numOfStudent;
		this.capacity = capacity;
	}
}
