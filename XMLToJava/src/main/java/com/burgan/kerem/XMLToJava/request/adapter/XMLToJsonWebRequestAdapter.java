package com.burgan.kerem.XMLToJava.request.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.burgan.kerem.XMLToJava.ApplicationConstants;
import com.burgan.kerem.XMLToJava.models.DataWrapper;
import com.burgan.kerem.XMLToJava.request.JSONRequest;
import com.burgan.kerem.XMLToJava.request.XMLRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;

public class XMLToJsonWebRequestAdapter<T> implements IWebRequestAdapter<T, JSONRequest<T>> {

	private XMLRequest<T> request;
	
	public XMLToJsonWebRequestAdapter(XMLRequest<T> request) {
		super();
		this.request = request;
	}


	@Override
	public JSONRequest<T> convert() {

		T data = request.getObject();
		DateFormat dateFormat = new SimpleDateFormat(ApplicationConstants.dateTimeFormat);
		ObjectMapper mapper = new ObjectMapper();

		// mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// StdDateFormat is ISO8601 since jackson 2.9

		mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
		mapper.setDateFormat(dateFormat);

		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		try {
			String json = mapper.writeValueAsString(new DataWrapper<T>(data));
			
			return new JSONRequest<T>(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
