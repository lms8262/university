package com.university.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.university.dto.LectureFormDto;
import com.university.dto.LectureScheduleDto;
import com.university.dto.LectureSearchDto;
import com.university.dto.ProfessorLectureDto;
import com.university.dto.ProfessorLectureSearchDto;
import com.university.dto.QLectureFormDto;
import com.university.dto.QLectureScheduleDto;
import com.university.dto.QProfessorLectureDto;
import com.university.dto.QProfessorLectureSearchDto;
import com.university.entity.QDepartment;
import com.university.entity.QLecture;
import com.university.entity.QLectureCode;
import com.university.entity.QLectureRoom;
import com.university.entity.QUser;
import com.university.util.SemesterUtil;

import jakarta.persistence.EntityManager;

public class LectureRepositoryCustomImpl implements LectureRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public LectureRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	// 전공 교양 여부 전체일때 null 처리
	private BooleanExpression lectureTypeEq(String type) {
		return type.equals("") ? null : QLecture.lecture.type.eq(type); 
	}
	
	// 학과 전체일때 null 처리
	private BooleanExpression departmentIdEq(Long departmentId) {
		return departmentId == 0 ? null : QLecture.lecture.department.id.eq(departmentId);
	}
	
	// 검색어(강의명)가 빈문자열 일때 처리
	private BooleanExpression lectureNameLike(String lectureName) {
		return StringUtils.isEmpty(lectureName) ? null : QLecture.lecture.name.like("%" + lectureName + "%");
	}
	
	// 전체 강의 조회시 년도 null 처리
	private BooleanExpression lectureYearEq(Integer year) {
		return year == null ? null : QLecture.lecture.year.eq(year);
	}
	
	// 전체 강의 조회시 학기 null 처리
	private BooleanExpression lectureSemesterEq(Integer semester) {
		return semester == null ? null : QLecture.lecture.semester.eq(semester);
	}
	
	// 검색할때 특정 강의 제외하기 위한 조건 처리
	private BooleanExpression lectureIdNotEq(Long lectureId) {
		return lectureId == null ? null : QLecture.lecture.id.ne(lectureId);
	}
	
	// 강의 시간표 리스트(이번학기) 가져오기 + 페이징
	@Override
	public Page<LectureScheduleDto> getLectureScheduleList(LectureSearchDto lectureSearchDto, Pageable pageable) {
		QLecture lecture = QLecture.lecture;
		QDepartment department = QDepartment.department;
		QUser user = QUser.user;
		QLectureRoom lectureRoom = QLectureRoom.lectureRoom;
		QLectureCode lectureCode = QLectureCode.lectureCode; 
		
		List<LectureScheduleDto> content = queryFactory
				.select(
					new QLectureScheduleDto(
							department.name, 
							lecture.id, 
							lecture.type, 
							lecture.name, 
							user.name, 
							lecture.credit,
							lecture.day, 
							lecture.startTime, 
							lecture.endTime, 
							lectureRoom.id, 
							lecture.numOfStudent, 
							lecture.capacity,
							lectureCode.detail
							)
						)
				.from(lecture)
				.join(lecture.department, department)
				.join(lecture.lectureRoom, lectureRoom)
				.join(lecture.lectureCode, lectureCode)
				.join(user).on(lecture.professor.id.eq(user.id))
				.where(lectureTypeEq(lectureSearchDto.getType()))
				.where(departmentIdEq(lectureSearchDto.getDepartmentId()))
				.where(lectureNameLike(lectureSearchDto.getLectureName()))
				.where(lectureYearEq(SemesterUtil.CURRENT_YEAR))
				.where(lectureSemesterEq(SemesterUtil.CURRENT_SEMESTER))
				.orderBy(lecture.id.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
				
		long total = queryFactory
				.select(Wildcard.count)
				.from(lecture)
				.join(lecture.department, department)
				.join(lecture.lectureRoom, lectureRoom)
				.join(user).on(lecture.professor.id.eq(user.id))
				.where(lectureTypeEq(lectureSearchDto.getType()))
				.where(departmentIdEq(lectureSearchDto.getDepartmentId()))
				.where(lectureNameLike(lectureSearchDto.getLectureName()))
				.where(lectureYearEq(SemesterUtil.CURRENT_YEAR))
				.where(lectureSemesterEq(SemesterUtil.CURRENT_SEMESTER))
				.fetchOne();
				
		return new PageImpl<>(content, pageable, total);
	}
	
	// 수강신청 가능 과목 가져오기 + 페이징
	@Override
	public Page<LectureScheduleDto> getRegistrationAbleLectureList(Long departmentId, Pageable pageable) {
		QLecture lecture = QLecture.lecture;
		QDepartment department = QDepartment.department;
		QUser user = QUser.user;
		QLectureRoom lectureRoom = QLectureRoom.lectureRoom;
		QLectureCode lectureCode = QLectureCode.lectureCode;
		
		List<LectureScheduleDto> content = queryFactory
				.select(
					new QLectureScheduleDto(
							department.name, 
							lecture.id, 
							lecture.type, 
							lecture.name, 
							user.name, 
							lecture.credit, 
							lecture.day, 
							lecture.startTime, 
							lecture.endTime, 
							lectureRoom.id, 
							lecture.numOfStudent, 
							lecture.capacity,
							lectureCode.detail
							)
						)
				.from(lecture)
				.join(lecture.department, department)
				.join(lecture.lectureRoom, lectureRoom)
				.join(lecture.lectureCode, lectureCode)
				.join(user).on(lecture.professor.id.eq(user.id))
				.where(departmentIdEq(departmentId)
						.or(lecture.type.eq("교양")))
				.where(lectureYearEq(SemesterUtil.CURRENT_YEAR))
				.where(lectureSemesterEq(SemesterUtil.CURRENT_SEMESTER))
				.orderBy(lecture.type.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory
				.select(Wildcard.count)
				.from(lecture)
				.join(lecture.department, department)
				.join(lecture.lectureRoom, lectureRoom)
				.join(user).on(lecture.professor.id.eq(user.id))
				.where(departmentIdEq(departmentId)
						.or(lecture.type.eq("교양")))
				.where(lectureYearEq(SemesterUtil.CURRENT_YEAR))
				.where(lectureSemesterEq(SemesterUtil.CURRENT_SEMESTER))
				.fetchOne();
		
		
		return new PageImpl<>(content, pageable, total);
	}
	
	// 현재 학기 강의 목록 출력(교수)
	@Override
	public List<ProfessorLectureDto> getProfessorLectureListOfCurrentSemester(Long professorId) {
		QLecture lecture = QLecture.lecture;
		QLectureRoom lectureRoom = QLectureRoom.lectureRoom;
		QLectureCode lectureCode = QLectureCode.lectureCode;
		
		List<ProfessorLectureDto> content = queryFactory
				.select(
						new QProfessorLectureDto(
								lecture.id,
								lectureCode.detail,
								lecture.type,
								lecture.name,
								lecture.credit,
								lecture.day,
								lecture.startTime,
								lecture.endTime,
								lectureRoom.id,
								lecture.numOfStudent,
								lecture.capacity
								)
						)
				.from(lecture)
				.join(lecture.lectureRoom, lectureRoom)
				.join(lecture.lectureCode, lectureCode)
				.where(lecture.professor.id.eq(professorId))
				.where(lectureYearEq(SemesterUtil.CURRENT_YEAR))
				.where(lectureSemesterEq(SemesterUtil.CURRENT_SEMESTER))
				.fetch();
				
		return content;
	}
	
	// 강의가 존재하는 년도와 학기 출력(교수)
	@Override
	public List<ProfessorLectureSearchDto> getProfessorLectureGroupByYearAndSemester(Long professorId) {
		QLecture lecture = QLecture.lecture;
		
		List<ProfessorLectureSearchDto> content = queryFactory
				.select(
						new QProfessorLectureSearchDto(
								lecture.year,
								lecture.semester
								)
						)
				.from(lecture)
				.where(lecture.professor.id.eq(professorId))
				.groupBy(lecture.year, lecture.semester)
				.orderBy(lecture.year.asc())
				.fetch();
				
		return content;
	}
	
	// 선택한 학기 강의 목록 조회(교수)
	@Override
	public List<ProfessorLectureDto> getProfessorLectureList(Long professorId,
			ProfessorLectureSearchDto professorLectureSearchDto) {
		QLecture lecture = QLecture.lecture;
		QLectureRoom lectureRoom = QLectureRoom.lectureRoom;
		QLectureCode lectureCode = QLectureCode.lectureCode;
		
		List<ProfessorLectureDto> content = queryFactory
				.select(
						new QProfessorLectureDto(
								lecture.id,
								lectureCode.detail,
								lecture.type,
								lecture.name,
								lecture.credit,
								lecture.year,
								lecture.semester,
								lecture.day,
								lecture.startTime,
								lecture.endTime,
								lectureRoom.id,
								lecture.numOfStudent,
								lecture.capacity
								)
						)
				.from(lecture)
				.join(lecture.lectureRoom, lectureRoom)
				.join(lecture.lectureCode, lectureCode)
				.where(lecture.professor.id.eq(professorId))
				.where(lectureYearEq(professorLectureSearchDto.getYear()))
				.where(lectureSemesterEq(professorLectureSearchDto.getSemester()))
				.fetch();
		
		return content;
	}

	@Override
	public Page<LectureFormDto> getLectureListOfMgmtPage(LectureSearchDto lectureSearchDto, Pageable pageable) {
		QLecture lecture = QLecture.lecture;
		
		List<LectureFormDto> content = queryFactory
				.select(
					new QLectureFormDto(
							lecture.id, 
							lecture.name, 
							lecture.lectureCode.id, 
							lecture.department.id, 
							lecture.professor.id, 
							lecture.lectureRoom.id, 
							lecture.type, 
							lecture.credit, 
							lecture.year, 
							lecture.semester, 
							lecture.day, 
							lecture.startTime, 
							lecture.endTime, 
							lecture.numOfStudent, 
							lecture.capacity
							)
						)
				.from(lecture)
				.where(lectureTypeEq(lectureSearchDto.getType()))
				.where(departmentIdEq(lectureSearchDto.getDepartmentId()))
				.where(lectureNameLike(lectureSearchDto.getLectureName()))
				.orderBy(lecture.id.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		Long total = queryFactory
				.select(Wildcard.count)
				.from(lecture)
				.where(lectureTypeEq(lectureSearchDto.getType()))
				.where(departmentIdEq(lectureSearchDto.getDepartmentId()))
				.where(lectureNameLike(lectureSearchDto.getLectureName()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}
	
	
}
