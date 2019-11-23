package com.burgan.kerem.XMLToJava.command;

import com.burgan.kerem.XMLToJava.file.File;

public abstract class AbstractFileMessageSender implements ISenderCommand {

	private File file;

	public AbstractFileMessageSender(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
