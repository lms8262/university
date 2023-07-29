package com.university.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.university.dto.QStudentInfoDto;
import com.university.dto.StudentInfoDto;
import com.university.dto.UserSearchDto;
import com.university.entity.QDepartment;
import com.university.entity.QStudent;
import com.university.entity.QUser;

import jakarta.persistence.EntityManager;

public class StudentRepositoryCustomImpl implements StudentRepositoryCustom{
	
	private JPAQueryFactory queryFactory;
	
	public StudentRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	// 학과 전체일때 null 처리
	private BooleanExpression departmentIdEq(Long departmentId) {
		return departmentId == 0 ? null : QStudent.student.department.id.eq(departmentId);
	}
	
	// 학번이 빈 값일때 null 처리
	private BooleanExpression studentIdEq(String userId) {
		return userId == "" ? null : QStudent.student.id.eq(Long.parseLong(userId));
	}
	
	@Override
	public Page<StudentInfoDto> getStudentInfoList(UserSearchDto userSearchDto, Pageable pageable) {
		QStudent student = QStudent.student;
		QDepartment department = QDepartment.department;
		QUser user = QUser.user;
		
		List<StudentInfoDto> content = queryFactory
				.select(
					new QStudentInfoDto(
							student.id, 
							student.entranceDate, 
							user.name, 
							user.birthDate, 
							user.gender, 
							user.tel, 
							user.email, 
							user.address, 
							department.name, 
							student.grade, 
							student.semester
							)
						)
				.from(student)
				.join(student.department, department)
				.join(user).on(student.id.eq(user.id))
				.where(departmentIdEq(userSearchDto.getDepartmentId()))
				.where(studentIdEq(userSearchDto.getUserId()))
				.orderBy(student.id.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory
				.select(Wildcard.count)
				.from(student)
				.join(student.department, department)
				.join(user).on(student.id.eq(user.id))
				.where(departmentIdEq(userSearchDto.getDepartmentId()))
				.where(studentIdEq(userSearchDto.getUserId()))
				.fetchOne();
				
		return new PageImpl<>(content, pageable, total);
	}
	
}
