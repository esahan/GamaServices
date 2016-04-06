package com.natica.ge.gl.service;

public class JournalBatch {

	
	private Integer batchId;
	private String writeJournalsResult;
	private String validateJournalsResult;
	private Integer invalidJournalsCount;
	
	
	public Integer getBatchId() {
		return batchId;
	}
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}
	public String getWriteJournalsResult() {
		return writeJournalsResult;
	}
	public void setWriteJournalsResult(String writeJournalsResult) {
		this.writeJournalsResult = writeJournalsResult;
	}
	public String getValidateJournalsResult() {
		return validateJournalsResult;
	}
	public void setValidateJournalsResult(String validateJournalsResult) {
		this.validateJournalsResult = validateJournalsResult;
	}
	public Integer getInvalidJournalsCount() {
		return invalidJournalsCount;
	}
	public void setInvalidJournalsCount(Integer invalidJournalsCount) {
		this.invalidJournalsCount = invalidJournalsCount;
	}
	
	
}
