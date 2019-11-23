package com.burgan.kerem.XMLToJava.command;

import com.burgan.kerem.XMLToJava.file.File;

public class FTPMessageSender extends AbstractFileMessageSender {

	private String host = "127.0.0.1";
	private String path = "\\logs\\";

	public FTPMessageSender(File file) {
		super(file);
	}

	@Override
	public void execute() {
		System.out.println(String.format("Sending file %s to FTP in %s in directory %s...", getFile(), host, path));
	}

}
