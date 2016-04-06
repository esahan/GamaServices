package com.natica.ge.ap;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="InvoiceHeader")
public class InvoiceHeader implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3679264081733538342L;
	private Integer vendorSiteId;
	private Date invoiceDate;
	private String invoiceNum;
	private String currencyCode;
	private BigDecimal invoiceAmount;
	private Integer termsId;
	private String maximoInvoiceNumber;
	private String invoiceType;
	private List<InvoiceLine> lines;
	public Integer getVendorSiteId() {
		return vendorSiteId;
	}
	public void setVendorSiteId(Integer vendorSiteId) {
		this.vendorSiteId = vendorSiteId;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public Integer getTermsId() {
		return termsId;
	}
	public void setTermsId(Integer termsId) {
		this.termsId = termsId;
	}
	public String getMaximoInvoiceNumber() {
		return maximoInvoiceNumber;
	}
	public void setMaximoInvoiceNumber(String maximoInvoiceNumber) {
		this.maximoInvoiceNumber = maximoInvoiceNumber;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	@XmlElement(name = "invoiceLine")
	public List<InvoiceLine> getLines() {
		return lines;
	}
	public void setLines(List<InvoiceLine> lines) {
		this.lines = lines;
	}
}
