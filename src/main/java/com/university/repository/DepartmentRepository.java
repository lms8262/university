package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.university.entity.College;
import com.university.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
	
	@Modifying(clearAutomatically = true)
	@Query("update Department d set d.college = null where d.college = :college")
	void setCollegeNull(College college);
}
