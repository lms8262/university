package com.university.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.university.entity.Lecture;
import com.university.entity.QLecture;
import com.university.entity.QLectureRegistration;

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
	
	
	
}
