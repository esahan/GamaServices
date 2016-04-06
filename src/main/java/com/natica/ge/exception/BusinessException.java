package com.natica.ge.exception;

import java.io.Serializable;

public class BusinessException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BusinessExceptionDetail faultDetail;

	public BusinessException(BusinessExceptionDetail faultDetail) {
		this.faultDetail = faultDetail;
	}

	public BusinessException(String message, BusinessExceptionDetail faultDetail) {
		super(message);
		this.faultDetail = faultDetail;
	}

	public BusinessExceptionDetail getFaultDetail() {
		return faultDetail;
	}
}
