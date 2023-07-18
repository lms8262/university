package com.university.exception;

public class OverlapException extends RuntimeException {
	
	// 수강신청시 중복되는 경우일때 발생(이미 신청한 강의, 같은 강의코드, 시간 겹침)
	public OverlapException(String message) {
		super(message);
	}
}
