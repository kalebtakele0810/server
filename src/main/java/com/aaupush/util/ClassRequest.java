package com.aaupush.util;

import com.aaupush.com.Course;

public class ClassRequest {
	String operation;
	Integer courseId;
	Course payload;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Course getPayload() {
		return payload;
	}

	public void setPayload(Course payload) {
		this.payload = payload;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}




}