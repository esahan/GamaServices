package com.natica.ge.ap;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvoiceLine implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1424497596453084276L;
	private String poNumber;
	private String lineDescription;
	private String lineType;
	private String itemCode;
	private BigDecimal amount; 
	private BigDecimal vatTaxAmount;
	private String vatTaxCode;
	private Integer defaultDistCcid;
	private String withholdingTaxCode;
	private String serialNumber;
	private String assetCategory;
	private Integer quantityInvoiced;
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getLineDescription() {
		return lineDescription;
	}
	public void setLineDescription(String lineDescription) {
		this.lineDescription = lineDescription;
	}
	public String getLineType() {
		return lineType;
	}
	public void setLineType(String lineType) {
		this.lineType = lineType;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getVatTaxAmount() {
		return vatTaxAmount;
	}
	public void setVatTaxAmount(BigDecimal vatTaxAmount) {
		this.vatTaxAmount = vatTaxAmount;
	}
	public String getVatTaxCode() {
		return vatTaxCode;
	}
	public void setVatTaxCode(String vatTaxCode) {
		this.vatTaxCode = vatTaxCode;
	}
	public Integer getDefaultDistCcid() {
		return defaultDistCcid;
	}
	public void setDefaultDistCcid(Integer defaultDistCcid) {
		this.defaultDistCcid = defaultDistCcid;
	}
	public String getWithholdingTaxCode() {
		return withholdingTaxCode;
	}
	public void setWithholdingTaxCode(String withholdingTaxCode) {
		this.withholdingTaxCode = withholdingTaxCode;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getAssetCategory() {
		return assetCategory;
	}
	public void setAssetCategory(String assetCategory) {
		this.assetCategory = assetCategory;
	}
	public Integer getQuantityInvoiced() {
		return quantityInvoiced;
	}
	public void setQuantityInvoiced(Integer quantityInvoiced) {
		this.quantityInvoiced = quantityInvoiced;
	}
	
}
