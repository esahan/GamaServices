package com.natica.ge.gl.service;

import java.util.ArrayList;
import java.util.List;

import javax.jws.HandlerChain;
import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.natica.ge.db.DbOperations;
import com.natica.ge.exception.BusinessException;
import com.natica.ge.exception.BusinessExceptionDetail;
import com.natica.ge.util.IntConstants;




@WebService(serviceName="Journal", endpointInterface="com.natica.ge.gl.service.JournalService")
@HandlerChain(file="/handler-chain.xml")
public class JournalServiceImpl implements JournalService {
	
	
    static Logger LOG=Logger.getLogger(JournalServiceImpl.class);
	
	@Override
	public List<JournalResponse> SendJournal(List<JournalHeader> JournalHeaderList) throws BusinessException {
		
		LOG.info("Database işlemlerinden önce");
		
		JournalBatch journalBatch = new JournalBatch();
		
		DbOperations.writeJournals(JournalHeaderList, journalBatch);
		
		if (IntConstants.FAILURE_STATUS.equals(journalBatch.getWriteJournalsResult())) {
			BusinessExceptionDetail bed = new BusinessExceptionDetail();
			bed.setFaultCode("EBS-0004");
			bed.setFaultMessage("Exception occurred during journal insert");
			throw new BusinessException("Fault Message", bed);
		}
		
		DbOperations.validateJournal(journalBatch.getBatchId(), journalBatch);
		
		if (IntConstants.FAILURE_STATUS.equals(journalBatch.getValidateJournalsResult())) {
			BusinessExceptionDetail bed = new BusinessExceptionDetail();
			bed.setFaultCode("EBS-0005");
			bed.setFaultMessage("Exception occurred during journal validation.");
			throw new BusinessException("Fault Message", bed);
		}	
		
		List<JournalResponse> jResponseList=new ArrayList<JournalResponse>();
		
		String retFromResult=DbOperations.populateJournalResults(journalBatch.getBatchId(), journalBatch.getInvalidJournalsCount(), jResponseList);
		
		if (IntConstants.FAILURE_STATUS.equals(retFromResult)) {
			BusinessExceptionDetail bed = new BusinessExceptionDetail();
			bed.setFaultCode("EBS-0006");
			bed.setFaultMessage("Exception occurred during processing.");
			throw new BusinessException("Fault Message", bed);
		}
		
		return jResponseList;
	}

}
