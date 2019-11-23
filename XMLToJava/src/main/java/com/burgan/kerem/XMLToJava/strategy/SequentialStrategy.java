package com.burgan.kerem.XMLToJava.strategy;

import java.util.List;

import com.burgan.kerem.XMLToJava.batch.FileSplitter;
import com.burgan.kerem.XMLToJava.file.FileMode;
import com.burgan.kerem.XMLToJava.file.MainFile;
import com.burgan.kerem.XMLToJava.observer.ObserverSubject;

public class SequentialStrategy extends AbstractStrategy {

	public SequentialStrategy(ObserverSubject subject) {
		super(subject);
	}

	@Override
	public void doAction(List<String> fileNames) {
		fileNames.forEach(str -> {

			FileSplitter splitter = new FileSplitter(getSubject(), new MainFile(str, FileMode.READONLY));
			splitter.prepare();
			splitter.splitFile();

		});
	}

}
