package com.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class LectureRoom {
	
	@Id
	@Column(columnDefinition = "char(4)")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "college_id", nullable = false)
	private College college;
}
