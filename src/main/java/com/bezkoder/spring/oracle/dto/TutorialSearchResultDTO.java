package com.bezkoder.spring.oracle.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bezkoder.spring.oracle.util.DBTable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TutorialSearchResultDTO {
	public TutorialSearchResultDTO() {
		super();
	}
	@DBTable(columnName ="ID")
	
	private long id;

	@DBTable(columnName ="TITLE")
	private String title;
	public TutorialSearchResultDTO(long id, String title, String code) {
		super();
		this.id = id;
		this.title = title;
		this.code = code;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@DBTable(columnName ="CODE")
	private String code;
	
	
}
