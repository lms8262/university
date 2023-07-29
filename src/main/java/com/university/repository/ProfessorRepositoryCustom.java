package com.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.university.dto.ProfessorInfoDto;
import com.university.dto.UserSearchDto;

public interface ProfessorRepositoryCustom {
	Page<ProfessorInfoDto> getProfessorInfoList(UserSearchDto userSearchDto, Pageable pageable);
}
