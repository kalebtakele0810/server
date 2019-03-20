package com.aaupush.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer {
	public String objectToJsonString(Object obj) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(obj);
	}

	public Object stringToObject(String jsonString, Class c) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Object obj = gson.fromJson(jsonString, c);
		return obj;
	}
}
