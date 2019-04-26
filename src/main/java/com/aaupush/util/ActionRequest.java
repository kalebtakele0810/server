package com.aaupush.util;

import com.aaupush.com.User;
import com.aaupush.com.Staff;

public class ActionRequest {
	String operation;
	Integer postId;
	Integer userId;
	Staff staffpayload;
	User userpayload;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public User getUserPayload() {
		return userpayload;
	}

	public void setUserPayload(User userpayload) { this.userpayload = userpayload; }

	public Staff getStaffPayload() {
		return staffpayload;
	}

	public void setStaffPayload(Staff staffpayload) { this.staffpayload = staffpayload; }

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}



}