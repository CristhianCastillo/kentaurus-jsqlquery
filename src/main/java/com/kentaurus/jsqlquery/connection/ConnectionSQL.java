package com.kentaurus.jsqlquery.connection;

import java.io.FileInputStream;
import java.util.Properties;

import com.kentaurus.jsqlquery.model.Encripta;
import com.kentaurus.jsqlquery.view.ConnectionStringPanel;

public class ConnectionSQL {
	public static final String ARCHIVO_CONEXION = "./data/ConexionDB.keta";
	public static final String clave = "LAS AVES VUELAN LIBREMENTE";

	private String motorBD;
	private String hostName; // 127.0.0.1
	private String portName; // 3306
	private String dataBaseName; // DAO
	private String userName; // root
	private String password; //
	private String dataBaseUrl;

	public ConnectionSQL() throws Exception {
		try {
			Encripta encripta = new Encripta(clave);
			Properties archivo = new Properties();
			try (FileInputStream datos = new FileInputStream(ARCHIVO_CONEXION)) {
				archivo.load(datos);

				String hostNameStr = archivo.getProperty("hostName");
				if (hostNameStr == null)
					throw new Exception("La propiedad 'Hostname' no se ha definido.");
				if (hostNameStr.trim().equalsIgnoreCase(""))
					throw new Exception("La propiedad 'Hostname' no puede estar vacia.");

				hostName = encripta.desencripta(hostNameStr);

				String portNameStr = archivo.getProperty("portName");
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
				this.motorBD = ConnectionStringPanel.MOTORES_DB[0];
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
		if (this.motorBD.equals(ConnectionStringPanel.MOTORES_DB[0])) {
			return dataBaseUrl = "jdbc:sqlserver://" + hostName + ":" + portName + ";databaseName=" + dataBaseName;
		} else if (this.motorBD.equals(ConnectionStringPanel.MOTORES_DB[1])) {
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
		return motorBD;
	}

	public void setMotorBD(String motorBD) {
		this.motorBD = motorBD;
	}

	@Override
	public String toString() {
		return "ConnnectionSQL{" + "dataBaseUrl=" + dataBaseUrl + '}';
	}
}
