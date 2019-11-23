package com.burgan.kerem.XMLToJava.observer;

import java.util.ArrayList;
import java.util.List;

import com.burgan.kerem.XMLToJava.command.CommandController;
import com.burgan.kerem.XMLToJava.command.ISenderCommand;
import com.burgan.kerem.XMLToJava.command.factory.ICommandPicker;

public class ObserverSubject implements ICommandPicker {

	private List<IProcessObserver> observers = new ArrayList<IProcessObserver>();

	private CommandController commandController = new CommandController();

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

	@Override
	public void pickCommand(ISenderCommand command) {
		commandController.getCommandList().add(command);
	}

	public CommandController getCommandController() {
		return commandController;
	}

	public void setCommandController(CommandController commandController) {
		this.commandController = commandController;
	}

}
