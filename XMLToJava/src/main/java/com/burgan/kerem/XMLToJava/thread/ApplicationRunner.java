package com.burgan.kerem.XMLToJava.thread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.burgan.kerem.XMLToJava.command.ISenderCommand;
import com.burgan.kerem.XMLToJava.observer.AbstractObserver;
import com.burgan.kerem.XMLToJava.observer.ObserverSubject;
import com.burgan.kerem.XMLToJava.strategy.StrategyContext;

public class ApplicationRunner extends AbstractObserver implements Runnable {

	private StrategyContext context;

	private List<ISenderCommand> commandList;

	public ApplicationRunner(ObserverSubject subject, StrategyContext context) {
		super(subject);
		this.context = context;
	}

	public ApplicationRunner(ObserverSubject subject, StrategyContext context, List<ISenderCommand> commandList) {
		super(subject);
		this.context = context;
		this.commandList = commandList;
	}

	@Override
	public void run() {
		
		
		try (Stream<Path> walk = Files.walk(Paths.get("."))) {

			List<String> result = walk.map(x -> x.toString())
					.filter(f -> f.endsWith(".log") && !f.contains("logs\\")).collect(Collectors.toList());

			context.executeStrategy(result);

			executeCommands();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void executeCommands() {
		if (commandList != null) {
			for (ISenderCommand iSenderCommand : commandList) {
				iSenderCommand.execute();
			}
		}
	}

	@Override
	public void setProcessState(boolean state) {

	}

}
