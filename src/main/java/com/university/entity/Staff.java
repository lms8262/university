package com.university.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "staff")
@Getter
@Setter
@ToString
@TableGenerator(name = "staff_id_generator", table = "id_sequences", initialValue = 2301, allocationSize = 1)
@EntityListeners(AuditingEntityListener.class)
public class Staff {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "staff_id_generator")
	private Long id;
	
	@Column(nullable = false, length = 10)
	private String name;
	
	@Column(columnDefinition = "char(2)", nullable = false)
	private String gender;
	
	@Column(nullable = false)
	private LocalDate birthDate;
	
	@Column(nullable = false, length = 100)
	private String address;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@Column(nullable = false, length = 15)
	private String tel;
	
	@Column(nullable = false)
	@CreatedDate
	private LocalDate hireDate;
	
}
