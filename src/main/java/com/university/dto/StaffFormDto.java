package com.university.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StaffFormDto {
	
	@NotEmpty(message = "비밀번호는 필수입력 값입니다.")
	@Length(min = 4, max = 16, message = "비밀번호는 4 ~ 16자 사이로 입력해주세요.")
	private String password;
	
	@NotEmpty(message = "이름은 필수입력 값입니다.")
	@Length(min = 2, max = 10, message = "이름은 2 ~ 10자 사이로 입력해주세요.")
	private String name;
	
	private String gender;
	
	@NotNull(message = "생년월일은 필수입력 값입니다.")
	private LocalDate birthDate;
	
	@NotEmpty(message = "주소는 필수입력 값입니다.")
	private String address;
	
	@NotEmpty(message = "이메일은 필수입력 값입니다.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	@Length(max = 50, message = "이메일 길이는 최대 50자입니다.")
	private String email;
	
	@NotEmpty(message = "전화번호는 필수입력 값입니다.")
	@Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식으로 입력해주세요.")
	private String tel;
	
}
