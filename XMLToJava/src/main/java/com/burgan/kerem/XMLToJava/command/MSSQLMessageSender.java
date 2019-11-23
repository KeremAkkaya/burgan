package com.burgan.kerem.XMLToJava.command;

import com.burgan.kerem.XMLToJava.file.File;

public class MSSQLMessageSender extends AbstractFileMessageSender {

	public MSSQLMessageSender(File file) {
		super(file);
	}

	@Override
	public void execute() {
		System.out.println(String.format("Sending file %s to MSSQL DB...", getFile()));

	}

}
