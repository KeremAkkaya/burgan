package com.burgan.kerem.XMLToJava.application.test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * To test the server...
 * @author Kerem Akkaya
 *
 */
public class ClientApplication {

	private static String testTraceXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Data><Method><Name>Order</Name><Type>Services</Type><Assembly>ServiceRepository, Version=1.0.0.1, Culture=neutral,PublicKeyToken=null</Assembly></Method><Process><Name>scheduler.exe</Name><Id>185232</Id><Start><Epoch>1464709722277</Epoch><Date>2016-05-31T12:07:42.2771759+03:00</Date></Start></Process><Layer>DailyScheduler</Layer><Creation><Epoch>1464709728500</Epoch><Date>2016-05-31T07:48:21.5007982+03:00</Date></Creation><Type>Trace</Type></Data>";
	private static String testInformationXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Data><Method><Name>Order</Name><Type>Services</Type><Assembly>ServiceRepository, Version=1.0.0.1, Culture=neutral,PublicKeyToken=null</Assembly></Method><Process><Name>scheduler.exe</Name><Id>185232</Id><Start><Epoch>1464709722277</Epoch><Date>2016-05-31T12:07:42.2771759+03:00</Date></Start></Process><Layer>DailyScheduler</Layer><Creation><Epoch>1464709728500</Epoch><Date>2016-05-31T07:48:21.5007982+03:00</Date></Creation><Type>Information</Type></Data>";

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		String xml = testInformationXML;
		Integer count = 10;
		if (args.length == 0) {
			System.out.println(String.format("Sending Information xml as default. Send count %d.", count));
			System.out.println("Parameters explained at below...");
			System.out.println(" 1- 't' to send trace xml");
			System.out.println(" 2- any number for send request count");
		} else {
			String type = args[0];
			if ("t".equals(type)) {
				xml = testTraceXML;
			}
		}

		if (args.length == 2) {
			String countStr = args[1];
			try {
				count = Integer.parseInt(countStr);
			} catch (NumberFormatException ex) {
				System.out.println(String.format("2nd parameter must be a number... Now default parameter %d is used for request count.", count));
			}
		}

		InetAddress host = InetAddress.getLocalHost();
		Socket socket = null;
		ObjectOutputStream oos = null;
		Scanner ois = null;
		for (int i = 0; i < count; i++) {
			// establish socket connection to server
			socket = new Socket(host.getHostName(), 9091);
			socket.setSoTimeout(0);
			// write to socket using ObjectOutputStream
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println(String.format("Sending request to Socket Server %04d", i + 1));
			oos.writeObject(xml.concat(System.lineSeparator()).concat(System.lineSeparator()));
			// read the server response message
			Thread.sleep(100);
			ois = new Scanner(socket.getInputStream());
			String message = ois.nextLine();
			System.out.println("Message: " + message);
			// close resources
			ois.close();
			oos.close();
			Thread.sleep(100);
		}
	}

}
