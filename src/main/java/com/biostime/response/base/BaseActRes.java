package com.biostime.response.base;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "出参")
public class BaseActRes<T> extends BaseRes{
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
