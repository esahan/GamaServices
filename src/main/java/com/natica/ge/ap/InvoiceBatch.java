package com.natica.ge.ap;

public class InvoiceBatch {
	private Integer batchId;
	private String writeInvoicesResult;
	private String validateInvoicesResult;
	private Integer invalidInvoiceCount;
	public Integer getBatchId() {
		return batchId;
	}
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}
	public String getWriteInvoicesResult() {
		return writeInvoicesResult;
	}
	public void setWriteInvoicesResult(String writeInvoicesResult) {
		this.writeInvoicesResult = writeInvoicesResult;
	}
	public String getValidateInvoicesResult() {
		return validateInvoicesResult;
	}
	public void setValidateInvoicesResult(String validateInvoicesResult) {
		this.validateInvoicesResult = validateInvoicesResult;
	}
	public Integer getInvalidInvoiceCount() {
		return invalidInvoiceCount;
	}
	public void setInvalidInvoiceCount(Integer invalidInvoiceCount) {
		this.invalidInvoiceCount = invalidInvoiceCount;
	}
}
