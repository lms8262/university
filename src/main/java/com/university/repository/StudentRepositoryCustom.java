package com.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.university.dto.StudentInfoDto;
import com.university.dto.UserSearchDto;

public interface StudentRepositoryCustom {
	Page<StudentInfoDto> getStudentInfoList(UserSearchDto userSearchDto, Pageable pageable);
}
