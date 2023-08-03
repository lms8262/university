package com.university.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.university.entity.Lecture;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LectureFormDto {
	private Long lectureId;
	
	@NotEmpty(message = "강의명은 필수입력 값입니다.")
	private String lectureName;
	
	private Long lectureCodeId;
	
	private Long departmentId;
	
	@NotNull(message = "교수번호는 필수입력 값입니다.")
	@Min(value = 230001, message = "교수번호는 230001~999999 사이의 숫자로 입력해주세요.")
	@Max(value = 999999, message = "교수번호는 230001~999999 사이의 숫자로 입력해주세요.")
	private Long professorId;
	
	@Pattern(regexp = "[A-Z]{1}[1-9]{1}[0-9]{2}", message = "강의실 번호는 영어 대문자 1자리 + 100~999 사이의 숫자 형식으로 입력해주세요.")
	private String lectureRoomId;
	
	private String type;
	
	private Integer credit;
	
	@NotNull(message = "강의연도는 필수입력 값입니다.")
	@Min(value = 1900, message = "강의연도는 1900 ~ 9999 사이의 숫자로 입력해주세요.")
	@Max(value = 9999, message = "강의연도는 1900 ~ 9999 사이의 숫자로 입력해주세요.")
	private Integer year;
	
	private Integer semester;
	
	private String day;
	
	private Integer startTime;
	
	private Integer endTime;
	
	private Integer numOfStudent;
	
	@NotNull(message = "정원은 필수입력 값입니다.")
	@Min(value = 5, message = "강의 최소인원은 5명입니다.")
	@Max(value = 50, message = "강의 최대인원은 50명입니다.")
	private Integer capacity;
	
	@QueryProjection
	public LectureFormDto(Long lectureId, String lectureName, Long lectureCodeId, Long departmentId, Long professorId,
			String lectureRoomId, String type, Integer credit, Integer year, Integer semester, String day,
			Integer startTime, Integer endTime, Integer numOfStudent, Integer capacity) {
		this.lectureId = lectureId;
		this.lectureName = lectureName;
		this.lectureCodeId = lectureCodeId;
		this.departmentId = departmentId;
		this.professorId = professorId;
		this.lectureRoomId = lectureRoomId;
		this.type = type;
		this.credit = credit;
		this.year = year;
		this.semester = semester;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.numOfStudent = numOfStudent;
		this.capacity = capacity;
	}
	
	public static LectureFormDto of(Lecture lecture) {
		LectureFormDto lectureFormDto = new LectureFormDto();
		lectureFormDto.setLectureId(lecture.getId());
		lectureFormDto.setLectureName(lecture.getName());
		lectureFormDto.setLectureCodeId(lecture.getLectureCode().getId());
		lectureFormDto.setDepartmentId(lecture.getDepartment().getId());
		lectureFormDto.setProfessorId(lecture.getProfessor().getId());
		lectureFormDto.setLectureRoomId(lecture.getLectureRoom().getId());
		lectureFormDto.setType(lecture.getType());
		lectureFormDto.setCredit(lecture.getCredit());
		lectureFormDto.setYear(lecture.getYear());
		lectureFormDto.setSemester(lecture.getSemester());
		lectureFormDto.setDay(lecture.getDay());
		lectureFormDto.setStartTime(lecture.getStartTime());
		lectureFormDto.setEndTime(lecture.getEndTime());
		lectureFormDto.setNumOfStudent(lecture.getNumOfStudent());
		lectureFormDto.setCapacity(lecture.getCapacity());
		
		return lectureFormDto;
	}
	
}
