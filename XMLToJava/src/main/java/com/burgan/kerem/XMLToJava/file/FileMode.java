package com.burgan.kerem.XMLToJava.file;

public enum FileMode {
	
	READONLY("r"),
	WRITABLE("rw");
	
	private String modeStr;

	private FileMode(String modeStr) {
		this.modeStr = modeStr;
	}

	public String getModeStr() {
		return modeStr;
	}
	
}
