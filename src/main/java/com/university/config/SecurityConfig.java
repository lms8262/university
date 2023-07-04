package com.university.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // bean 객체를 싱글톤으로 공유할 수 있게 해준다
@EnableWebSecurity // spring security filterChain이 자동으로 포함되게 한다
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 로그인에 대한 설정
		http.authorizeHttpRequests(authorize -> authorize // 페이지 접근권한에 관한 설정
				// 모든 사용자가 로그인(인증) 없이 접근할 수 있도록 설정
				.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
				.requestMatchers("/", "/user/**").permitAll()
				.requestMatchers("/favicon.ico", "/error").permitAll()
				// 'staff'으로 시작하는 경로는 관리자만 접근가능하도록 설정
				.requestMatchers("/staff/**").hasRole("STAFF")
				.requestMatchers("/professor/**").hasRole("PROFESSOR")
				// 그 외의 페이지는 모두 로그인(인증을 받아야 한다)
				.anyRequest().authenticated()).formLogin(formLogin -> formLogin // 2. 로그인에 관련된 설정
						.loginPage("/user/login") // 로그인 페이지 URL 설정
						.defaultSuccessUrl("/main") // 로그인 성공시 이동할 페이지
						.usernameParameter("id") // 로그인 시 id로 사용할 파라메터 이름
						.failureUrl("/user/login/error")) // 로그인 실패시 이동할 URL
				.logout(logout -> logout // 3. 로그아웃에 관련된 설정
						.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃시 이동할 URL
						.logoutSuccessUrl("/user/login")) // 로그아웃 성공시 이동할 URL
				.exceptionHandling(handling -> handling // 4. 인증되지 않은 사용자가 리소스에 접근했을때 설정(ex. 로그인 안했는데 cart페이지에 접근...)
						.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
				.rememberMe(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
