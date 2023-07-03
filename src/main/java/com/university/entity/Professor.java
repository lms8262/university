package com.university.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@TableGenerator(name = "professor_id_generator", table = "id_sequences", initialValue = 230001, allocationSize = 1)
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "professor_id_generator")
	private Long id;
	
	@Column(nullable = false, length = 10)
	private String name;
	
	@Column(columnDefinition = "char(2)", nullable = false)
	private String gender;
	
	@Column(nullable = false)
	private Date birthDate;
	
	@Column(nullable = false, length = 100)
	private String address;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@Column(nullable = false, length = 15)
	private String tel;
	
	@Column(nullable = false)
	private Date hireDate;
	
	@ManyToOne
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
}
