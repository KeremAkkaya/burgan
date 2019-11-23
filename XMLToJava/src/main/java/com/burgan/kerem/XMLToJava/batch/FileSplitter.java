package com.burgan.kerem.XMLToJava.batch;

import com.burgan.kerem.XMLToJava.ApplicationConstants;
import com.burgan.kerem.XMLToJava.file.File;
import com.burgan.kerem.XMLToJava.file.FileMode;
import com.burgan.kerem.XMLToJava.file.MainFile;
import com.burgan.kerem.XMLToJava.file.MetaFile;
import com.burgan.kerem.XMLToJava.file.SplittedFile;
import com.burgan.kerem.XMLToJava.observer.AbstractObserver;
import com.burgan.kerem.XMLToJava.observer.ObserverSubject;

public class FileSplitter extends AbstractObserver {

	private long latestFileCount;
	private long latestFilePosition;
	private long latestRecordCount;
	private MetaFile metaFile;
	private MainFile mainFile;

	private Boolean processing;

	public FileSplitter(ObserverSubject subject, MainFile mainFile) {
		super(subject);
		this.mainFile = mainFile;
		this.mainFile.openFile();
	}

	public void prepare() {
		this.metaFile = new MetaFile(mainFile, mainFile.getBaseName(), mainFile.getDate(), FileMode.WRITABLE);

		this.metaFile.openFile();
		this.latestFileCount = metaFile.readLong(0);
		this.latestRecordCount = metaFile.readLong(1);
		this.latestFilePosition = metaFile.readLong(2);

		this.processing = true;

	}

	public void splitFile() {
		Long totalRecordCount = mainFile.readLong(0);
		long currentFileCount = latestRecordCount % ApplicationConstants.fileDivideCount;
		if (currentFileCount == 0) {
			latestFileCount++;
		}

		while (totalRecordCount - latestRecordCount > 0 && processing) {

			long remainingRecordCount = remainingCount(totalRecordCount);

			long remainingFileRecordCount = ApplicationConstants.fileDivideCount - currentFileCount;
			long divideCnt = remainingRecordCount > remainingFileRecordCount ? remainingFileRecordCount
					: remainingRecordCount;

			File splittedFile = new SplittedFile(mainFile.getFileName(), FileMode.WRITABLE, latestFileCount);

			System.out.println(String.format("Processing file %s %04d ...", mainFile.getFileName(), latestFileCount));

			metaFile.seekLongIndex(0);
			metaFile.writeLong(latestFileCount);

			splittedFile.openFile();

			for (long i = 0; i < divideCnt; i++) {

				if (!processing) {
					closeFiles();
					System.out.println(String.format("Processing files STOPPED at  %s %04d  %05d!!!",
							mainFile.getFileName(), latestFileCount,
							ApplicationConstants.fileDivideCount - remainingFileRecordCount + i));
					break;
				}

				mainFile.seek(latestFilePosition + 1);
				String record = mainFile.readLine();

				splittedFile.writeLine(record);

				metaFile.seekLongIndex(1);
				metaFile.writeLong(++latestRecordCount);

				latestFilePosition = mainFile.getLastPosition();

				metaFile.seekLongIndex(2);
				metaFile.writeLong(latestFilePosition);

//				try {
//					System.out.print(String.format("%04d ",
//							ApplicationConstants.fileDivideCount - remainingFileRecordCount + i));
//					Thread.sleep(300);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}

			}
			splittedFile.closeFile();

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			++latestFileCount;
			currentFileCount = 0;

		}
		closeFiles();
		System.out.println(String.format("File processing FINISHED :)"));
	}

	private long remainingCount(long totalCount) {
		return totalCount - latestRecordCount;
	}

	private void closeFiles() {
		mainFile.closeFile();
		metaFile.closeFile();
	}

	@Override
	public void setProcessState(boolean state) {
		this.processing = state;
		System.out.println(String.format("Process state changed: %s", state));
	}

}
