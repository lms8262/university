package com.university.entity;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "student")
@Getter
@Setter
@ToString
@TableGenerator(name = "student_id_generator", table = "id_sequences", initialValue = 23000001, allocationSize = 1)
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "student_id_generator")
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
	private LocalDate entranceDate;

	@Column(nullable = false)
	@ColumnDefault("1")
	private Integer grade;

	@Column(nullable = false)
	@ColumnDefault("1")
	private Integer semester;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
}
