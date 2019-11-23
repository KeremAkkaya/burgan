package com.burgan.kerem.XMLToJava.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataWrapper<T> {

	@JsonProperty("Data")
	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public DataWrapper(T data) {
		super();
		this.data = data;
	}
	
	
	
}
