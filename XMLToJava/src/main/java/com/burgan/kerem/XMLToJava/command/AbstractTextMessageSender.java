package com.burgan.kerem.XMLToJava.command;

public abstract class AbstractTextMessageSender implements ISenderCommand {

	private String message;

	public AbstractTextMessageSender(String message) {
		super();
		this.message = message;
	}

	protected String getMessage() {
		return message;
	}
}
