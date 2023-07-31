package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.university.entity.College;
import com.university.entity.LectureRoom;

public interface LectureRoomRepository extends JpaRepository<LectureRoom, String>{
	
	@Modifying(clearAutomatically = true)
	@Query("update LectureRoom l set l.college = null where l.college = :college")
	void setLectureRoomNull(@Param("college") College college);
}
