package com.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "grade_score")
@Getter
@Setter
@ToString
public class GradeScore {
	
	@Id
	@Column(columnDefinition = "char(2)")
	private String grade;
	
	@Column(nullable = false)
	private Float score;
}
