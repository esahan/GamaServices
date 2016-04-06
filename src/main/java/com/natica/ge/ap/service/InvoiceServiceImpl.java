package com.natica.ge.ap.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.natica.ge.ap.InvoiceBatch;
import com.natica.ge.ap.InvoiceHeader;
import com.natica.ge.db.DbOperations;
import com.natica.ge.exception.BusinessException;
import com.natica.ge.exception.BusinessExceptionDetail;
import com.natica.ge.util.IntConstants;


//@org.apache.cxf.interceptor.InInterceptors (interceptors = {"com.natica.log.NaticaLoggingInInterceptor"})
@WebService(serviceName="invoice", endpointInterface="com.natica.ge.ap.service.InvoiceService")
@HandlerChain(file="/handler-chain.xml")
public class InvoiceServiceImpl implements InvoiceService {
	static Logger LOG = Logger.getLogger(InvoiceServiceImpl.class);
	
	@Override
	public List<InvoiceResponse> sendInvoice(List<InvoiceHeader> invoices) throws BusinessException {
		LOG.debug("Before DbOperations.writeInvoiceRecors call");
		InvoiceBatch invBatch = new InvoiceBatch();
		DbOperations.writeInvoices(invoices, invBatch);
		
		LOG.debug("After DbOperations.writeInvoiceRecors call");
		LOG.debug("xxntc_ap_maximo_int_pkg.write_invoices result:" + invBatch.getWriteInvoicesResult());
		LOG.debug("Batch Id:" + invBatch.getBatchId());
		
		if (IntConstants.FAILURE_STATUS.equals(invBatch.getWriteInvoicesResult())) {
			BusinessExceptionDetail bed = new BusinessExceptionDetail();
			bed.setFaultCode("EBS-0001");
			bed.setFaultMessage("Exception occurred during invoice insert");
			throw new BusinessException("Fault Message", bed);
		}
			
		Integer batchId = invBatch.getBatchId();
		LOG.debug("Before DbOperations.validateInvoices call");
		DbOperations.validateInvoices(batchId, invBatch);
		LOG.debug("After DbOperations.validateInvoices call");
		LOG.debug("xxntc_ap_maximo_int_pkg.validate_invoices result:" + invBatch.getValidateInvoicesResult());
		LOG.debug("Invalid Invoice Count:" + invBatch.getInvalidInvoiceCount());
		if (IntConstants.FAILURE_STATUS.equals(invBatch.getValidateInvoicesResult())) {
			BusinessExceptionDetail bed = new BusinessExceptionDetail();
			bed.setFaultCode("EBS-0002");
			bed.setFaultMessage("Exception occurred during validation.");
			throw new BusinessException("Fault Message", bed);
		}		
		
		List<InvoiceResponse> responses = new ArrayList<InvoiceResponse>();
		LOG.debug("Before DbOperations.populateResults call");
		String populateResultsResult = DbOperations.populateResults(batchId, invBatch.getInvalidInvoiceCount(), responses);
		LOG.debug("After DbOperations.populateResults call");
		LOG.debug("populateResultsResult:" + populateResultsResult);
		if (IntConstants.FAILURE_STATUS.equals(populateResultsResult)) {
			BusinessExceptionDetail bed = new BusinessExceptionDetail();
			bed.setFaultCode("EBS-0003");
			bed.setFaultMessage("Exception occurred during processing.");
			throw new BusinessException("Fault Message", bed);
		}
		
		return responses;
	}

}
