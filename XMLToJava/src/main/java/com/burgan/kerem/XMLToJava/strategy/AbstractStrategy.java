package com.burgan.kerem.XMLToJava.strategy;

import java.util.List;

import com.burgan.kerem.XMLToJava.observer.AbstractObserver;
import com.burgan.kerem.XMLToJava.observer.ObserverSubject;
 
public abstract class AbstractStrategy extends AbstractObserver implements IWorkerStrategy {

	public AbstractStrategy(ObserverSubject subject) {
		super(subject);
	}

	protected boolean state ;
	
	@Override
	public abstract void doAction(List<String> fileNames);

	@Override
	public void setProcessState(boolean state) {
		this.state = state;
		
	}

}
