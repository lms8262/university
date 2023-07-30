package com.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "college")
@Getter
@Setter
@ToString
public class College {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 15)
	private String name;
	
	@Column(columnDefinition = "char(1) unique", nullable = false)
	private String collegeCode;
	
	public static College createCollege(String name, String collegeCode) {
		College college = new College();
		college.setName(name);
		college.setCollegeCode(collegeCode);
		
		return college;
	}
}
