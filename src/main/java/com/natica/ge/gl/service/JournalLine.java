package com.natica.ge.gl.service;

public class JournalLine {
	
	Integer codeCombinationId;
	Double creditAmount;
	Double debitAmount;
	String lineDescription;
	
	
	
	public Integer getCodeCombinationId() {
		return codeCombinationId;
	}
	public void setCodeCombinationId(Integer codeCombinationId) {
		this.codeCombinationId = codeCombinationId;
	}
	public Double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Double getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(Double debitAmount) {
		this.debitAmount = debitAmount;
	}
	public String getLineDescription() {
		return lineDescription;
	}
	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}
	
	

}
