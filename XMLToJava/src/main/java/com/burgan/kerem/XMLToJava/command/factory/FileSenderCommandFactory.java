package com.burgan.kerem.XMLToJava.command.factory;

import com.burgan.kerem.XMLToJava.command.FTPMessageSender;
import com.burgan.kerem.XMLToJava.command.ISenderCommand;
import com.burgan.kerem.XMLToJava.command.MSSQLMessageSender;
import com.burgan.kerem.XMLToJava.file.File;

public class FileSenderCommandFactory implements ICommandFactory {

	private File file;
	

	public FileSenderCommandFactory(File file) {
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
	public ISenderCommand getCommand(String name) {
		switch (name) {
		case "ms":
			return new MSSQLMessageSender(getFile());
		case "ftp":
			return new FTPMessageSender(file);
		}
		return new FTPMessageSender(file);
	}

}
