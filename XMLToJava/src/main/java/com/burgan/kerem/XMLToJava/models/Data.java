package com.burgan.kerem.XMLToJava.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {

	@JsonProperty("Method")
	private Method method;
	@JsonProperty("Process")
	private Process process;
	@JsonProperty("Layer")
	private String layer;
	@JsonProperty("Creation")
	private EpochDate creation;
	@JsonProperty("Type")
	private String type;

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public EpochDate getCreation() {
		return creation;
	}

	public void setCreation(EpochDate creation) {
		this.creation = creation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
