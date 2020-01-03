package com.kentaurus.jsqlquery.connection;

import java.io.FileInputStream;
import java.util.Properties;

import com.kentaurus.jsqlquery.constants.AppConstants;
import com.kentaurus.jsqlquery.model.Encrypt;

public class ConnectionSQL {

	private String dbType;
	private String hostName;
	private String portName;
	private String dataBaseName;
	private String userName;
	private String password;
	private String dataBaseUrl;

	public ConnectionSQL() throws Exception {
		try {
			Encrypt encrypt = new Encrypt(AppConstants.ENCRYPTION_PASSWORD);
			Properties file = new Properties();
			try (FileInputStream fileInput = new FileInputStream(AppConstants.CONNECTION_FILE)) {
				file.load(fileInput);

				String hostNameStr = file.getProperty(AppConstants.HOSTNAME_PARAMETER);
				if (hostNameStr == null)
					throw new Exception(AppConstants.ERROR_HOSTNAME_NULL);
				if (hostNameStr.trim().equalsIgnoreCase(""))
					throw new Exception(AppConstants.ERROR_HOSTNAME_EMPTY);

				hostName = encrypt.decryptedString(hostNameStr);

				String portNameStr = file.getProperty(AppConstants.PORT_NAME_PARAMETER);
				if (portNameStr == null)
					throw new Exception(AppConstants.ERROR_PORT_NAME_NULL);
				if (portNameStr.trim().equalsIgnoreCase(""))
					throw new Exception(AppConstants.ERROR_PORT_NAME_EMPTY);

				portName = encrypt.decryptedString(portNameStr);

				String dataBaseNameStr = file.getProperty(AppConstants.DATABASE_PARAMETER);
				if (dataBaseNameStr == null)
					throw new Exception(AppConstants.ERROR_DATABASE_NULL);
				if (dataBaseNameStr.trim().equalsIgnoreCase(""))
					throw new Exception(AppConstants.ERROR_DATABASE_EMPTY);

				dataBaseName = encrypt.decryptedString(dataBaseNameStr);

				String userNameStr = file.getProperty(AppConstants.USERNAME_PARAMETER);
				if (userNameStr == null)
					throw new Exception(AppConstants.ERROR_USERNAME_NULL);
				if (userNameStr.trim().equalsIgnoreCase(""))
					throw new Exception(AppConstants.ERROR_USERNAME_EMPTY);

				userName = encrypt.decryptedString(userNameStr);

				String passwordStr = file.getProperty(AppConstants.PASSWORD_PARAMETER);
				if (passwordStr == null)
					throw new Exception(AppConstants.ERROR_PASSWORD_NULL);
				if (passwordStr.trim().equalsIgnoreCase(""))
					throw new Exception(AppConstants.ERROR_PASSWORD_EMPTY);

				this.password = encrypt.decryptedString(passwordStr);
				this.dbType = AppConstants.DB_TYPES[0];
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDataBaseUrl() {
		String dataBaseUrlTepm = "";
		if (this.dbType.equals(AppConstants.DB_TYPES[0])) {
			dataBaseUrlTepm = AppConstants.SQL_SERVER_END_POINT;
		} else if (this.dbType.equals(AppConstants.DB_TYPES[1])) {
			dataBaseUrlTepm = AppConstants.MY_SQL_END_POINT;
		} else {
			dataBaseUrlTepm = AppConstants.ORACLE_END_POINT;
		}
		return dataBaseUrlTepm.replace(AppConstants.HOSTNAME_PARAMETER_END_POINT, this.hostName)
				.replace(AppConstants.PORT_NAME_PARAMETER_END_POINT, this.portName)
				.replace(AppConstants.DATABASE_PARAMETER_END_POINT, this.dataBaseName);
	}

	public void setDataBaseUrl(String dataBaseUrl) {
		this.dataBaseUrl = dataBaseUrl;
	}

	public String getMotorBD() {
		return dbType;
	}

	public void setMotorBD(String motorBD) {
		this.dbType = motorBD;
	}

	@Override
	public String toString() {
		return "ConnnectionSQL{" + "dataBaseUrl=" + dataBaseUrl + '}';
	}
}
