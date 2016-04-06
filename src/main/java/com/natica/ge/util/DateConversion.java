package com.natica.ge.util;

public class DateConversion {

/*	public static oracle.jbo.domain.Date getJboDateFromUtilDate(
			java.util.Date utilDate) {
		oracle.jbo.domain.Date jboDate = null;
		if (utilDate != null) {
			jboDate = new oracle.jbo.domain.Date(
					DateConversion.getSqlDateFromUtilDate(utilDate));
		}
		return jboDate;
	}

	public static oracle.jbo.domain.Date getJboDateFromSqlDate(
			java.sql.Date sqlDate) {
		oracle.jbo.domain.Date jboDate = null;
		if (sqlDate != null) {
			jboDate = new oracle.jbo.domain.Date(sqlDate);
		}
		return jboDate;
	}

	public static java.sql.Date getSqlDateFromJboDate(
			oracle.jbo.domain.Date jboDate) {
		return jboDate.dateValue();
	}

	public static java.util.Date getUtilDateFromJboDate(
			oracle.jbo.domain.Date jboDate) {
		java.util.Date utilDate = null;
		if (jboDate != null) {
			utilDate = new java.util.Date(jboDate.dateValue().getTime());
		}
		return utilDate;
	}*/

	public static java.sql.Date getSqlDateFromUtilDate(java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}

	public static java.util.Date getUtilDateFromSqlDate(java.sql.Date sqlDate) {
		return new java.util.Date(sqlDate.getTime());
	}
}
