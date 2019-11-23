package com.burgan.kerem.XMLToJava.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Method {

	@JsonProperty("Name")
	private String name;
	@JsonProperty("Type")
	private String type;
	@JsonProperty("Assembly")
	private String assembly;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAssembly() {
		return assembly;
	}

	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}

}
