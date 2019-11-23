package com.burgan.kerem.XMLToJava.logger;

public interface ILogger<T> {

	void log();
	T getLogObject();
	void setLogObject(T obj);
}
