package com.natica.ge.ap.service;

import java.util.ArrayList;
import java.util.List;

public class InvoiceResponse {
	private String status;
	private Integer oracleHeaderId;
	private String invoiceNum;
	private String maximoInvoiceNumber;
	private List<InvoiceError> errors = new ArrayList<InvoiceError>();
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public String getMaximoInvoiceNumber() {
		return maximoInvoiceNumber;
	}
	public void setMaximoInvoiceNumber(String maximoInvoiceNumber) {
		this.maximoInvoiceNumber = maximoInvoiceNumber;
	}
	public List<InvoiceError> getErrors() {
		return errors;
	}
	public void setErrors(List<InvoiceError> errors) {
		this.errors = errors;
	}
	public Integer getOracleHeaderId() {
		return oracleHeaderId;
	}
	public void setOracleHeaderId(Integer oracleHeaderId) {
		this.oracleHeaderId = oracleHeaderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 
}
