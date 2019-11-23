package com.burgan.kerem.XMLToJava.request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.burgan.kerem.XMLToJava.application.ApplicationConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class XMLRequest<T> extends AbstractRequest<T> {

	private XmlMapper xmlMapper;
	

	public XMLRequest(String request, Class<T> clazz) {
		super(request, clazz);
		this.xmlMapper = new XmlMapper();
	}

	@Override
	public T getObject() {

		DateFormat dateFormat = new SimpleDateFormat(ApplicationConstants.dateTimeFormat);
		dateFormat.setTimeZone(TimeZone.getTimeZone(ApplicationConstants.timezone));

		xmlMapper.setTimeZone(TimeZone.getTimeZone(ApplicationConstants.timezone));
		xmlMapper.setDateFormat(dateFormat);

		xmlMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		JavaTimeModule module = new JavaTimeModule();
		// module.addSerializer(ZonedDateTime.class, JSR310DateTimeSerializer.INSTANCE);
		xmlMapper.registerModule(module);

		try {

			T data = xmlMapper.readValue(request, clazz);

			return data;
		} catch (JsonMappingException ex) {

		} catch (JsonProcessingException ex) {

		}
		return null;
	}

	public Class<T> getClazz() {
		return clazz;
	}	

}
