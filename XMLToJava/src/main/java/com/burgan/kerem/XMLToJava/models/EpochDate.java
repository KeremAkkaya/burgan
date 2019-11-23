package com.burgan.kerem.XMLToJava.models;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

public class EpochDate {

	@JsonProperty("Epoch")
	private String epoch;

	@JsonProperty("Date")
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	private ZonedDateTime date;

	public String getEpoch() {
		return epoch;
	}

	public void setEpoch(String epoch) {
		this.epoch = epoch;
	}

	public ZonedDateTime getDate() {
		return date;
	}

	public void setDate(ZonedDateTime date) {
		this.date = date;
	}

}
