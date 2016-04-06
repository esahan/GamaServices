package com.natica.ge.log;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

public class NaticaLoggingInInterceptor extends AbstractSoapInterceptor {

	public NaticaLoggingInInterceptor() {
		super(Phase.READ);
		
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		System.out.println("aaa");
		/*message.getContextualPropertyKeys();
		((java.util.TreeMap)message.getContextualProperty("org.apache.cxf.message.Message.PROTOCOL_HEADERS")).get("Content-Length")
		message.getContextualProperty("org.apache.cxf.request.url")
		message.getContextualProperty("org.apache.cxf.message.Message.ENCODING")
		message.getContextualProperty("org.apache.cxf.binding.soap.SoapVersion")
		message.getContextualProperty("org.apache.cxf.request.method") //post
		message.getContextualProperty("Content-Type")
		message.getContextualProperty("org.apache.cxf.interceptor.LoggingMessage.ID")
		message.getExchange().getOutMessage()*/
	}


}
