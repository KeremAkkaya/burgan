package com.burgan.kerem.XMLToJava.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Process {

	@JsonProperty("Name")
	private String name;
	@JsonProperty("Id")
	private String id;
	@JsonProperty("Start")
	private EpochDate start;
	
}
