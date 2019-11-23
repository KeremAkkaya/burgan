package com.burgan.kerem.XMLToJava.application.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.text.DateFormatter;

import com.burgan.kerem.XMLToJava.application.ApplicationConstants;
import com.burgan.kerem.XMLToJava.models.Data;
import com.burgan.kerem.XMLToJava.models.DataWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Hello world!
 *
 */
@Deprecated
public class App {

	private static String testXML2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Data><Method><Name>Order</Name><Type>Services</Type><Assembly>ServiceRepository, Version=1.0.0.1, Culture=neutral,PublicKeyToken=null</Assembly></Method><Process><Name>scheduler.exe</Name><Id>185232</Id><Start><Epoch>1464709722277</Epoch><Date>2016-05-31T12:07:42.2771759+03:00</Date></Start></Process><Layer>DailyScheduler</Layer><Creation><Epoch>1464709728500</Epoch><Date>2016-05-31T07:48:21.5007982+03:00</Date></Creation><Type>Information</Type></Data>";
	private static String testXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Data><Method><Name>Order</Name><Type>Services</Type><Assembly>ServiceRepository, Version=1.0.0.1, Culture=neutral,PublicKeyToken=null</Assembly></Method><Process><Name>scheduler.exe</Name><Id>185232</Id><Start><Epoch>1464709722277</Epoch><Date>2016-05-31T12:07:42.2771759+03:00</Date></Start></Process><Layer>DailyScheduler</Layer><Creation><Epoch>1464709728500</Epoch><Date>2016-05-31T07:48:21.5007982+03:00</Date></Creation><Type>Trace</Type></Data>";

	private static Integer divideCount = 100;
	
	public static void main(String[] args) throws Exception {
		App1();
		App2();

	}
	
	private static void App2() {
		try (Stream<Path> walk = Files.walk(Paths.get(ApplicationConstants.splittedFileRelativePath))) {

			List<String> result = walk.map(x -> x.toString()).filter(f -> f.startsWith("Information") && f.endsWith(".log"))
					.collect(Collectors.toList());
			lastPosition = (long) Long.BYTES;
			result.forEach(str -> {
				System.out.println(str);
				System.out.println(getLatestFileName(str, 1));
				divideFile(lastPosition, 0, str, 1);
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	private static Long lastPosition = 0l;
	
	private static void divideFile(long latestFilePositon, long lastCount, String fileName, long lastFileNameCount) {
		
		//First read file
 		Long count = readLongFromRandomAccessFile(fileName);
		
		String latestFileName = getLatestFileName(fileName, lastFileNameCount);
		String metaFileName = fileName.concat(".meta");
		
		while(count - lastCount > 0) {
			
			writeToRandomAccessFile(metaFileName, 0l,lastFileNameCount);
			
			long divideCnt = count - lastCount > divideCount ? divideCount : count - lastCount;
			
			for (long i = 0; i < divideCnt; i++) {
				String str = readStringFromRandomAccessFile(fileName, latestFilePositon + 1);
				
				writeToRandomAccessFile(latestFileName, str);
				
				writeToRandomAccessFile(metaFileName, 8l, ++lastCount);
				writeToRandomAccessFile(metaFileName, 17l, ++lastCount);
				latestFilePositon = lastPosition;
			}
			
			
			latestFileName = getLatestFileName(fileName, ++lastFileNameCount);
			
		}
		
	}
	
	private static String getLatestFileName(String base, long count) {
		
		int lastIndex = base.lastIndexOf(".");
		
		String baseName = base.substring(0, lastIndex);
		String baseExt = base.substring(lastIndex);
		
		return String.format("%s-%04d%s" , baseName, count, baseExt);
	}
	
	

	public static void App1() throws Exception {
		// First convert XML to object

		XmlMapper xmlMapper = new XmlMapper();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSXXX");
		dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));

		xmlMapper.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
		xmlMapper.setDateFormat(dateFormat);

		xmlMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		JavaTimeModule module = new JavaTimeModule();
		// module.addSerializer(ZonedDateTime.class, JSR310DateTimeSerializer.INSTANCE);
		xmlMapper.registerModule(module);

		Data data = xmlMapper.readValue(testXML2, Data.class);

		// Now we have data object.
		System.out.println("Type:" + data.getType());

		// Configure mapper object according to requirements

		ObjectMapper mapper = new ObjectMapper();

		// mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// StdDateFormat is ISO8601 since jackson 2.9

		mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
		mapper.setDateFormat(dateFormat);

		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		String json = mapper.writeValueAsString(new DataWrapper(data));

		System.out.println(json);

		String fileNameToWrite = getFileName(data);

		System.out.println(fileNameToWrite);
		Long count = null;
		if (!controlFile(fileNameToWrite)) {
			count = 0l;
			writeToRandomAccessFile(fileNameToWrite, count);
		} else {
			count = readLongFromRandomAccessFile(fileNameToWrite);
		}
		// System.out.println("Total Count:" + count);

		// 0 index is used to store record count of file.
		for (int i = 0; i < 1335; i++) {
			writeToRandomAccessFile(fileNameToWrite, json);

			writeToRandomAccessFile(fileNameToWrite, ++count);

			System.out.println("Total Count:" + count);
		}
		lastPosition = (long) Long.BYTES;
		for (int i = 0; i < count; i++) {
			String str = readStringFromRandomAccessFile(fileNameToWrite, lastPosition + 1);
			System.out.println(str);
		}
	}
	
	


	private static String getModuleName(Data data) {
		return data.getType().concat("-")
				.concat(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(data.getCreation().getDate()));
	}

	private static String getFileName(Data data) {
		return getModuleName(data).concat(".log");
	}

	private static Boolean controlFile(String fileName) {
		File file = new File(fileName);

		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Utility method to read from RandomAccessFile in Java
	 */
	public static String readStringFromRandomAccessFile(String file, long positon) {
		String record = null;
		try {
			RandomAccessFile fileStore = new RandomAccessFile(file, "r");

			// moves file pointer to position specified
			fileStore.seek(positon);

			// reading String from RandomAccessFile
			record = fileStore.readLine();

			System.out.println(fileStore.getFilePointer());
			lastPosition = fileStore.getFilePointer();
			fileStore.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return record;
	}

	/*
	 * Utility method to read from RandomAccessFile in Java
	 */
	public static Long readLongFromRandomAccessFile(String file) {
		Long count = null;
		try {
			RandomAccessFile fileStore = new RandomAccessFile(file, "r");

			// moves file pointer to position specified
			fileStore.seek(0);

			// reading String from RandomAccessFile
			count = fileStore.readLong();

			fileStore.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return count;
	}


	/*
	 * Utility method for writing into RandomAccessFile in Java
	 */
	public static void writeToRandomAccessFile(String file, String record) {
		try {
			RandomAccessFile fileStore = new RandomAccessFile(file, "rw");

			long position = fileStore.length();
			// moves file pointer to position specified
			fileStore.seek(position);

			StringBuilder builder = new StringBuilder(record);

			builder.append(System.lineSeparator());
			// writing String to RandomAccessFile
			fileStore.writeUTF(builder.toString());

			fileStore.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Utility method for writing into RandomAccessFile in Java
	 */
	public static void writeToRandomAccessFile(String file, Long count) {
		writeToRandomAccessFile(file, 0l, count);
	}

	/*
	 * Utility method for writing into RandomAccessFile in Java
	 */
	public static void writeToRandomAccessFile(String file, Long position, Long value) {
		try {
			RandomAccessFile fileStore = new RandomAccessFile(file, "rw");

			// moves file pointer to position specified
			fileStore.seek(position);

			// writing Long to RandomAccessFile
			fileStore.writeLong(value);

			fileStore.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
