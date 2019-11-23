package com.burgan.kerem.XMLToJava.file;

public interface IFileIO {
	
	void writeLong(long value);
	void writeLine(String value);
	Long readLong(int index);
	String readLine();

}
