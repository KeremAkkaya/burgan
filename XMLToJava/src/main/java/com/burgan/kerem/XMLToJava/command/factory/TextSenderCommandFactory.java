package com.burgan.kerem.XMLToJava.command.factory;

import com.burgan.kerem.XMLToJava.command.EmailSender;
import com.burgan.kerem.XMLToJava.command.ISenderCommand;
import com.burgan.kerem.XMLToJava.command.SMSSender;

public class TextSenderCommandFactory implements ICommandFactory {

	private String message;
	
	private String from;
	
	private String to;
	
	private String subject;
	
	public TextSenderCommandFactory(String message) {
		super();
		this.message = message;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public ISenderCommand getCommand(String name) {
		
		switch (name) {
		case "sms":
			return new SMSSender(getMessage());
		case "email":
			return new EmailSender(getMessage(), from, to, subject);
			
		}
		return new SMSSender(getMessage());
	}

	public TextSenderCommandFactory setEmail(String from, String to, String subject) {
		this.from = from;
		this.to = to;
		return this;
	}
	
	
}
