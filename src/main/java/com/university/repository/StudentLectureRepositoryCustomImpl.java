package com.university.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.university.dto.QStudentInfoOfLectureDto;
import com.university.dto.StudentInfoOfLectureDto;
import com.university.entity.QDepartment;
import com.university.entity.QStudent;
import com.university.entity.QStudentLecture;
import com.university.entity.QUser;

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
	
	
}
