package com.university.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.university.constant.Role;
import com.university.dto.CustomUser;
import com.university.dto.ProfessorFormDto;
import com.university.dto.StaffFormDto;
import com.university.dto.StudentFormDto;
import com.university.entity.Department;
import com.university.entity.Professor;
import com.university.entity.Staff;
import com.university.entity.Student;
import com.university.entity.User;
import com.university.exption.NotFoundException;
import com.university.repository.DepartmentRepository;
import com.university.repository.ProfessorRepository;
import com.university.repository.StaffRepository;
import com.university.repository.StudentRepository;
import com.university.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 쿼리문 수행시 에러가 발생하면 변경된 데이터를 이전상태로 콜백시켜줌
@RequiredArgsConstructor // @Autowired를 사용하지 않고 의존성 주입을 시켜준다
public class UserService implements UserDetailsService{
	
	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final StaffRepository staffRepository;
	private final ProfessorRepository professorRepository;
	private final StudentRepository studentRepository;
	private final DepartmentRepository departmentRepository; 
	
	// dto -> entity 변환 후 db에 저장(staff)
	@Transactional
	public Long saveUser(StaffFormDto staffFormDto) {
		// dto -> entity 변환 후 staff 테이블에 저장
		Staff staff = modelMapper.map(staffFormDto, Staff.class);
		// dto(사용자가 입력한 값)에 있는 email, tel 중복 검사
		validateDuplicateMember(staffFormDto.getEmail(), staffFormDto.getTel());
		staffRepository.save(staff);
		
		// dto -> entity 변환 후 user 테이블에 저장
		User user = modelMapper.map(staffFormDto, User.class);
		String password = passwordEncoder.encode(staffFormDto.getPassword());
		user.setId(staff.getId());
		user.setPassword(password);
		user.setRole(Role.STAFF);
		userRepository.save(user);
		
		return staff.getId();
	}
	
	// dto -> entity 변환 후 db에 저장(professor)
	@Transactional
	public Long saveUser(ProfessorFormDto professorFormDto) throws NotFoundException {
		// dto -> entity 변환 후 professor 테이블에 저장
		Professor professor = modelMapper.map(professorFormDto, Professor.class);
		// dto(사용자가 입력한 값)에 있는 email, tel 중복 검사
		validateDuplicateMember(professorFormDto.getEmail(), professorFormDto.getTel());
		
		Long departmentId = professorFormDto.getDepartmentId();
		Optional<Department> department = departmentRepository.findById(departmentId);
		// db에 없는 학과인 경우
		if(!department.isPresent()) {
			throw new NotFoundException("존재하지 않는 학과입니다.");
		}
		professor.setDepartment(department.get());
		professorRepository.save(professor);
		
		// dto -> entity 변환 후 user 테이블에 저장
		User user = modelMapper.map(professorFormDto, User.class);
		String password = passwordEncoder.encode(professorFormDto.getPassword());
		user.setId(professor.getId());
		user.setPassword(password);
		user.setRole(Role.PROFESSOR);
		userRepository.save(user);
		
		return professor.getId();
	}
	
	// dto -> entity 변환 후 db에 저장(student)
	@Transactional
	public Long saveUser(StudentFormDto studentFormDto) {
		// dto -> entity 변환 후 professor 테이블에 저장
		Student student = modelMapper.map(studentFormDto, Student.class);
		
		// dto(사용자가 입력한 값)에 있는 email, tel 중복 검사
		validateDuplicateMember(studentFormDto.getEmail(), studentFormDto.getTel());
		
		Long departmentId = studentFormDto.getDepartmentId();
		Optional<Department> department = departmentRepository.findById(departmentId);
		// db에 없는 학과인 경우
		if(!department.isPresent()) {
			throw new NotFoundException("존재하지 않는 학과입니다.");
		}
		student.setDepartment(department.get());
		studentRepository.save(student);
		
		// dto -> entity 변환 후 user 테이블에 저장
		User user = modelMapper.map(studentFormDto, User.class);
		String password = passwordEncoder.encode(studentFormDto.getPassword());
		user.setId(student.getId());
		user.setPassword(password);
		user.setRole(Role.STUDENT);
		userRepository.save(user);
		
		return student.getId();
	}
	
	// 중복 이메일, 전화번호 검사
	private void validateDuplicateMember(String email, String tel) {
		User userEmailCheck = userRepository.findByEmail(email);
		User userTelCheck = userRepository.findByTel(tel);
		
		if(userEmailCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 이메일입니다.");
		}
		
		if(userTelCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 전화번호입니다.");
		}
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 사용자가 입력한 id가 DB에 있는지 쿼리문을 사용
		Optional<User> searchUser = userRepository.findById(Long.parseLong(username));
		
		if(!searchUser.isPresent()) { // 사용자가 없다면
			throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
		}
		
		User user = searchUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())); // 권한 추가
		
		// 사용자가 있다면 DB에서 가져온 값으로 userDetails 객체를 만들어서 반환
		return new CustomUser(user, authorities);
	}
	
	
}
