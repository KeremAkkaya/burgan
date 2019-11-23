package com.burgan.kerem.XMLToJava.application.socket;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * Server application. This application listens port and handles request...
 * @author Kerem Akkaya
 *
 */
public class ServerApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		ServerSocket server = new ServerSocket(9091);
		System.out.println("Listening for connection on port 9091 ....");
		while (true) {
			try (Socket socket = server.accept()) {
				ServerApplication application = new ServerApplication();

				ServerThread thread = new ServerThread(application.getRequest(socket));
				thread.start();

				application.writeResponse(socket);
				
			}
		}

	}

	private String getRequest(Socket socket) {

		InputStreamReader isr;
		try {
			isr = new InputStreamReader(socket.getInputStream());

			BufferedReader reader = new BufferedReader(isr);

			String message = reader.readLine();

			String request = message.substring(message.indexOf("<?xml"));

			while (!validate(request)) {
				message = reader.readLine();
				request = request.concat(message);
			}

			// System.out.println("Request:" +request);
			return request;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private boolean validate(String xml) {
		if ("Valid".equals(validateXML(xml)))
			return true;
		return false;
	}

	public String validateXML(String xml) {

		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setNamespaceAware(true);

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
			return "Valid";
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return e.getMessage().toString();
		} catch (SAXException e) {
			return e.getMessage().toString();
		} catch (IOException e) {
			return e.getMessage().toString();
		}

	
//		// the "parse" method also validates XML, will throw an exception if misformatted
//		
//		try {
//			Source xmlFile = new StreamSource(new StringReader(xml));
//			URL schemaFile = new URL("https://www.w3.org/2001/XMLSchema.xsd");
//			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//			Schema schema = schemaFactory.newSchema(schemaFile);
//			Validator validator = schema.newValidator();
//			validator.validate(xmlFile);
//
//		} catch (SAXException ex) {
//			System.out.println(ex.getMessage());
//			return ex.getMessage().toString();
//
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//			return e.getMessage().toString();
//		}
//		return "Valid";
	}

	public void writeResponse(Socket socket) throws UnsupportedEncodingException, IOException {
		Date today = new Date();
		String httpResponse = "HTTP/1.1 200 OK".concat(System.lineSeparator()).concat(System.lineSeparator())
				.concat(today.toString());
		socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
	}

	public void writeErrorResponse(Socket socket) {

		Date today = new Date();
		String httpResponse = "HTTP/1.1 400 OK".concat(System.lineSeparator()).concat(System.lineSeparator())
				.concat(today.toString());
		try {
			socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
