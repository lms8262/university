package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.College;

public interface CollegeRepository extends JpaRepository<College, Long> {
	College findByName(String name);
	
	College findByCollegeCode(String collegeCode);
	
	College findByNameAndIdNot(String name, Long id);
	
	College findByCollegeCodeAndIdNot(String collegeCode, Long id);
}
