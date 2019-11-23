package com.burgan.kerem.XMLToJava.request;

public abstract class AbstractRequest<T> implements IRequest<T> {

	protected String request;
	protected Class<T> clazz;
	
	public AbstractRequest(String request, Class<T> clazz) {
		super();
		this.request = request;
		this.clazz = clazz;
	}
	public String getRequest() {
		return request;
	}
	public Class<T> getClazz() {
		return clazz;
	}
	
	
}
