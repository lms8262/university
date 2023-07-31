package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.university.entity.Department;
import com.university.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom {
	
	@Modifying(clearAutomatically = true)
	@Query("update Lecture l set l.department = null where l.department = :department")
	void setDepartmentNull(@Param("department") Department department);
}
