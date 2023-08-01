package com.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.university.dto.LectureRoomDto;

public interface LectureRoomRepositoryCustom {
	Page<LectureRoomDto> getLectureRoomList(Pageable pageable);
}
