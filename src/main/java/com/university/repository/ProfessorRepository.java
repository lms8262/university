package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.university.entity.Department;
import com.university.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, ProfessorRepositoryCustom {
	
	@Modifying(clearAutomatically = true)
	@Query("update Professor p set p.department = null where p.department = :department")
	void setDepartmentNull(@Param("department") Department department);
}
