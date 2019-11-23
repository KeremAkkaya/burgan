package com.burgan.kerem.XMLToJava.request;

public class JSONRequest<T> extends AbstractRequest<T> {
	
	private String request;
	private Class<T> clazz;

	public JSONRequest(String request) {
		this(request, null);
		this.request = request;
	}

	public JSONRequest(String request, Class<T> clazz) {
		super(request, clazz);
		this.clazz = clazz;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	@Override
	public T getObject() {
		return null;
	}

	public String getRequest() {
		return request;
	}

	
}
