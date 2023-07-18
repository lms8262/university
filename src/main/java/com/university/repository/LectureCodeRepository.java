package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.LectureCode;

public interface LectureCodeRepository extends JpaRepository<LectureCode, Long>{
}
