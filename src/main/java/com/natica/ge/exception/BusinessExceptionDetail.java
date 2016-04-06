package com.natica.ge.exception;

import java.io.Serializable;

public class BusinessExceptionDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1749715419266777228L;
	private String faultCode;
	private String faultMessage;

	public BusinessExceptionDetail() {
	}

	public String getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}

	public String getFaultMessage() {
		return faultMessage;
	}

	public void setFaultMessage(String faultMessage) {
		this.faultMessage = faultMessage;
	}

}
