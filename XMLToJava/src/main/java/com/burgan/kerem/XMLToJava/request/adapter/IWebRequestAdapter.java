package com.burgan.kerem.XMLToJava.request.adapter;

import com.burgan.kerem.XMLToJava.request.IRequest;

public interface IWebRequestAdapter<T, Y extends IRequest<T>> {

	Y convert();
	
}
