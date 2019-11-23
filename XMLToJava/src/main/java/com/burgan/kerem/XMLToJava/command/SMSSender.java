package com.burgan.kerem.XMLToJava.command;

public class SMSSender extends AbstractTextMessageSender {

	
	public SMSSender(String message) {
		super(message);
	}
	
	@Override
	public void execute() {
		System.out.println(String.format("Message sending: %s", getMessage()));
	}

}
