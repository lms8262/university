package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.LectureRegistration;
import com.university.entity.LectureRegistrationId;

public interface LectureRegistrationRepository
		extends JpaRepository<LectureRegistration, LectureRegistrationId>, LectureRegistrationRepositoryCustom {
}
