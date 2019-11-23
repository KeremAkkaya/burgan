package com.burgan.kerem.XMLToJava.file;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.burgan.kerem.XMLToJava.exception.WebException;

public class MetaFile extends File {

	private File mainFile;

	// Positions have to be exactly one of the elements in locations array
	private static short[] locations = new short[] { 0 * Long.BYTES, 1 * Long.BYTES, 2 * Long.BYTES };

	
	public MetaFile(File mainFile, String baseName, ZonedDateTime date, FileMode mode) {
		super(baseName, date, mode);
		this.mainFile = mainFile;
	}

	@Override
	protected String buildFileName() {
		return mainFile.buildFileName().concat(getExtension());
	}

	@Override
	public String getExtension() {
		return ".meta";
	}

	@Override
	public void writeLong(long value) {
		if (validatePosition()) {
			super.writeLong(value);
		} else {
			throw new WebException();
		}
	}

	@Override
	public void writeLine(String value) {
		throw new WebException();
	}

	@Override
	public Long readLong(int index) {
		seekLongIndex(index);
		if (validatePosition()) {
			try {
				long value = file.readLong();
				return value;
			} catch (IOException e) {
				//System.err.println(e.getMessage());
			}
		}
		return 0l;
	}

	@Override
	public String readLine() {
		throw new WebException();
	}

	public boolean validatePosition() {
		for (int i = 0; i < locations.length; i++) {
			if (getLastPosition() == locations[i]) {
				return true;
			}
		}
		return false;
	}

}
