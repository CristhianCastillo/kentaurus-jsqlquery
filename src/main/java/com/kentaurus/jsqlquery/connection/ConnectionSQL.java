package com.kentaurus.jsqlquery.connection;

import java.io.FileInputStream;
import java.util.Properties;

import com.kentaurus.jsqlquery.constants.AppConstants;
import com.kentaurus.jsqlquery.model.Encripta;
import com.kentaurus.jsqlquery.view.ConnectionStringPanel;

public class ConnectionSQL {

	private String dbType;
	private String hostName; // 127.0.0.1
	private String portName; // 3306
	private String dataBaseName; // DAO
	private String userName; // root
	private String password; //
	private String dataBaseUrl;

	public ConnectionSQL() throws Exception {
		try {
			Encripta encripta = new Encripta(AppConstants.ENCRYPTION_PASSWORD);
			Properties archivo = new Properties();
			try (FileInputStream datos = new FileInputStream(AppConstants.CONNECTION_FILE)) {
				archivo.load(datos);

				String hostNameStr = archivo.getProperty(AppConstants.HOSTNAME_PARAMETER);
				if (hostNameStr == null)
					throw new Exception(AppConstants.ERROR_HOSTNAME_NULL);
				if (hostNameStr.trim().equalsIgnoreCase(""))
					throw new Exception(AppConstants.ERROR_HOSTNAME_EMPTY);

				hostName = encripta.desencripta(hostNameStr);

				String portNameStr = archivo.getProperty(AppConstants.PORT_NAME_PARAMETER);
				if (portNameStr == null)
					throw new Exception("La propiedad 'nombre puerto' no se ha definido.");
				if (portNameStr.trim().equalsIgnoreCase(""))
					throw new Exception("La propiedad 'nombre puerto' no puede estar vacia.");

				portName = encripta.desencripta(portNameStr);

				String dataBaseNameStr = archivo.getProperty("dataBaseName");
				if (dataBaseNameStr == null)
					throw new Exception("No se ha definido el nombre de la base de datos.");
				if (dataBaseNameStr.trim().equalsIgnoreCase(""))
					throw new Exception("El nombre de la base de datos no puede estar vacia.");

				dataBaseName = encripta.desencripta(dataBaseNameStr);

				String userNameStr = archivo.getProperty("userName");
				if (userNameStr == null)
					throw new Exception("No se ha definido un usuario para la conexion con la base de datos.");
				if (userNameStr.trim().equalsIgnoreCase(""))
					throw new Exception("El nombre del usuario no puede estar vacio.");

				userName = encripta.desencripta(userNameStr);

				String passwordStr = archivo.getProperty("password");
				if (passwordStr == null)
					throw new Exception("No se ha definido una contrase�a para establecer la conexion.");
				if (passwordStr.trim().equalsIgnoreCase(""))
					throw new Exception("La contrase�a no puede estar vacia.");

				this.password = encripta.desencripta(passwordStr);
				this.dbType = ConnectionStringPanel.MOTORES_DB[0];
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
		if (this.dbType.equals(ConnectionStringPanel.MOTORES_DB[0])) {
			return dataBaseUrl = "jdbc:sqlserver://" + hostName + ":" + portName + ";databaseName=" + dataBaseName;
		} else if (this.dbType.equals(ConnectionStringPanel.MOTORES_DB[1])) {
			return dataBaseUrl = "jdbc:mysql://" + hostName + ":" + portName + "/" + dataBaseName
					+ "?verifyServerCertificate=true&useSSL=true&requireSSL=true";
		} else {
			return dataBaseUrl = "jdbc:oracle:thin:@" + hostName + ":" + portName + ":" + dataBaseName;
		}
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
