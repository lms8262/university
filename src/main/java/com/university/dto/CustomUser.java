package com.university.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

// 로그인한 사용자 정보 들어있는 객체 커스터마이징
@Getter
public class CustomUser extends User {
	private final String name;
	
	public CustomUser(com.university.entity.User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getId().toString(), user.getPassword(), authorities);
		this.name = user.getName();
	}

}
