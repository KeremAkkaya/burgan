package com.burgan.kerem.XMLToJava.application.socket;

import java.io.FileNotFoundException;

import com.burgan.kerem.XMLToJava.file.FileMode;
import com.burgan.kerem.XMLToJava.file.MainFile;
import com.burgan.kerem.XMLToJava.logger.FileLogger;
import com.burgan.kerem.XMLToJava.logger.ILogger;
import com.burgan.kerem.XMLToJava.models.Data;
import com.burgan.kerem.XMLToJava.request.IRequest;
import com.burgan.kerem.XMLToJava.request.JSONRequest;
import com.burgan.kerem.XMLToJava.request.RequestFactory;
import com.burgan.kerem.XMLToJava.request.XMLRequest;
import com.burgan.kerem.XMLToJava.request.adapter.IWebRequestAdapter;
import com.burgan.kerem.XMLToJava.request.adapter.XMLToJsonWebRequestAdapter;
import com.burgan.kerem.XMLToJava.request.handler.IRequestHandler;
import com.burgan.kerem.XMLToJava.request.handler.XMLRequestHandler;

public class ServerThread extends Thread {

	private String request;

	public ServerThread(String request) {
		super();
		this.request = request;
	}

	private String getRequest()  {

		return request;
	}


	@Override
	public void run() {
		super.run();

		IRequest<Data> dataRequest = new RequestFactory<Data>().getRequest("xml", this.getRequest(), Data.class);

		this.handleRequest(dataRequest);
	}

	public void handleRequest(IRequest<Data> dataRequest) {

		// Chain of responsibility pattern uygulanabilir. Başka tipteki requestler ayırt
		// edilip buna göre işlem yapılabilir.
		// Şu anda uygulama sadece XMLRequest lerini işleyebilecek şekilde çalışıyor.
		IWebRequestAdapter<Data, JSONRequest<Data>> adapter = new XMLToJsonWebRequestAdapter<Data>(
				(XMLRequest<Data>) dataRequest);

		Data data = dataRequest.getObject();

		try {

			ILogger<String> logger = new FileLogger(
					new MainFile(data.getType(), data.getCreation().getDate(), FileMode.WRITABLE));

			IRequestHandler handler = new XMLRequestHandler<Data, JSONRequest<Data>>(dataRequest, adapter, logger);

			handler.handleRequest();
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		finally {
			
		}
	}
	
	

}
