package com.burgan.kerem.XMLToJava.application.batch;

import java.io.IOException;
import java.util.Scanner;

import com.burgan.kerem.XMLToJava.observer.ObserverSubject;
import com.burgan.kerem.XMLToJava.strategy.IWorkerStrategy;
import com.burgan.kerem.XMLToJava.strategy.StrategyContext;
import com.burgan.kerem.XMLToJava.strategy.StrategyFactory;

/**
 * This application processes files and performs necessary actions according to implementation and parameters.
 * @author Kerem Akkaya
 *
 */
public class WorkerApplication {

	public static void main(String[] args) throws IOException {

		String strategyCommand = "s"; // s: sequential, m: multithreaded, p: multiprocessed
		String sendCommand = "f";//f: file, s: sms, e: email, d: database TODO: later not implemented
		switch (args.length) {
		case 1:
			strategyCommand =  args[0];
			break;
		case 2:
			strategyCommand =  args[0];
			sendCommand = args[1];
			break;
		}
		
		ObserverSubject subject = new ObserverSubject();
		subject.setState(true);
		
		IWorkerStrategy strategy = StrategyFactory.getStrategy(strategyCommand, subject);
		StrategyContext context = new StrategyContext(strategy);
		
		// create a BufferedReader using System.in
		Scanner s = new Scanner(System.in); 
		String str;

		System.out.println("File splitter is running...");
		WorkerApplication application = new WorkerApplication();
		application.processFiles(subject, context);
		System.out.println("Enter 'stop' to quit.");
		do {
			str = s.nextLine();
			System.err.println(str);
		} while (!str.equals("stop"));

		subject.setState(false);
		
		s.close();
		
		
	}

	private Thread thread;
	
	public void processFiles(ObserverSubject subject, StrategyContext context) {

		ApplicationRunner runner = new ApplicationRunner(subject, context);

		thread = new Thread(runner);

		thread.start();

	}

}
