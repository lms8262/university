package com.university.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.university.dto.ProfessorInfoDto;
import com.university.dto.QProfessorInfoDto;
import com.university.dto.UserSearchDto;
import com.university.entity.QDepartment;
import com.university.entity.QProfessor;
import com.university.entity.QStudent;
import com.university.entity.QUser;

import jakarta.persistence.EntityManager;

public class ProfessorRepositoryCustomImpl implements ProfessorRepositoryCustom {

	private JPAQueryFactory queryFactory;
	
	public ProfessorRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	// 학과 전체일때 null 처리
	private BooleanExpression departmentIdEq(Long departmentId) {
		return departmentId == 0 ? null : QProfessor.professor.department.id.eq(departmentId);
	}
	
	// 교번이 빈 값일때 null 처리
	private BooleanExpression professorIdEq(String userId) {
		return userId == "" ? null : QProfessor.professor.id.eq(Long.parseLong(userId));
	}
	
	@Override
	public Page<ProfessorInfoDto> getProfessorInfoList(UserSearchDto userSearchDto, Pageable pageable) {
		QProfessor professor = QProfessor.professor;
		QDepartment department = QDepartment.department;
		QUser user = QUser.user;
		
		List<ProfessorInfoDto> content = queryFactory
				.select(
					new QProfessorInfoDto(
							professor.id, 
							professor.hireDate, 
							user.name, 
							user.birthDate, 
							user.gender, 
							user.tel, 
							user.email, 
							user.address, 
							department.name
							)
						)
				.from(professor)
				.join(professor.department, department)
				.join(user).on(professor.id.eq(user.id))
				.where(departmentIdEq(userSearchDto.getDepartmentId()))
				.where(professorIdEq(userSearchDto.getUserId()))
				.orderBy(professor.id.asc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory
				.select(Wildcard.count)
				.from(professor)
				.join(professor.department, department)
				.join(user).on(professor.id.eq(user.id))
				.where(departmentIdEq(userSearchDto.getDepartmentId()))
				.where(professorIdEq(userSearchDto.getUserId()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}
	
}
