package com.aaupush.util;

import com.aaupush.com.User;

public class UserRequest {
	String operation;
	User payload;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public User getPayload() {
		return payload;
	}

	public void setPayload(User payload) {
		this.payload = payload;
	}

}
