package com.kalkivera.apps.simpleapi.exception;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * DTO for response errors
 *
 */
public class SimpleApiErrorDTO {

	private Date timestamp;
	private String status;
	private String error;
	private List<String> messages;
	private String path;

	public SimpleApiErrorDTO(Date timestamp, String status, String error, List<String> messages, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.messages = messages;
		this.path = path;
	}

	@JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
