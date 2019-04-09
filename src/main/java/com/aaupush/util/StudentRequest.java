package com.aaupush.util;

import com.aaupush.com.Student;

public class StudentRequest {
	String operation;
	Student payload;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Student getPayload() {
		return payload;
	}

	public void setPayload(Student payload) {
		this.payload = payload;
	}

}
