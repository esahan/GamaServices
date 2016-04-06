package com.natica.ge.db.conn;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Logger;

public class OracleConn {
	static Logger LOG = Logger.getLogger(OracleConn.class);	
	
	public static Connection getConnection() {
		
		Properties configProp = new Properties();
		InputStream propFis = null;
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			propFis = (InputStream) classloader.getResourceAsStream("config.properties");
			configProp.load(propFis);
		} catch (Exception e) {
			LOG.error("config.properties cannot be loaded.", e);
		}
		finally {
			if (propFis != null) {
				try {
					propFis.close();
				} catch (IOException e) {
					LOG.error("propFis cannot be closed.", e);
					return null;
				}
			}
		}
		
		String conString = configProp.getProperty("db.url");
		String userName = configProp.getProperty("db.username");
		String password = configProp.getProperty("db.password");
		
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
        	LOG.error("oracle.jdbc.driver.OracleDriver not found..", ex);
            return null;
        }
        
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(conString, userName, password);
        } catch (SQLException e) {
        	LOG.error("Database connection is failed.", e);
            return null;
        }

        return connection;		
	}
	
	public static OracleConnection getOracleConnection() {
		
		Properties configProp = new Properties();
		InputStream propFis = null;
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			propFis = (InputStream) classloader.getResourceAsStream("config.properties");
			configProp.load(propFis);
		} catch (Exception e) {
			LOG.error("config.properties cannot be loaded.", e);
			return null;
		}
		finally {
			if (propFis != null) {
				try {
					propFis.close();
				} catch (IOException e) {
					LOG.error("propFis cannot be closed.", e);
					return null;
				}
			}
		}
		
		String conString = configProp.getProperty("db.url");
		String userName = configProp.getProperty("db.username");
		String password = configProp.getProperty("db.password");
		
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
        	LOG.error("oracle.jdbc.driver.OracleDriver not found..", ex);
            return null;
        }
        
        OracleConnection connection = null;
        try {
            connection = (OracleConnection) DriverManager.getConnection(conString, userName, password);
        } catch (SQLException e) {
        	LOG.error("Database connection is failed.", e);
            return null;
        }

        return connection;		
	}	
}
