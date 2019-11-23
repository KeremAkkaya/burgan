package com.burgan.kerem.XMLToJava.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverSubject {

	private List<IProcessObserver> observers = new ArrayList<IProcessObserver>();
	private boolean state = true;

	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		if (this.state != state) {
			this.state = state;
			notifyAllObservers();
		}
	}

	public void attach(IProcessObserver observer) {
		observers.add(observer);
	}

	public void notifyAllObservers() {
		for (IProcessObserver observer : observers) {
			observer.setProcessState(state);
		}
	}
}
