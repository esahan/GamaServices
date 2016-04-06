package com.natica.ge.db;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.CLOB;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

import org.apache.log4j.Logger;

import com.natica.ge.ap.InvoiceHeader;
import com.natica.ge.ap.InvoiceLine;
import com.natica.ge.ap.InvoiceBatch;
import com.natica.ge.ap.service.InvoiceError;
import com.natica.ge.ap.service.InvoiceResponse;
import com.natica.ge.db.conn.OracleConn;
import com.natica.ge.gl.service.JournalBatch;
import com.natica.ge.gl.service.JournalError;
import com.natica.ge.gl.service.JournalHeader;
import com.natica.ge.gl.service.JournalLine;
import com.natica.ge.gl.service.JournalResponse;
import com.natica.ge.log.ServiceLog;
import com.natica.ge.util.DateConversion;
import com.natica.ge.util.IntConstants;

public class DbOperations {
	static Logger LOG = Logger.getLogger(DbOperations.class);
	
	public static void writeRequestLog(ServiceLog logRecord) {		
        OracleConnection c = OracleConn.getOracleConnection();
		OraclePreparedStatement psInsert = null;
		OraclePreparedStatement psSelect = null;
		OracleResultSet rsSelect = null;
		CLOB requestXml = null;
		try {
			c.setAutoCommit(false);
			if (c != null) {
				String insertSql = SqlConstants.LOG_INSERT_DML;
				
				psInsert = (OraclePreparedStatement) c.prepareStatement(insertSql);
			
				psInsert.setString(1, logRecord.getMessageId());
				psInsert.setString(2, logRecord.getEndpoint());
				psInsert.setString(3, logRecord.getContentType());
				psInsert.setInt(4, logRecord.getContentLength().intValue());
				psInsert.setString(5, logRecord.getCharset());
				psInsert.setString(6, logRecord.getOperation());
				psInsert.setString(7, logRecord.getRequestMethod());
				psInsert.setString(8, logRecord.getNamespace());
				psInsert.executeUpdate();
				
				String selectSql = SqlConstants.LOG_REQUEST_SELECT;
				psSelect = (OraclePreparedStatement) c.prepareStatement(selectSql);
				psSelect.setString(1, logRecord.getMessageId());
				rsSelect = (OracleResultSet) psSelect.executeQuery();
				rsSelect.next();
				requestXml = rsSelect.getCLOB(1);
				String requestXmlStr = logRecord.getRequestXml().toString("UTF-8");
				
				requestXml.setString(1, requestXmlStr);
				c.commit();
			}
			
		} catch (SQLException e) {
			LOG.error("Error during SQLException", e);
		} catch (UnsupportedEncodingException e) {
			LOG.error("Error during UnsupportedEncodingException", e);
		}
		finally {
			try {
				if (rsSelect != null)
					rsSelect.close();
				
				if (psInsert != null)
					psInsert.close();
				
				if (psSelect != null)
					psSelect.close();
				
				if (c != null && !c.isClosed())
					c.close();
				
			} catch (SQLException e) {
				LOG.error("Error during SQLException", e);
			}
		}
	}
	
	public static void writeResponseLog(ServiceLog logRecord) {
        OracleConnection c = OracleConn.getOracleConnection();
		OraclePreparedStatement psInsert = null;
		OraclePreparedStatement psSelect = null;
		OraclePreparedStatement psUpdate = null;
		OracleResultSet rsSelect = null;
		CLOB responseXml = null;
		try {
			c.setAutoCommit(false);
			if (c != null) {

				String selectSql = SqlConstants.LOG_RESPONSE_SELECT;
				psSelect = (OraclePreparedStatement) c.prepareStatement(selectSql);
				psSelect.setString(1, logRecord.getMessageId());
				rsSelect = (OracleResultSet) psSelect.executeQuery();
				rsSelect.next();
				responseXml = rsSelect.getCLOB(1);
				String responseXmlStr = logRecord.getResponseXml().toString("UTF-8");
				
				responseXml.setString(1, responseXmlStr);

				String updateSql = SqlConstants.LOG_UPDATE_DML;
				psUpdate = (OraclePreparedStatement) c.prepareStatement(updateSql);
				psUpdate.setString(1, logRecord.getHasFault().booleanValue() ? "Y" : "N");
				psUpdate.setString(2, logRecord.getMessageId());
				psUpdate.executeUpdate();
				c.commit();
			}
			
		} catch (SQLException e) {
			LOG.error("Error during SQLException", e);
		} catch (UnsupportedEncodingException e) {
			LOG.error("Error during UnsupportedEncodingException", e);
		}
		finally {
			try {
				if (rsSelect != null)
					rsSelect.close();
				
				if (psInsert != null)
					psInsert.close();
				
				if (psSelect != null)
					psSelect.close();
				
				if (c != null && !c.isClosed())
					c.close();
				
			} catch (SQLException e) {
				LOG.error("Error during SQLException", e);
			}
		}
	}
	
	
	public static void writeJournals(List<JournalHeader> journals, JournalBatch journalBatch){
		
	OracleConnection con=OracleConn.getOracleConnection();
		
	OracleCallableStatement writeJournalsCS=null;
	
	String writeJournalsResult;
	Integer batchID=null;
	
	try {
	    StructDescriptor headerStructDescriptor=StructDescriptor.createDescriptor("xxntc_gl_maximo_je_h_obj", con);
	    StructDescriptor lineStructDescriptor=StructDescriptor.createDescriptor("xxntc_gl_maximo_je_l_obj", con);
	    
	    ArrayDescriptor headerArrayDescriptor = ArrayDescriptor.createDescriptor("xxntc_gl_maximo_je_h_obj_tbl", con);
		ArrayDescriptor lineArrayDescriptor = ArrayDescriptor.createDescriptor("xxntc_gl_maximo_je_l_obj_tbl", con);
	    
		int headerCounter = 0;
		
		STRUCT[] headerStructs = new STRUCT[journals.size()];
		
		Iterator<JournalHeader> headerIterator = journals.iterator();
		
		while(headerIterator.hasNext()){
			   JournalHeader header=headerIterator.next();
			
			STRUCT[] lineStructs=new STRUCT[header.getJournalLines().size()];
			int lineCount=0;
			Iterator<JournalLine> lineIterator=header.getJournalLines().iterator();
			
			while (lineIterator.hasNext())
			{
				
				JournalLine line=lineIterator.next();
				
				Object[] lineObjects=new Object[4];
				lineObjects[0]=line.getCodeCombinationId();
				lineObjects[1]=line.getCreditAmount();
				lineObjects[2]=line.getDebitAmount();
				lineObjects[3]=line.getLineDescription();
				
				STRUCT lineStruct=new STRUCT(lineStructDescriptor,con,lineObjects);
				lineStructs[lineCount]=lineStruct;
				lineCount=lineCount+1;
				//STRUCT(StructDescriptor type, java.sql.Connection conn, java.lang.Object[] attributes		
				
			}
			
			Object[] headerObjects=new Object[4];
			headerObjects[0]=header.getJournalDate();
			headerObjects[1]=header.getJournalNumber();
			headerObjects[2]=header.getJournalCategory();
			headerObjects[3]=header.getMaximoTrxId();
			headerObjects[4]=  new ARRAY(lineArrayDescriptor,con,lineStructs);
			
			STRUCT headerStruct=new STRUCT(headerStructDescriptor,con,headerObjects);
			headerStructs[headerCounter] = headerStruct;
			headerCounter=headerCounter+1;	
		} 
		
		ARRAY headerStructsArray = new ARRAY(headerArrayDescriptor, con, headerStructs);
		
		writeJournalsCS=(OracleCallableStatement)con.prepareCall(SqlConstants.WRITE_JOURNALS_PRC);
		writeJournalsCS.setARRAY(1, headerStructsArray);
		writeJournalsCS.registerOutParameter(2, Types.INTEGER );
		writeJournalsCS.registerOutParameter(3, Types.VARCHAR);
		
		writeJournalsCS.execute();
				
		batchID=writeJournalsCS.getInt(2);
		writeJournalsResult=writeJournalsCS.getString(3);
		
		
	}
	catch (SQLException e) {
		LOG.error("Error during SQLException", e);
		writeJournalsResult = IntConstants.FAILURE_STATUS;
	}
	finally {
		try {
			if (writeJournalsCS != null)
				writeJournalsCS.close();
			
			if (con != null && !con.isClosed())
				con.close();
			
		} catch (SQLException e) {
			LOG.error("Error during SQLException", e);
		}
	}
	journalBatch.setBatchId(batchID);
	journalBatch.setWriteJournalsResult(writeJournalsResult);
		
	}
	
	public static void writeInvoices(List<InvoiceHeader> invoices, InvoiceBatch invBatch) {
		OracleConnection c = OracleConn.getOracleConnection();
		OracleCallableStatement writeInvoicesCs = null;
		String writeInvoicesPrcResult = null;
		Integer batchId = null;
		try {
			//c.setAutoCommit(false);
			StructDescriptor headerStructDescriptor = StructDescriptor.createDescriptor("XXNTC_AP_MAXIMO_INV_H_OBJ", c);
			StructDescriptor lineStructDescriptor = StructDescriptor.createDescriptor("XXNTC_AP_MAXIMO_INV_L_OBJ", c);

			ArrayDescriptor headerArrayDescriptor = ArrayDescriptor.createDescriptor("XXNTC_AP_MAXIMO_INV_H_OBJ_TBL", c);
			ArrayDescriptor lineArrayDescriptor = ArrayDescriptor.createDescriptor("XXNTC_AP_MAXIMO_INV_L_OBJ_TBL", c);
			
			
			
			int headerCounter = 0;
			STRUCT[] headerStructs = new STRUCT[invoices.size()];
			
			Iterator<InvoiceHeader> headerIterator = invoices.iterator();
			while (headerIterator.hasNext()) {
				InvoiceHeader header = headerIterator.next();
				
				//satirlari isleyelim
				int lineCounter = 0;
				STRUCT[] lineStructs = new STRUCT[header.getLines().size()];
				Iterator<InvoiceLine> lineIterator = header.getLines().iterator();
				while (lineIterator.hasNext()) {
					InvoiceLine line = lineIterator.next();
					Object[] lineValues = new Object[12];
					lineValues[0] = line.getLineType();
					lineValues[1] = line.getLineDescription();
					lineValues[2] = line.getItemCode();
					lineValues[3] = line.getAmount();
					lineValues[4] = line.getVatTaxAmount();
					lineValues[5] = line.getVatTaxCode();
					lineValues[6] = line.getWithholdingTaxCode();
					lineValues[7] = line.getDefaultDistCcid();
					lineValues[8] = line.getPoNumber();
					lineValues[9] = line.getSerialNumber();
					lineValues[10]= line.getAssetCategory();
					lineValues[11]= line.getQuantityInvoiced();
					STRUCT lineStruct = new STRUCT(lineStructDescriptor, c, lineValues);
					lineStructs[lineCounter++] = lineStruct;
				}
				
				Object[] headerValues = new Object[9];
				headerValues[0] = header.getVendorSiteId();
				headerValues[1] = DateConversion.getSqlDateFromUtilDate(header.getInvoiceDate());
				headerValues[2] = header.getInvoiceNum();
				headerValues[3] = header.getCurrencyCode();
				headerValues[4] = header.getInvoiceAmount();
				headerValues[5] = header.getTermsId();
				headerValues[6] = header.getMaximoInvoiceNumber();
				headerValues[7] = header.getInvoiceType();
				headerValues[8] = new ARRAY(lineArrayDescriptor, c, lineStructs);
				STRUCT headerStruct = new STRUCT(headerStructDescriptor, c, headerValues);
				headerStructs[headerCounter++] = headerStruct;
			}
			
			ARRAY headerStructsArray = new ARRAY(headerArrayDescriptor, c, headerStructs);
			
			writeInvoicesCs = (OracleCallableStatement) c.prepareCall(SqlConstants.WRITE_INVOICES_PRC);
			writeInvoicesCs.registerOutParameter(2, Types.INTEGER);
			writeInvoicesCs.registerOutParameter(3, Types.VARCHAR);
			writeInvoicesCs.setARRAY(1, headerStructsArray);
			writeInvoicesCs.execute();
			batchId = writeInvoicesCs.getInt(2);
			writeInvoicesPrcResult = writeInvoicesCs.getString(3);
			LOG.debug("writeInvoicesPrcResult:"+writeInvoicesPrcResult);
			LOG.debug("batchId:"+batchId);
			
		} catch (SQLException e) {
			LOG.error("Error during SQLException", e);
			writeInvoicesPrcResult = IntConstants.FAILURE_STATUS;
		}
		finally {
			try {
				if (writeInvoicesCs != null)
					writeInvoicesCs.close();
				
				if (c != null && !c.isClosed())
					c.close();
				
			} catch (SQLException e) {
				LOG.error("Error during SQLException", e);
			}
		}
		invBatch.setBatchId(batchId);
		invBatch.setWriteInvoicesResult(writeInvoicesPrcResult);		
	}
	
	public static void validateJournal(Integer batchId ,JournalBatch journalBatch){
		
		OracleConnection con=OracleConn.getOracleConnection();
		OracleCallableStatement validateJournalsCs=null;
		String validateJournalsResult = null;
		Integer invalidJournalsCount = null;
		
		try{
			validateJournalsCs=(OracleCallableStatement)con.prepareCall(SqlConstants.VALIDATE_JOURNALS_PRC);
			validateJournalsCs.setInt(1, batchId.intValue());
			
			validateJournalsCs.registerOutParameter(2, Types.VARCHAR);
			validateJournalsCs.registerOutParameter(3, Types.INTEGER);
			validateJournalsCs.execute();
			
			validateJournalsResult= validateJournalsCs.getString(2);
			invalidJournalsCount=validateJournalsCs.getInt(3);
			
		}
		catch (SQLException e) {
			LOG.error("Error during SQLException", e);
			validateJournalsResult = IntConstants.FAILURE_STATUS;
		}
		finally {
			try {
				if (validateJournalsCs != null)
					validateJournalsCs.close();
				
				if (con != null && !con.isClosed())
					con.close();
				
			} catch (SQLException e) {
				LOG.error("Error during SQLException", e);
			}
		}
		
		journalBatch.setInvalidJournalsCount(invalidJournalsCount);
		journalBatch.setValidateJournalsResult(validateJournalsResult);
		
	}
	
	public static void validateInvoices(Integer batchId, InvoiceBatch invBatch) {
		OracleConnection c = OracleConn.getOracleConnection();
		OracleCallableStatement validateInvoicesCs = null;
		String validateInvoicesResult = null;
		Integer invalidInvoiceCount = null;
		
		try {
			validateInvoicesCs = (OracleCallableStatement) c.prepareCall(SqlConstants.VALIDATE_INVOICES_PRC);
			validateInvoicesCs.registerOutParameter(2, Types.VARCHAR);
			validateInvoicesCs.registerOutParameter(3, Types.INTEGER);
			validateInvoicesCs.setInt(1, batchId.intValue());
			validateInvoicesCs.execute();
			validateInvoicesResult = validateInvoicesCs.getString(2);
			invalidInvoiceCount = validateInvoicesCs.getInt(3);
		} catch (SQLException e) {
			LOG.error("Error during SQLException", e);
			validateInvoicesResult = IntConstants.FAILURE_STATUS;
		}
		finally {
			try {
				if (validateInvoicesCs != null)
					validateInvoicesCs.close();
				
				if (c != null && !c.isClosed())
					c.close();
				
			} catch (SQLException e) {
				LOG.error("Error during SQLException", e);
			}
		}
		
		invBatch.setValidateInvoicesResult(validateInvoicesResult);
		invBatch.setInvalidInvoiceCount(invalidInvoiceCount);		
	}
	
	public static String populateJournalResults(Integer batchId, Integer invalidJournalCount, List<JournalResponse> responses){
		String populateJournalResults=null;
		OracleConnection con=OracleConn.getOracleConnection();
		OraclePreparedStatement populateResultsPS=null;
		OracleResultSet oracleResultSet=null;
		Map<Integer, List<JournalError>> errorsMap=Collections.emptyMap();
		
		if(invalidJournalCount>0){
			
			errorsMap=getJournalErrorMessages(batchId);
		}
		
		try{
			
			populateResultsPS=(OraclePreparedStatement)con.prepareStatement(SqlConstants.JE_RESULT_SELECT);
			populateResultsPS.setInt(1, batchId.intValue());
			oracleResultSet=(OracleResultSet)populateResultsPS.executeQuery();
			while(oracleResultSet.next())
			{
			Integer oracleHeaderId=	oracleResultSet.getInt(1); //oracle header id
			String rsStatus=	oracleResultSet.getString(2); //  rs status
			String journalNum=	oracleResultSet.getString(3); // journal num
			String maximoId=	oracleResultSet.getString(4); //MAXIMOTRXID
			Integer resultId=	oracleResultSet.getInt(5); // result id 
			
			JournalResponse journalResponse= new JournalResponse();
			journalResponse.setOracleHeaderId(oracleHeaderId);
			journalResponse.setJournalNum(journalNum);
			journalResponse.setStatus(rsStatus);
			journalResponse.setMaximoTrxId(maximoId);
			journalResponse.setErrors(errorsMap.get(resultId));
			
			}
	
		}
		catch (SQLException e) {
			LOG.error("Error during SQLException", e);
			populateJournalResults = IntConstants.FAILURE_STATUS;
		}
		finally {
			try {
				if (populateResultsPS != null)
					populateResultsPS.close();
				
				if (populateResultsPS != null)
					populateResultsPS.close();
				
				if (con != null && !con.isClosed())
					con.close();
				
			} catch (SQLException e) {
				LOG.error("Error during SQLException", e);
			}
		
		
	}
		populateJournalResults = IntConstants.SUCCESS_STATUS;		
		return populateJournalResults;
	
	}
	
	public static String populateResults (Integer batchId, Integer invalidInvoiceCount, List<InvoiceResponse> responses) {
		String populateResultsResult = null;
        OracleConnection c = OracleConn.getOracleConnection();
		OraclePreparedStatement psSelect = null;		
		OracleResultSet rsSelect = null;		
		Map<Integer, List<InvoiceError>> errorsMap = Collections.emptyMap();
		
		if (invalidInvoiceCount > 0)
			errorsMap = getErrorMessages(batchId);
		
		try {
			psSelect = (OraclePreparedStatement) c.prepareStatement(SqlConstants.INV_RESULT_SELECT);
			psSelect.setInt(1, batchId.intValue());
			rsSelect = (OracleResultSet) psSelect.executeQuery();
			while (rsSelect.next()) {
				Integer oracleHeaderId = rsSelect.getInt(1);
				String status = rsSelect.getString(2);
				String invoiceNum = rsSelect.getString(3);
				String maximoInvoiceNumber = rsSelect.getString(4);
				Integer resultId = rsSelect.getInt(5);
				InvoiceResponse resp = new InvoiceResponse();
				resp.setInvoiceNum(invoiceNum);
				resp.setMaximoInvoiceNumber(maximoInvoiceNumber);
				resp.setOracleHeaderId(oracleHeaderId);
				resp.setStatus(status);
				resp.setErrors(errorsMap.get(resultId));
				responses.add(resp);
			}
		} catch (SQLException e) {
			LOG.error("Error during SQLException", e);
			populateResultsResult = IntConstants.FAILURE_STATUS;
		}
		finally {
			try {
				if (psSelect != null)
					psSelect.close();
				
				if (rsSelect != null)
					rsSelect.close();
				
				if (c != null && !c.isClosed())
					c.close();
				
			} catch (SQLException e) {
				LOG.error("Error during SQLException", e);
			}
		}		
		populateResultsResult = IntConstants.SUCCESS_STATUS;		
		return populateResultsResult;
	}
	
	
	private static Map<Integer, List<JournalError>> getJournalErrorMessages(Integer batchId){
		Map<Integer, List<JournalError>> errorsMap = new HashMap<Integer, List<JournalError>>();
        OracleConnection c = OracleConn.getOracleConnection();
		OraclePreparedStatement psSelect = null;		
		OracleResultSet rsSelect = null;
		try {
			psSelect = (OraclePreparedStatement) c.prepareStatement(SqlConstants.JE_RESULT_DET_SELECT);
			psSelect.setInt(1, batchId.intValue());
			rsSelect = (OracleResultSet) psSelect.executeQuery();
			while (rsSelect.next()) {
				String errorCode = rsSelect.getString(1);
				String errorMessage = rsSelect.getString(2);
				Integer resultId = rsSelect.getInt(3);
				JournalError error = new JournalError();
				error.setErrorCode(errorCode);
				error.setErrorMessage(errorMessage);
				if (!errorsMap.containsKey(resultId))
					errorsMap.put(resultId, new ArrayList<JournalError>());
				((ArrayList<JournalError>)errorsMap.get(resultId)).add(error);
			}
		} catch (SQLException e) {
			LOG.error("Error during SQLException", e);
		}
		finally {
			try {
				if (psSelect != null)
					psSelect.close();
				
				if (rsSelect != null)
					rsSelect.close();
				
				if (c != null && !c.isClosed())
					c.close();
				
			} catch (SQLException e) {
				LOG.error("Error during SQLException", e);
			}
		}
		
	
		return errorsMap;
		
	}
	
	private static Map<Integer, List<InvoiceError>> getErrorMessages(Integer batchId) {
		Map<Integer, List<InvoiceError>> errorsMap = new HashMap<Integer, List<InvoiceError>>();
        OracleConnection c = OracleConn.getOracleConnection();
		OraclePreparedStatement psSelect = null;		
		OracleResultSet rsSelect = null;
		
		try {
			psSelect = (OraclePreparedStatement) c.prepareStatement(SqlConstants.INV_RESULT_DET_SELECT);
			psSelect.setInt(1, batchId.intValue());
			rsSelect = (OracleResultSet) psSelect.executeQuery();
			while (rsSelect.next()) {
				String errorCode = rsSelect.getString(1);
				String errorMessage = rsSelect.getString(2);
				Integer resultId = rsSelect.getInt(3);
				InvoiceError error = new InvoiceError();
				error.setErrorCode(errorCode);
				error.setErrorMessage(errorMessage);
				if (!errorsMap.containsKey(resultId))
					errorsMap.put(resultId, new ArrayList<InvoiceError>());
				((ArrayList<InvoiceError>)errorsMap.get(resultId)).add(error);
			}
		} catch (SQLException e) {
			LOG.error("Error during SQLException", e);
		}
		finally {
			try {
				if (psSelect != null)
					psSelect.close();
				
				if (rsSelect != null)
					rsSelect.close();
				
				if (c != null && !c.isClosed())
					c.close();
				
			} catch (SQLException e) {
				LOG.error("Error during SQLException", e);
			}
		}
		
	
		return errorsMap;
	}
	
}
