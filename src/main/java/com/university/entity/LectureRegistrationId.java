package com.university.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
// 복합키 사용시 JpaRepository id 타입 넣어주기 위한 클래스
public class LectureRegistrationId implements Serializable {
	private Long student;
	
	private Long lecture;
}
