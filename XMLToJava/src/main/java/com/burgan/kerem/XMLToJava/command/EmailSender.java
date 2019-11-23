package com.burgan.kerem.XMLToJava.command;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmailSender extends AbstractTextMessageSender {

	private List<String> toList;
	private String from;
	private String subject;
	private List<File> attachments;

	public EmailSender(String message, String from, String to, String subject) {
		this(message, from, Arrays.asList(message), subject, new ArrayList<>());
	}

	public EmailSender(String message, String from, List<String> toList, String subject) {
		this(message, from, toList, subject, new ArrayList<>());
	}

	public EmailSender(String message, String from, List<String> toList, String subject, List<File> attachments) {
		super(message);
		this.toList = toList;
		this.from = from;
		this.subject = subject;
		this.attachments = attachments;
	}

	@Override
	public void execute() {
		System.out.println(String.format("Email has been sent:\n From: %s To: %s Subject: %s  Attachments Count: %s",
				from, String.join(",", toList), subject, getMessage(), attachments.size()));
	}

}
