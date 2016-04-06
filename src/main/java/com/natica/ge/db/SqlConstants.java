package com.natica.ge.db;

public class SqlConstants {
	/*LOG SQL Scripts */
	public static String LOG_TABLE 				= "XXNTC_MAXIMO_WS_LOG";
	public static String LOG_REQUEST_SELECT 	= "SELECT REQUEST_XML_CLOB FROM " + LOG_TABLE + " WHERE MESSAGE_ID = ? FOR UPDATE";
	public static String LOG_RESPONSE_SELECT 	= "SELECT RESPONSE_XML_CLOB FROM " + LOG_TABLE + " WHERE MESSAGE_ID = ? FOR UPDATE";
	public static String LOG_INSERT_DML			= "INSERT INTO " + LOG_TABLE + " (MESSAGE_ID, ENDPOINT, CONTENT_TYPE, CONTENT_LENGTH, CHARSET, OPERATION, REQUEST_METHOD, NAMESPACE, REQUEST_XML_CLOB, RESPONSE_XML_CLOB, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE) "
												+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, empty_clob(), empty_clob(), SYSDATE, NULL, SYSDATE)";
	public static String LOG_UPDATE_DML			= "UPDATE " + LOG_TABLE + " SET HAS_ERROR = ? WHERE MESSAGE_ID = ?";
		
	
	/* INV SQL Scripts */
	public static String INV_RESULT_SELECT		= "SELECT mr.header_id, mr.status, mh.invoice_num, mh.maximo_invoice_number, mr.result_id "
												+ "FROM xxntc_ap_maximo_inv_header mh, xxntc_ap_maximo_inv_result mr "
												+ "WHERE mh.header_id = mr.header_id AND mh.batch_id = ?";
	public static String INV_RESULT_DET_SELECT  = "SELECT md.error_code, md.error_message, md.result_id "
												+ "FROM xxntc_ap_maximo_inv_result mr, xxntc_ap_maximo_inv_res_det md "
												+ "WHERE mr.result_id = md.result_id AND mr.batch_id = ?";
	public static String JE_RESULT_SELECT		= "SELECT HD.HEADER_ID,RS.STATUS,HD.JOURNAL_NUMBER,HD.MAXIMOTRXID,RS.RESULT_ID "
												+ "FROM XXNTC_GL_MAXIMO_JE_HEADER HD, XXNTC_GL_MAXIMO_JE_RESULT RS"    
												+ "WHERE RS.HEADER_ID=HD.HEADER_ID AND HD.BATCH_ID=? ";
	public static String JE_RESULT_DET_SELECT	= "SELECT MD.ERROR_CODE, MD.ERROR_MESSAGE, MD.RESULT_ID "
                                                + "FROM XXNTC_GL_MAXIMO_JE_RESULT MR, XXNTC_GL_MAXIMO_JE_RES_DET MD "
                                                + "WHERE MR.RESULT_ID = MD.RESULT_ID AND MR.BATCH_ID = ? ";
	
	
	/*PlSql Procedure Call Scripts */
	public static String WRITE_INVOICES_PRC		= "{call xxntc_ap_maximo_int_pkg.write_invoices(?, ?, ?)}";
	public static String VALIDATE_INVOICES_PRC  = "{call xxntc_ap_maximo_int_pkg.validate_invoices(?, ?, ?)}";
	public static String WRITE_JOURNALS_PRC 	= "{call xxntc_gl_maximo_je_pkg.write_journals(?, ?, ?)}";
	public static String VALIDATE_JOURNALS_PRC  = "{call xxntc_gl_maximo_je_pkg.validate_journals(?, ?, ?)}";
	
	
	
}
