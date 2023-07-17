package com.university.exception;

public class OutOfCreditException extends RuntimeException {
	
	// 수강 신청시 신청 총 학점이 18학점 초과일때 발생
	public OutOfCreditException(String message) {
		super(message);
	}
}
