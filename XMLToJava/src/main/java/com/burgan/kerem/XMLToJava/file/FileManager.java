package com.burgan.kerem.XMLToJava.file;

public class FileManager {

	private File fileStore;

	public FileManager(File fileStore) {
		super();
		this.fileStore = fileStore;
	}

	public File getFileStore() {
		return fileStore;
	}

	public void setFileStore(File fileStore) {
		this.fileStore = fileStore;
	}

}
