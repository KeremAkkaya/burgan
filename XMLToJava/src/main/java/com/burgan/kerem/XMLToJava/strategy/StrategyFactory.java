package com.burgan.kerem.XMLToJava.strategy;

import com.burgan.kerem.XMLToJava.observer.ObserverSubject;

public class StrategyFactory {

	public static IWorkerStrategy getStrategy(String strategyName, ObserverSubject subject) {

		switch (strategyName) {
		case "s":
			return new SequentialStrategy(subject);
		case "m":
			return new MultithreadedStrategy(subject);
		default:
			return new SequentialStrategy(subject);
		}

	}

}
