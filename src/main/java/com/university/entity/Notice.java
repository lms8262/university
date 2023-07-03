package com.university.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Notice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String title;
	
	@Column(columnDefinition = "longtext", nullable = false)
	private String content;
	
	@Column(nullable = false)
	@ColumnDefault("0")
	private Integer views;
	
	@Column(nullable = false)
	private LocalDateTime createdDate;
}
