package com.natica.ge.gl.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.natica.ge.exception.BusinessException;

@WebService(name="Journal")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface JournalService {
	
	
	@WebMethod
	public List<JournalResponse> SendJournal(@WebParam(name="JournalHeaders") List<JournalHeader> JournalHeaderList) throws BusinessException;

}
