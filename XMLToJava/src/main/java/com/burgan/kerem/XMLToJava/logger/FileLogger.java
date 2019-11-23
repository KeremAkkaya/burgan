package com.burgan.kerem.XMLToJava.logger;

import com.burgan.kerem.XMLToJava.file.File;

public class FileLogger implements ILogger<String> {

	private File file;
	private String log;

	public FileLogger(File file, String log) {
		super();
		this.file = file;
		this.log = log;
	}
	

	public FileLogger(File file) {
		super();
		this.file = file;
	}


	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public void log() {
		file.openFile();
		file.writeLine(log);
		file.closeFile();
	}

	@Override
	public String getLogObject() {
		return log;
	}

	@Override
	public void setLogObject(String obj) {
		this.log = obj;
	}

}
