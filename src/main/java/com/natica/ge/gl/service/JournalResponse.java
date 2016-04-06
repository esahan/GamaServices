package com.natica.ge.gl.service;

import java.util.ArrayList;
import java.util.List;



public class JournalResponse {
	private String status;
	private Integer oracleHeaderId;
	private String journalNum;
	private String maximoTrxId;
	private List<JournalError> errors = new ArrayList<JournalError>();
	
	
	public List<JournalError> getErrors() {
		return errors;
	}
	public void setErrors(List<JournalError> errors) {
		this.errors = errors;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getOracleHeaderId() {
		return oracleHeaderId;
	}
	public void setOracleHeaderId(Integer oracleHeaderId) {
		this.oracleHeaderId = oracleHeaderId;
	}
	public String getJournalNum() {
		return journalNum;
	}
	public void setJournalNum(String journalNum) {
		this.journalNum = journalNum;
	}
	public String getMaximoTrxId() {
		return maximoTrxId;
	}
	public void setMaximoTrxId(String maximoTrxId) {
		this.maximoTrxId = maximoTrxId;
	}
	
	

}
