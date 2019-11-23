package com.burgan.kerem.XMLToJava.command;

public class ProcessStoppedCommand implements ISenderCommand {

	@Override
	public void execute() {
		
		System.out.println("Command Pattern test... Command process stopped by user action!!!");

	}

}
