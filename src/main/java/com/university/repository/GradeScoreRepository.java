package com.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.university.entity.GradeScore;

public interface GradeScoreRepository extends JpaRepository<GradeScore, String>{
	@Query("select grade from GradeScore")
	List<String> getAllGradeId();
}
