package com.burgan.kerem.XMLToJava.file;

public interface IFileIterator {

	long getTotalRecordCount();
	
	void next();
	
	void hasMore();
	
	void isEOF();
	
}
