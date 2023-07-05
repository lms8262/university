package com.university.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.constant.Role;
import com.university.dto.ProfessorFormDto;
import com.university.dto.StaffFormDto;
import com.university.dto.StudentFormDto;
import com.university.entity.Professor;
import com.university.entity.Staff;
import com.university.entity.Student;
import com.university.entity.User;
import com.university.repository.ProfessorRepository;
import com.university.repository.StaffRepository;
import com.university.repository.StudentRepository;
import com.university.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional // 쿼리문 수행시 에러가 발생하면 변경된 데이터를 이전상태로 콜백시켜줌
@RequiredArgsConstructor // @Autowired를 사용하지 않고 의존성 주입을 시켜준다
public class UserService implements UserDetailsService{
	
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final StaffRepository staffRepository;
	private final ProfessorRepository professorRepository;
	private final StudentRepository studentRepository;
	
	// dto -> entity 변환 후 db에 저장
	public Long saveUser(StaffFormDto staffFormDto) {
		Staff staff = modelMapper.map(staffFormDto, Staff.class);
		validateDuplicateMember(staff.getEmail(), staff.getTel());
		staffRepository.save(staff);
		
		User user = new User();
		String password = passwordEncoder.encode(staffFormDto.getPassword());
		user.setId(staff.getId());
		user.setPassword(password);
		user.setRole(Role.STAFF);
		userRepository.save(user);
		
		return staff.getId();
	}
	
	public void saveUser(ProfessorFormDto professorFormDto) {
		
	}
	
	public void saveUser(StudentFormDto studentFormDto) {
		
	}
	
	// 중복 이메일, 전화번호 검사
	private void validateDuplicateMember(String email, String tel) {
		Staff staffEmailCheck = staffRepository.findByEmail(email);
		Staff staffTelCheck = staffRepository.findByTel(tel);
		Professor professorEmailCheck = professorRepository.findByEmail(email);
		Professor professorTelCheck = professorRepository.findByTel(tel);
		Student studentEmailCheck = studentRepository.findByEmail(email);
		Student studentTelCheck = studentRepository.findByTel(tel);
		
		if(staffEmailCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 이메일입니다.");
		}
		
		if(staffTelCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 전화번호입니다.");
		}
		
		if(professorEmailCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 이메일입니다.");
		}
		
		if(professorTelCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 전화번호입니다.");
		}
		
		if(studentEmailCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 이메일입니다.");
		}
		
		if(studentTelCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 전화번호입니다.");
		}
	}
	
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
