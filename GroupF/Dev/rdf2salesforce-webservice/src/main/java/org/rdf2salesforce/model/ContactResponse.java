package org.rdf2salesforce.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactResponse {

	private Integer totalSize;
	private Boolean done;
	private List<Contact> records;

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	public List<Contact> getRecords() {
		return records;
	}

	public void setRecords(List<Contact> records) {
		this.records = records;
	}

}
