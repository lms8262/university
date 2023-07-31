package com.university.repository;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.university.dto.DepartmentFormDto;
import com.university.dto.QDepartmentFormDto;
import com.university.entity.QCollege;
import com.university.entity.QDepartment;

import jakarta.persistence.EntityManager;

public class DepartmentRepositoryCustomImpl implements DepartmentRepositoryCustom {
	
	private JPAQueryFactory queryFactory;
	
	public DepartmentRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<DepartmentFormDto> getDepartmentList() {
		QDepartment department = QDepartment.department;
		QCollege college = QCollege.college;
		
		List<DepartmentFormDto> content = queryFactory
				.select(
						new QDepartmentFormDto(
								department.id, 
								department.name, 
								college.id, 
								college.name
								)
						)
				.from(department)
				.join(department.college, college)
				.orderBy(department.id.asc())
				.fetch();
		
		return content;
	}

	@Override
	public DepartmentFormDto findDepartmentInfoById(Long departmentId) {
		QDepartment department = QDepartment.department;
		QCollege college = QCollege.college;
		
		DepartmentFormDto content = queryFactory
				.select(
						new QDepartmentFormDto(
								department.id, 
								department.name, 
								college.id, 
								college.name
								)
						)
				.from(department)
				.join(department.college, college)
				.where(department.id.eq(departmentId))
				.fetchOne();
		
		return content;
	}
	
}
