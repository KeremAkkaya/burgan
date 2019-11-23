package com.burgan.kerem.XMLToJava.command;

import java.util.ArrayList;
import java.util.List;

import com.burgan.kerem.XMLToJava.command.factory.FileSenderCommandFactory;
import com.burgan.kerem.XMLToJava.command.factory.ICommandFactory;
import com.burgan.kerem.XMLToJava.command.factory.TextSenderCommandFactory;
import com.burgan.kerem.XMLToJava.file.File;

public class CommandController {

	private List<ISenderCommand> commandList = new ArrayList<>();
	private File file;
	private String message;

	
	public void generateDefaultCommandList() {
		commandList = new ArrayList<ISenderCommand>(4);
		if (file != null) {
			ICommandFactory factory1 = getFileSenderCommandFactory(file);
			factory1.getCommand("ftp");
			commandList.add(factory1.getCommand("ftp"));
		}
		if (message != null) {
			TextSenderCommandFactory factory = getTextSenderCommandFactory(message);
			commandList.add(factory.getCommand("sms"));
			commandList.add(factory.getCommand("email"));
		}
	}
	
	public void generateDefaultCommandListFromString(String commands) {
		//TODO: split string to list using "," and generate commands according to splitted list
	}

	public FileSenderCommandFactory getFileSenderCommandFactory(File file) {
		return new FileSenderCommandFactory(file);
	}

	public TextSenderCommandFactory getTextSenderCommandFactory(String message) {
		return new TextSenderCommandFactory(message);
	}

	public List<ISenderCommand> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<ISenderCommand> commandList) {
		this.commandList = commandList;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
