package com.university.entity;

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
@TableGenerator(name = "department_id_generator", table = "id_sequences", initialValue = 101, allocationSize = 1)
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "department_id_generator")
	private Long id;

	@Column(nullable = false, unique = true, length = 15)
	private String name;

	@ManyToOne
	@JoinColumn(name = "college_id", nullable = false)
	private College college;
}
