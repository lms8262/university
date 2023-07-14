package com.university.exception;

public class OutOfCapacityException extends RuntimeException{
	
	// 수강신청시 강의 정원이 다 찼을때 발생
	public OutOfCapacityException(String message) {
		super(message);
	}

}
