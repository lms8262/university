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

import com.university.config.CustomUser;
import com.university.constant.Role;
import com.university.dto.ProfessorFormDto;
import com.university.dto.StaffFormDto;
import com.university.dto.StaffInfoDto;
import com.university.dto.StudentFormDto;
import com.university.dto.UserInfoUpdateDto;
import com.university.entity.Department;
import com.university.entity.Professor;
import com.university.entity.Staff;
import com.university.entity.Student;
import com.university.entity.User;
import com.university.repository.DepartmentRepository;
import com.university.repository.ProfessorRepository;
import com.university.repository.StaffRepository;
import com.university.repository.StudentRepository;
import com.university.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // 쿼리문 수행시 에러가 발생하면 변경된 데이터를 이전상태로 콜백시켜줌
@RequiredArgsConstructor // @Autowired를 사용하지 않고 의존성 주입을 시켜준다
public class UserService implements UserDetailsService {

	private final ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final StaffRepository staffRepository;
	private final ProfessorRepository professorRepository;
	private final StudentRepository studentRepository;
	private final DepartmentRepository departmentRepository;

	// 교직원 회원가입 insert
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

	// 교수 회원가입 insert
	@Transactional
	public Long saveUser(ProfessorFormDto professorFormDto) {
		// dto -> entity 변환 후 professor 테이블에 저장
		Professor professor = modelMapper.map(professorFormDto, Professor.class);
		// dto(사용자가 입력한 값)에 있는 email, tel 중복 검사
		validateDuplicateMember(professorFormDto.getEmail(), professorFormDto.getTel());

		Long departmentId = professorFormDto.getDepartmentId();
		Department department = departmentRepository.findById(departmentId).orElseThrow(EntityNotFoundException::new);
		professor.setDepartment(department);
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

	// 학생 회원가입 insert
	@Transactional
	public Long saveUser(StudentFormDto studentFormDto) {
		// dto -> entity 변환 후 professor 테이블에 저장
		Student student = modelMapper.map(studentFormDto, Student.class);

		// dto(사용자가 입력한 값)에 있는 email, tel 중복 검사
		validateDuplicateMember(studentFormDto.getEmail(), studentFormDto.getTel());

		Long departmentId = studentFormDto.getDepartmentId();
		Department department = departmentRepository.findById(departmentId).orElseThrow(EntityNotFoundException::new);
		student.setDepartment(department);
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

		if (userEmailCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 이메일입니다.");
		}

		if (userTelCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 전화번호입니다.");
		}

	}
	
	// 교직원 정보 가져오기
	public StaffInfoDto loadStaffInfo(Long id) {
		User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		Staff staff = staffRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		StaffInfoDto staffInfoDto = new StaffInfoDto(user, staff);
		return staffInfoDto;
	}
	
	// 수정용 유저 정보 가져오기
	public UserInfoUpdateDto loadUserInfo(Long id) {
		User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		UserInfoUpdateDto userInfoUpdateDto = modelMapper.map(user, UserInfoUpdateDto.class);
		return userInfoUpdateDto;
	}
	
	// 유저 정보 수정
	@Transactional
	public void updateUserInfo(Long id, UserInfoUpdateDto userInfoUpdateDto) {
		// 자신을 제외한 다른 유저가 사용중인 이메일, 전화번호인지 검사
		User userEmailCheck = userRepository.findByEmailAndIdNot(userInfoUpdateDto.getEmail(), id);
		User userTelCheck = userRepository.findByTelAndIdNot(userInfoUpdateDto.getTel(), id);
		
		if (userEmailCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 이메일입니다.");
		}

		if (userTelCheck != null) {
			throw new IllegalStateException("다른 회원이 사용중인 전화번호입니다.");
		}
		
		User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
		user.updateUserInfo(userInfoUpdateDto.getAddress(), userInfoUpdateDto.getEmail(), userInfoUpdateDto.getTel());
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 사용자가 입력한 id가 DB에 있는지 쿼리문을 사용
		Optional<User> searchUser = userRepository.findById(Long.parseLong(username));

		if (!searchUser.isPresent()) { // 사용자가 없다면
			throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
		}

		User user = searchUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())); // 권한 추가

		// 사용자가 있다면 DB에서 가져온 값으로 userDetails 객체를 만들어서 반환
		return new CustomUser(user, authorities);
	}

}
