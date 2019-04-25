package com.aaupush.util;

import com.aaupush.com.Post;

public class PostRequest {
	String operation;
	Integer postId;
	Post payload;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Post getPayload() {
		return payload;
	}

	public void setPayload(Post payload) {
		this.payload = payload;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}




}