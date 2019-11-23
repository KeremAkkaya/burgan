package com.burgan.kerem.XMLToJava.strategy;

import java.util.List;

import com.burgan.kerem.XMLToJava.observer.ObserverSubject;

public class MultithreadedStrategy extends AbstractStrategy {

	public MultithreadedStrategy(ObserverSubject subject) {
		super(subject);
	}

	@Override
	public void doAction(List<String> fileNames) {
		// Process files using multiple threads and thread pool.

	}

}
