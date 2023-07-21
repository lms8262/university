package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.StudentLecture;

public interface StudentLectureRepository extends JpaRepository<StudentLecture, Long>, StudentLectureRepositoryCustom {
	StudentLecture findByStudentIdAndLectureId(Long studentId, Long lectureId);
}
