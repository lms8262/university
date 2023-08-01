package com.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, ProfessorRepositoryCustom {
	
	List<Professor> findByDepartment_Id(Long departmentId);
}
