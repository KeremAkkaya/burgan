package com.burgan.kerem.XMLToJava.strategy;

import java.util.List;

public class StrategyContext {

	private IWorkerStrategy strategy;

	public StrategyContext(IWorkerStrategy strategy) {
		super();
		this.strategy = strategy;
	}
	
	public void executeStrategy(List<String> fileNames) {
		strategy.doAction(fileNames);
	}
	
}
