package com.natica.ge.handler;

import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.UUID;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import com.natica.ge.db.DbOperations;
import com.natica.ge.log.ServiceLog;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		logToSystemOut(context);
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		logToSystemOut(context);
		return true;
	}

	private void logToSystemOut(SOAPMessageContext smc) {
		Boolean outboundProperty = (Boolean)smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		try {
			if (!outboundProperty.booleanValue()) {
				
				SOAPMessage message = smc.getMessage();
				//System.out.println("Incoming message:");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				message.writeTo(stream);
				
				//System.out.println(stream.toString());
				//System.out.println("=====================================");
				
				String endpoint = (String)smc.get("org.apache.cxf.request.url");
				String[] contentTypeArray = message.getMimeHeaders().getHeader("Content-Type"); 
	            String contentType = contentTypeArray[0];
	            String[] contentLengthArray= message.getMimeHeaders().getHeader("Content-Length");
	            String contentLength = contentLengthArray[0];
	            String charset = (String)smc.get("org.apache.cxf.message.Message.ENCODING");
	            QName values = (QName)smc.get("javax.xml.ws.wsdl.operation");
	            String namespace = values.getNamespaceURI();
	            String operation = values.getLocalPart();
	            String requstMethod = (String)smc.get("org.apache.cxf.request.method");
	            String messageId = UUID.randomUUID().toString();
	            smc.put("MessageId", messageId);
	            
	            ServiceLog log = new ServiceLog();
	            log.setCharset(charset);
	            log.setContentLength(Integer.parseInt(contentLength));
	            log.setContentType(contentType);
	            log.setEndpoint(endpoint);
	            log.setMessageId(messageId);
	            log.setNamespace(namespace);
	            log.setOperation(operation);
	            log.setRequestMethod(requstMethod);
	            log.setRequestXml(stream);
	            DbOperations.writeRequestLog(log);
	            				
			} else {
				SOAPMessage message = smc.getMessage();
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				message.writeTo(stream);
				ServiceLog log = new ServiceLog();
				log.setMessageId((String)smc.get("MessageId"));
				
				
				if (!message.getSOAPPart().getEnvelope().getBody().hasFault()) {
					log.setHasFault(Boolean.FALSE);
				} else {
					log.setHasFault(Boolean.TRUE);
				}
				log.setResponseXml(stream);
				DbOperations.writeResponseLog(log);

			}
		} catch (Exception e) {
			System.out.println("Exception in handler: " + e);
		}
	}
	
	
}