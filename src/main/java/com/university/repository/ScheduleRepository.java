package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
}
