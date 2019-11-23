package com.burgan.kerem.XMLToJava.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;

import com.burgan.kerem.XMLToJava.exception.WebException;

public class SplittedFile extends File {

	protected long maxRecordCount;
	protected long fileNumber;


	private final static String relativePath = ".\\logs\\";
	
	public SplittedFile(String fileName, FileMode mode, long fileNumber) {
		this(fileName, mode, 100, fileNumber);
	}
	
	public SplittedFile(String baseName, ZonedDateTime date, FileMode mode, long fileNumber) {
		this(baseName, date, mode, 100, fileNumber);
	}

	public SplittedFile(String baseName, ZonedDateTime date, FileMode mode, long maxRecordCount, long fileNumber) {
		super(baseName, date, mode);

		this.maxRecordCount = maxRecordCount;
		this.fileNumber = fileNumber;
	}
	
	public SplittedFile(String fileName, FileMode mode, long maxRecordCount, long fileNumber) {
		super(fileName, mode);

		this.maxRecordCount = maxRecordCount;
		this.fileNumber = fileNumber;
		this.fileName = fileName;
	}

	@Override
	protected String buildFileName() {
		
		if(fileName != null) {
			return new StringBuilder(relativePath).append(fileName).append(separator).append(String.format("%04d", fileNumber))
					.append(getExtension()).toString();
		}
		
		StringBuilder builder = new StringBuilder(relativePath).append(getBaseName());

		builder.append(separator).append(dateFromatter.format(getDate())).append(String.format("%04d", fileNumber))
				.append(getExtension());
		return builder.toString();
	}
	
	@Override
	public void writeLong(long value) {
		throw new WebException();

	}

	@Override
	public void writeLine(String value) {
		moveToEnd();
		super.writeLine(value);
	}

	@Override
	public Long readLong(int index) {
		throw new WebException();
	}

	@Override
	public String readLine() {
		throw new WebException();
	}

	@Override
	protected String getPath() {
		return relativePath;
	}
	
	@Override
	public void openFile() {
		Path path = Paths.get(getPath());
		if(Files.notExists(path)){
	        try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
		super.openFile();
	}
}
