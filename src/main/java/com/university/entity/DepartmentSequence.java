package com.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class DepartmentSequence {
	
	@Id
	@Column(nullable = false)
	private String sequenceName;
	
	@Column
	private Long nextVal;
}
