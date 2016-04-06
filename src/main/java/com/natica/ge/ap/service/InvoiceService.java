package com.natica.ge.ap.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.natica.ge.ap.InvoiceHeader;
import com.natica.ge.exception.BusinessException;

@WebService(name="invoice")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface InvoiceService {
	@WebMethod()
	public List<InvoiceResponse> sendInvoice(@WebParam(name = "invoiceHeader") List<InvoiceHeader> invoices) throws BusinessException;
}
