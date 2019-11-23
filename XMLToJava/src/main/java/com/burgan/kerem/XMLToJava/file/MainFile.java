package com.burgan.kerem.XMLToJava.file;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.ZonedDateTime;

public class MainFile extends File {

	private static int longSize = Long.BYTES;


	public MainFile(String fileName, FileMode mode) {
		super(fileName, mode);
	}


	public MainFile(String baseName, ZonedDateTime date, FileMode mode) throws FileNotFoundException {
		super(baseName, date, mode);
	}
	
	
	@Override
	public void openFile() {
		super.openFile();
		
		if(readLong(0) == 0l) {
			writeLong(0l);
		}
	}
	

	@Override
	protected String buildFileName() {
		
		if(fileName != null) {
			return fileName;
		}
		
		StringBuilder builder = new StringBuilder(getBaseName());

		builder.append(separator).append(dateFromatter.format(getDate())).append(getExtension());

		return builder.toString();
	}

	@Override
	public void writeLong(long value) {
		moveToBeginning();
		super.writeLong(value);
		System.out.println(String.format("Record count in main file: %03d", value));
	}

	@Override
	public void writeLine(String value) {
		if (getLastPosition() > longSize) {
			try {
				this.writeLineToEnd(value);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void writeLineToEnd(String value) throws IOException  {
		if (getLastPosition() > longSize) {
			moveToEnd();
			super.writeLine(value);
			this.incrementRecordCount();
		}

	}

	@Override
	public Long readLong(int index) {
		moveToBeginning();
		try {
			long value = file.readLong();
			
			incrementPosition();
			
			return value;
		} catch(EOFException ex) {
			System.err.println("Read long not found " + ex.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return 0l;
	}

	@Override
	public String readLine() {
		String record = null;
		try {
			if (getLastPosition() >= longSize) {
				record = file.readLine();
				incrementPosition();
			}
			return record;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void incrementRecordCount() {
		long count = readLong(0);
		writeLong(++count);
	}

	
	@Override
	public void seek(long position) {
		if(position <= Long.BYTES) {
			position = Long.BYTES + 1;
		}
		super.seek(position);
	}
}
