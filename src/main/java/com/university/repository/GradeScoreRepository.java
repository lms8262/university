package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.GradeScore;

public interface GradeScoreRepository extends JpaRepository<GradeScore, String>{
}
