package com.university.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.entity.User;
import com.university.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional // 쿼리문 수행시 에러가 발생하면 변경된 데이터를 이전상태로 콜백시켜줌
@RequiredArgsConstructor // @Autowired를 사용하지 않고 의존성 주입을 시켜준다
public class UserService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 사용자가 입력한 id가 DB에 있는지 쿼리문을 사용
		Optional<User> User = userRepository.findById(Long.parseLong(username));
		
		if(!User.isPresent()) { // 사용자가 없다면
			throw new UsernameNotFoundException(username);
		}
		
		// 사용자가 있다면 DB에서 가져온 값으로 userDetails 객체를 만들어서 반환
		return org.springframework.security.core.userdetails.User.builder()
				.username(User.get().getId().toString())
				.password(User.get().getPassword())
				.roles(User.get().getRole().toString())
				.build();
	}
	
	
}
