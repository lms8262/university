package com.university.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.university.dto.LectureScheduleDto;
import com.university.dto.QLectureScheduleDto;
import com.university.entity.Lecture;
import com.university.entity.QDepartment;
import com.university.entity.QLecture;
import com.university.entity.QLectureRegistration;
import com.university.entity.QLectureRoom;
import com.university.entity.QUser;

import jakarta.persistence.EntityManager;

public class LectureRegistrationRepositoryCustomImpl implements LectureRegistrationRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public LectureRegistrationRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	@Override
	public Integer getTotalCreaditByStudentId(Long studentId) {
		QLectureRegistration lectureRegistration = QLectureRegistration.lectureRegistration;
		QLecture lecture = QLecture.lecture;
		
		Integer totalCredit = queryFactory
				.select(lecture.credit.sum().coalesce(0).as("totalCredit"))
				.from(lectureRegistration)
				.join(lectureRegistration.lecture, lecture)
				.where(lectureRegistration.student.id.eq(studentId))
				.fetchOne();
		 
		return totalCredit;
	}

	@Override
	public List<Lecture> getRegistrationLectureListOfStudent(Long studentId) {
		QLectureRegistration lectureRegistration = QLectureRegistration.lectureRegistration;
		QLecture lecture = QLecture.lecture;
		
		List<Lecture> lectureList = queryFactory
				.select(lecture)
				.from(lectureRegistration)
				.join(lectureRegistration.lecture, lecture)
				.where(lectureRegistration.student.id.eq(studentId))
				.fetch();
		
		return lectureList;
	}

	@Override
	public List<LectureScheduleDto> getLectureRegistrationHistory(Long studentId) {
		QLectureRegistration lectureRegistration = QLectureRegistration.lectureRegistration;
		QLecture lecture = QLecture.lecture;
		QDepartment department = QDepartment.department;
		QUser user = QUser.user;
		QLectureRoom lectureRoom = QLectureRoom.lectureRoom;
		
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
								lecture.capacity
								)
						)
				.from(lectureRegistration)
				.join(lectureRegistration.lecture, lecture)
				.join(lecture.department, department)
				.join(lecture.lectureRoom, lectureRoom)
				.join(user).on(lecture.professor.id.eq(user.id))
				.where(lectureRegistration.student.id.eq(studentId))
				.fetch();			
		
		return content;
	}
	
}
