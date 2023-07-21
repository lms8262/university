package com.university.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.university.dto.QStudentInfoOfLectureDto;
import com.university.dto.StudentInfoOfLectureDto;
import com.university.entity.Lecture;
import com.university.entity.QDepartment;
import com.university.entity.QLecture;
import com.university.entity.QStudent;
import com.university.entity.QStudentLecture;
import com.university.entity.QUser;
import com.university.entity.StudentLecture;

import jakarta.persistence.EntityManager;

public class StudentLectureRepositoryCustomImpl implements StudentLectureRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public StudentLectureRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<StudentInfoOfLectureDto> getStudentInfoAndGradeList(Long professorId, Long lectureId) {
		QStudentLecture studentLecture = QStudentLecture.studentLecture;
		QStudent student = QStudent.student;
		QDepartment department = QDepartment.department;
		QUser user = QUser.user;
		
		List<StudentInfoOfLectureDto> content = queryFactory
				.select(
						new QStudentInfoOfLectureDto(
								student.id, 
								user.name, 
								department.name, 
								studentLecture.gradeScore.grade
								)
						)
				.from(studentLecture)
				.join(studentLecture.student, student)
				.join(student.department, department)
				.join(user).on(studentLecture.student.id.eq(user.id))
				.where(studentLecture.lecture.id.eq(lectureId))
				.where(studentLecture.lecture.professor.id.eq(professorId))
				.orderBy()
				.fetch();

		return content;
	}

	@Override
	public StudentLecture findbyLectureCodeIdAndStudentId(Long lectureCodeId, Long studentId) {
		QStudentLecture studentLecture = QStudentLecture.studentLecture;
		QLecture lecture = QLecture.lecture;
		
		StudentLecture content = queryFactory
				.select(studentLecture)
				.from(studentLecture)
				.join(studentLecture.lecture, lecture)
				.where(studentLecture.student.id.eq(studentId))
				.where(lecture.lectureCode.id.eq(lectureCodeId))
				.fetchOne();
		
		return content;
	}

	@Override
	public Integer getTotalCreditByStudentIdAndYearAndSemester(Long studentId, Integer year, Integer semester) {
		QStudentLecture studentLecture = QStudentLecture.studentLecture;
		QLecture lecture = QLecture.lecture;
		
		Integer totalCredit = queryFactory
				.select(lecture.credit.sum().coalesce(0).as("totalCredit"))
				.from(studentLecture)
				.join(studentLecture.lecture, lecture)
				.where(studentLecture.student.id.eq(studentId))
				.where(lecture.year.eq(year))
				.where(lecture.semester.eq(semester))
				.fetchOne();
		
		return totalCredit;
	}

	@Override
	public List<Lecture> getCurrentRegistrationLectureListOfStudent(Long studentId, Integer year, Integer semester) {
		QStudentLecture studentLecture = QStudentLecture.studentLecture;
		QLecture lecture = QLecture.lecture;
		
		List<Lecture> lectureList = queryFactory
				.select(lecture)
				.from(studentLecture)
				.join(studentLecture.lecture, lecture)
				.where(studentLecture.student.id.eq(studentId))
				.where(lecture.year.eq(year))
				.where(lecture.semester.eq(semester))
				.fetch();
		
		return lectureList;
	}

	@Override
	public StudentInfoOfLectureDto getStudentInfoForModifyGrade(Long lectureId, Long studentId) {
		QStudentLecture studentLecture = QStudentLecture.studentLecture;
		QStudent student = QStudent.student;
		QDepartment department = QDepartment.department;
		QUser user = QUser.user;
		
		StudentInfoOfLectureDto content = queryFactory
				.select(
						new QStudentInfoOfLectureDto(
								student.id, 
								user.name, 
								department.name, 
								studentLecture.gradeScore.grade
								)
						)
				.from(studentLecture)
				.join(studentLecture.student, student)
				.join(student.department, department)
				.join(user).on(studentLecture.student.id.eq(user.id))
				.where(studentLecture.lecture.id.eq(lectureId))
				.where(studentLecture.student.id.eq(studentId))
				.fetchOne();
		return content;
	}
	
}
