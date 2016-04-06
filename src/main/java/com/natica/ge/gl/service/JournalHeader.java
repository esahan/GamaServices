package com.natica.ge.gl.service;

import java.util.Date;
import java.util.List;

public class JournalHeader {
	
	Date journalDate;
	String JournalNumber;
	String	JournalCategory;
	String MaximoTrxId;
	List<JournalLine> journalLines;
	
	
	public Date getJournalDate() {
		return journalDate;
	}
	public void setJournalDate(Date journalDate) {
		this.journalDate = journalDate;
	}
	public String getJournalNumber() {
		return JournalNumber;
	}
	public void setJournalNumber(String journalNumber) {
		JournalNumber = journalNumber;
	}
	public String getJournalCategory() {
		return JournalCategory;
	}
	public void setJournalCategory(String journalCategory) {
		JournalCategory = journalCategory;
	}
	public String getMaximoTrxId() {
		return MaximoTrxId;
	}
	public void setMaximoTrxId(String maximoTrxId) {
		MaximoTrxId = maximoTrxId;
	}
	public List<JournalLine> getJournalLines() {
		return journalLines;
	}
	public void setJournalLines(List<JournalLine> journalLines) {
		this.journalLines = journalLines;
	}
	
 
	
	


}
