package com.university.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.dto.LectureScheduleDto;
import com.university.dto.LectureSearchDto;
import com.university.entity.Student;
import com.university.repository.LectureRepository;
import com.university.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LectureService {
	private final LectureRepository lectureRepository;
	private final StudentRepository studentRepository;
	
	public Page<LectureScheduleDto> getLectureScheduleList(LectureSearchDto lectureSearchDto, Pageable pageable) {
		return lectureRepository.getLectureScheduleList(lectureSearchDto, pageable);
	}
	
	public Page<LectureScheduleDto> getRegistrationAbleLectureList(Long id, Pageable pageable) {
		Student student = studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		Long departmentId = student.getDepartment().getId();
		return lectureRepository.getRegistrationAbleLectureList(departmentId, pageable);
	}
}
