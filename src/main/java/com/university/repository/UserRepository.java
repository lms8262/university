package com.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.university.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
	
	User findByTel(String tel);
	
	User findByEmailAndIdNot(String email, Long id);
	
	User findByTelAndIdNot(String tel, Long id);
}
