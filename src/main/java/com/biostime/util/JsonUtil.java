package com.biostime.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonUtil {

	private static Gson GSON = new Gson();

	private JsonUtil() {
	}

	public static String toJson(Object src) {
		return GSON.toJson(src);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		return GSON.fromJson(json, clazz);
	}

	public static <T> T fromJson(String json, Type typeOfT) {
		return GSON.fromJson(json, typeOfT);
	}
}
