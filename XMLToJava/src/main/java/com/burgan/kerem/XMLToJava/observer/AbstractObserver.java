package com.burgan.kerem.XMLToJava.observer;

public abstract class AbstractObserver implements IProcessObserver {

	protected ObserverSubject subject;

	public AbstractObserver(ObserverSubject subject) {
		super();
		this.subject = subject;
		this.subject.attach(this);
	}

	public ObserverSubject getSubject() {
		return subject;
	}

	public void setSubject(ObserverSubject subject) {
		this.subject = subject;
	}

	public abstract void setProcessState(boolean state);

}
