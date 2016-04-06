package com.natica.ge.log;

import java.io.ByteArrayOutputStream;

public class ServiceLog {
	private String messageId;
	private String contentType;
	private Integer contentLength;
	private String charset;
	private String endpoint;
	private String operation;
	private String requestMethod;
	private String namespace;
	private Boolean hasFault;
	
	private ByteArrayOutputStream requestXml;
	private ByteArrayOutputStream responseXml;
	
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Integer getContentLength() {
		return contentLength;
	}
	public void setContentLength(Integer contentLength) {
		this.contentLength = contentLength;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public Boolean getHasFault() {
		return hasFault;
	}
	public void setHasFault(Boolean hasFault) {
		this.hasFault = hasFault;
	}
	public ByteArrayOutputStream getRequestXml() {
		return requestXml;
	}
	public void setRequestXml(ByteArrayOutputStream requestXml) {
		this.requestXml = requestXml;
	}
	public ByteArrayOutputStream getResponseXml() {
		return responseXml;
	}
	public void setResponseXml(ByteArrayOutputStream responseXml) {
		this.responseXml = responseXml;
	}
}
