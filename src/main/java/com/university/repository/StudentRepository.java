package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.university.entity.Department;
import com.university.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom{
	
	@Modifying(clearAutomatically = true)
	@Query("update Student s set s.department = null where s.department = :department")
	void setDepartmentNull(@Param("department") Department department);
}
