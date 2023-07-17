package com.university.exception;

public class TimeOverlapException extends RuntimeException {
	
	// 기존에 수강신청한 시간과 겹치면 발생
	public TimeOverlapException(String message) {
		super(message);
	}
}
