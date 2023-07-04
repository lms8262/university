package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long>{
}
