package com.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom {
	List<Lecture> findByYearAndSemesterAndDay(Integer year, Integer semester, String day);
	
	List<Lecture> findByYearAndSemesterAndDayAndIdNot(Integer year, Integer semester, String day, Long lectureId);
}
