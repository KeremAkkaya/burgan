package com.burgan.kerem.XMLToJava.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class File implements IFileIterator, IFileIO {

	protected final static Character separator = '-';
	protected final static DateTimeFormatter dateFromatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	protected final static String lineSeparator = System.lineSeparator();

	protected RandomAccessFile file;

	protected long recordCount;

	protected String baseName;
	protected ZonedDateTime date;
	protected FileMode mode;

	protected String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File(String fileName, FileMode mode) {
		super();
		this.fileName = fileName;
		this.mode = mode;
	}

	protected File(String baseName, ZonedDateTime date, FileMode mode) {
		this.baseName = baseName;
		this.date = date;
		this.mode = mode;
	}

	protected abstract String buildFileName();
	
	protected String getPath() {
		return ".\\";
	}

	public void openFile() {
		try {
			java.io.File controlFile = new java.io.File(buildFileName());
			if (!controlFile.exists()) {
				controlFile.createNewFile();
			}

			file = new RandomAccessFile(buildFileName(), mode.getModeStr());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeFile() {
		if (file != null) {
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public RandomAccessFile getFile() {
		return file;
	}

	public long getLastPosition() {
		try {
			return file.getFilePointer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0l;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public String getBaseName() {
		return baseName;
	}

	public ZonedDateTime getDate() {
		return date;
	}

	public FileMode getMode() {
		return mode;
	}

	@Override
	public long getTotalRecordCount() {
		return recordCount;
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hasMore() {
		// TODO Auto-generated method stub

	}

	@Override
	public void isEOF() {
		// TODO Auto-generated method stub

	}

	public String getExtension() {
		return ".log";
	}

	protected void incrementPosition() throws IOException {
		// seek to the next position
		file.seek(file.getFilePointer() + 1);
	}

	@Override
	public void writeLine(String value) {
		StringBuilder builder = new StringBuilder(value);

		builder.append(lineSeparator);
		// writing String to RandomAccessFile
		try {
			file.writeUTF(builder.toString());
			incrementPosition();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void writeLong(long value) {
		try {
			file.writeLong(value);
			incrementPosition();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void seek(long position) {
		try {
			file.seek(position);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void seekLongIndex(int index) {
		try {
			file.seek(Long.BYTES * index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void moveToBeginning() {

		try {
			file.seek(0l);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void moveToEnd() {
		try {
			file.seek(file.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
