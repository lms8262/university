package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.College;

public interface CollegeRepository extends JpaRepository<College, Long> {
}
