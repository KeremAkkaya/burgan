package com.burgan.kerem.XMLToJava.test.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {

	private static String testInformationXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Data><Method><Name>Order</Name><Type>Services</Type><Assembly>ServiceRepository, Version=1.0.0.1, Culture=neutral,PublicKeyToken=null</Assembly></Method><Process><Name>scheduler.exe</Name><Id>185232</Id><Start><Epoch>1464709722277</Epoch><Date>2016-05-31T12:07:42.2771759+03:00</Date></Start></Process><Layer>DailyScheduler</Layer><Creation><Epoch>1464709728500</Epoch><Date>2016-05-31T07:48:21.5007982+03:00</Date></Creation><Type>Trace</Type></Data>";
	private static String testXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Data><Method><Name>Order</Name><Type>Services</Type><Assembly>ServiceRepository, Version=1.0.0.1, Culture=neutral,PublicKeyToken=null</Assembly></Method><Process><Name>scheduler.exe</Name><Id>185232</Id><Start><Epoch>1464709722277</Epoch><Date>2016-05-31T12:07:42.2771759+03:00</Date></Start></Process><Layer>DailyScheduler</Layer><Creation><Epoch>1464709728500</Epoch><Date>2016-05-31T07:48:21.5007982+03:00</Date></Creation><Type>Information</Type></Data>";

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		InetAddress host = InetAddress.getLocalHost();
		Socket socket = null;
		ObjectOutputStream oos = null;
		Scanner ois = null;
		for (int i = 0; i < 3; i++) {
			// establish socket connection to server
			socket = new Socket(host.getHostName(), 9091);
			socket.setSoTimeout(0);
			// write to socket using ObjectOutputStream
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println(String.format("Sending request to Socket Server %04d", i));
			oos.writeObject(testInformationXML.concat(System.lineSeparator()).concat(System.lineSeparator()));
			// read the server response message
			Thread.sleep(50);
			ois = new Scanner(socket.getInputStream());
			String message = ois.nextLine();
			System.out.println("Message: " + message);
			// close resources
			ois.close();
			oos.close();
			Thread.sleep(10);
		}
	}

}
