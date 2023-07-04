package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.dto.LectureRegistrationId;
import com.university.entity.LectureRegistration;

public interface LectureRegistrationRepository extends JpaRepository<LectureRegistration, LectureRegistrationId>{
}
