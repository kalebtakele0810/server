package com.aaupush.util;

import com.aaupush.com.Forum;

public class ForumRequest {
	String operation;
	String forumName;
	Integer forumId;
	Integer userId;
	Forum payload;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Forum getPayload() {
		return payload;
	}

	public void setPayload(Forum payload) { this.payload = payload; }

	public Integer getForumId() {
		return forumId;
	}

	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(Strig forumName) {
		this.forumName = forumName;
	}


}