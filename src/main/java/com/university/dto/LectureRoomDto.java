package com.university.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 강의실 목록 출력용 dto
@Getter
@Setter
@NoArgsConstructor
public class LectureRoomDto {
	private String lectureRoomId;
	
	private String collegeName;
	
	@QueryProjection
	public LectureRoomDto(String lectureRoomId, String collegeName) {
		this.lectureRoomId = lectureRoomId;
		this.collegeName = collegeName;
	}
	
}
