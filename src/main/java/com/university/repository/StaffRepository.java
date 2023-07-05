package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long>{
	
	Staff findByEmail(String email);
	
	Staff findByTel(String tel);
}
