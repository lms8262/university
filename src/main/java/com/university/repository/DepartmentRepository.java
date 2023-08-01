package com.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepositoryCustom {
	
	Department findByName(String name);
	
	Department findByNameAndIdNot(String name, Long id);
	
	List<Department> findByCollege_Id(Long collegeId);
}
