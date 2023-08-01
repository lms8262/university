package com.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom{
	
	List<Student> findByDepartment_Id(Long departmentId); 
}
