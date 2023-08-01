package com.university.dto;

import com.university.entity.LectureRoom;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 강의실 등록 수정용 dto
@Getter
@Setter
@NoArgsConstructor
public class LectureRoomFormDto {
	
	@NotNull(message = "강의실 호수는 필수입력 값입니다.")
	@Min(value= 101, message = "강의실 번호는 101 ~ 999 사이 숫자로 입력해주세요.")
	@Max(value = 999, message = "강의실 번호는 101 ~ 999 사이 숫자로 입력해주세요.")
	private Long lectureRoomNumber;
	
	private Long collegeId;
	
	public static LectureRoomFormDto of(LectureRoom lectureRoom) {
		LectureRoomFormDto lectureRoomFormDto = new LectureRoomFormDto();
		Long lectureRoomNumber = Long.parseLong(lectureRoom.getId().substring(1));
		lectureRoomFormDto.setLectureRoomNumber(lectureRoomNumber);
		lectureRoomFormDto.setCollegeId(lectureRoom.getCollege().getId());
		
		return lectureRoomFormDto;
	}
}
