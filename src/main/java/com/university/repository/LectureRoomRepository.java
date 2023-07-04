package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.LectureRoom;

public interface LectureRoomRepository extends JpaRepository<LectureRoom, String>{
}
