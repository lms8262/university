package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long>{
}
