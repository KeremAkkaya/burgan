package com.burgan.kerem.XMLToJava.command.factory;

import com.burgan.kerem.XMLToJava.command.ISenderCommand;

public interface ICommandFactory {

	public ISenderCommand getCommand(String name);
}
