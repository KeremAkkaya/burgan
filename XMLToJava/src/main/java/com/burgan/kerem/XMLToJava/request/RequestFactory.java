package com.burgan.kerem.XMLToJava.request;

public class RequestFactory<T> {

	public IRequest<T> getRequest(String type, String request, Class<T> clazz){
		
		if("xml".equals(type)) {
			return new XMLRequest<T>(request, clazz);
		}
		else if("json".equals(type)) {
			return new JSONRequest<T>(request, clazz);
		}
		//Default is XMLRequest
		return new XMLRequest<T>(request, clazz);
	}
	
}
