package com.university.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "lecture_room")
@Getter
@Setter
@ToString
public class LectureRoom {
	
	@Id
	@Column(columnDefinition = "char(4)")
	private String id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "college_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private College college;
	
	public void updateLectureRoom() {
		
	}
}
