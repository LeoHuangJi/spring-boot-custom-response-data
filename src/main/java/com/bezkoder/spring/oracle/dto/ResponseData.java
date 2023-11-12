package com.bezkoder.spring.oracle.dto;

import java.io.Serializable;
import java.sql.Date;

import org.hibernate.type.SerializableType;
import org.springframework.data.annotation.Transient;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseData implements Serializable {

	private long code;
	private boolean status;
	
	private String message;
	private Object data;
	@JsonIgnore
	private HttpStatus httpStatus;

	
	public ResponseData(HttpStatus httpStatus, boolean status, String message, Object data) {
		super();
		this.code = httpStatus.value();
		this.status = status;
		this.message = message;		
		this.data = data;
		this.httpStatus= httpStatus;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
