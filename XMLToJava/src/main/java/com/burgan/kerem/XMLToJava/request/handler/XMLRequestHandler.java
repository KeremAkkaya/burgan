package com.burgan.kerem.XMLToJava.request.handler;

import com.burgan.kerem.XMLToJava.logger.ILogger;
import com.burgan.kerem.XMLToJava.request.IRequest;
import com.burgan.kerem.XMLToJava.request.JSONRequest;
import com.burgan.kerem.XMLToJava.request.adapter.IWebRequestAdapter;

public class XMLRequestHandler<T, Y extends IRequest<T>> implements IRequestHandler {

	private ILogger<String> logger;
	private IRequest<T> dataRequest;
	
	private IWebRequestAdapter<T, JSONRequest<T>> adapter;

	public XMLRequestHandler(IRequest<T> dataRequest, IWebRequestAdapter<T, JSONRequest<T>> adapter, ILogger<String> logger) {
		super();
		this.dataRequest = dataRequest;
		this.adapter = adapter;
		this.logger = logger;
	}

	public void handleRequest() {
		
		// WARNING: to track the original string... 
		System.out.println("Original Request: ".concat(dataRequest.getRequest()));
		
		JSONRequest<T> jsonRequest = adapter.convert();

		logger.setLogObject(jsonRequest.getRequest());
		
		logger.log();
		
	}

}
