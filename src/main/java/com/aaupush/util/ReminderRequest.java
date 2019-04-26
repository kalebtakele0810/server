package com.aaupush.util;

import com.aaupush.com.Reminder;

public class ReminderRequest {
	String operation;
	Integer reminderId;
	Reminder payload;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Reminder getPayload() {
		return payload;
	}

	public void setPayload(Reminder payload) { this.payload = payload; }

	public Integer getReminderId() {
		return reminderId;
	}

	public void setReminderId(Integer reminderId) {
		this.reminderId = reminderId;
	}



}