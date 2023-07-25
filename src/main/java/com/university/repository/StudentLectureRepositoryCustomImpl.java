package com.university.repository;

import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.university.dto.QStudentInfoOfLectureDto;
import com.university.dto.QStudentLectureScoreInfoDto;
import com.university.dto.StudentInfoOfLectureDto;
import com.university.dto.StudentLectureScoreInfoDto;
import com.university.entity.Lecture;
import com.university.entity.QDepartment;
import com.university.entity.QGradeScore;
import com.university.entity.QLecture;
import com.university.entity.QLectureCode;
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

	private BooleanExpression lectureTypeEq(String type) {
		return type.equals("") ? null : QLecture.lecture.type.eq(type); 
	}
	
	private BooleanExpression gradeNe(String grade) {
		return grade.equals("") ? null : QGradeScore.gradeScore.grade.ne(grade);
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
				.orderBy(student.id.asc())
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

	@Override
	public List<StudentLectureScoreInfoDto> getStudentLectureScoreInfoList(Long studentId, Integer year,
			Integer semester, String type) {
		QStudentLecture studentLecture = QStudentLecture.studentLecture;
		QUser user = QUser.user;
		QLecture lecture = QLecture.lecture;
		QLectureCode lectureCode = QLectureCode.lectureCode;
		
		List<StudentLectureScoreInfoDto> content = queryFactory
				.select(
						new QStudentLectureScoreInfoDto(
								lecture.year, 
								lecture.semester, 
								lectureCode.detail, 
								lecture.id, 
								lecture.name, 
								lecture.type, 
								user.name, 
								lecture.credit, 
								studentLecture.gradeScore.grade
								)
						)
				.from(studentLecture)
				.join(studentLecture.lecture, lecture)
				.join(lecture.lectureCode, lectureCode)
				.join(user).on(lecture.professor.id.eq(user.id))
				.where(studentLecture.student.id.eq(studentId))
				.where(lecture.year.eq(year))
				.where(lecture.semester.eq(semester))
				.where(lectureTypeEq(type))
				.fetch();
		return content;
	}

	@Override
	public Integer getTotalCreditOfStudentLecture(Long studentId, Integer year, Integer semester, String type, String grade) {
		QStudentLecture studentLecture = QStudentLecture.studentLecture;
		QLecture lecture = QLecture.lecture;
		
		Integer content = queryFactory
				.select(studentLecture.lecture.credit.sum().coalesce(0))
				.from(studentLecture)
				.join(studentLecture.lecture, lecture)
				.where(studentLecture.student.id.eq(studentId))
				.where(lecture.year.eq(year))
				.where(lecture.semester.eq(semester))
				.where(lectureTypeEq(type))
				.where(gradeNe(grade))
				.fetchOne();
		return content;
	}

	@Override
	public Double getTotalScoreOfStudentLecture(Long studentId, Integer year, Integer semester, String type) {
		QStudentLecture studentLecture = QStudentLecture.studentLecture;
		QLecture lecture = QLecture.lecture;
		QGradeScore gradeScore = QGradeScore.gradeScore;
		
		Double content = queryFactory
				.select(gradeScore.score.multiply(lecture.credit).sum().coalesce(0D))
				.from(studentLecture)
				.join(studentLecture.lecture, lecture)
				.join(studentLecture.gradeScore, gradeScore)
				.where(studentLecture.student.id.eq(studentId))
				.where(lecture.year.eq(year))
				.where(lecture.semester.eq(semester))
				.where(lectureTypeEq(type))
				.fetchOne();
		return content;
	}
	
}
