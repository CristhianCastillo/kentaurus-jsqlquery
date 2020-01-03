package com.kentaurus.jsqlquery.constants;

public class AppConstants {

	// Default
	public static final String TITLE_APPLICATION = "JSQLQuery V1.0.0";
	public static final String CONNECTION_FILE = "./data/properties/connection-db.keta";
	public static final String ICON_APPLICATION = "./data/images/jsqlquery.png";
	public static final String ENCRYPTION_PASSWORD = "LAS AVES VUELAN LIBREMENTE";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	// Properties
	public static final String HOSTNAME_PARAMETER = "hostName";
	public static final String PORT_NAME_PARAMETER = "portName";
	public static final String DATABASE_PARAMETER = "dataBaseName";
	public static final String USERNAME_PARAMETER = "userName";
	public static final String PASSWORD_PARAMETER = "password";

	// Messages
	public static final String ERROR_HOSTNAME_NULL = "La propiedad 'Hostname' no se ha definido.";
	public static final String ERROR_HOSTNAME_EMPTY = "La propiedad 'Hostname' no puede estar vacia.";
	public static final String ERROR_PORT_NAME_NULL = "La propiedad 'nombre puerto' no se ha definido.";
	public static final String ERROR_PORT_NAME_EMPTY = "La propiedad 'nombre puerto' no puede estar vacia.";
	public static final String ERROR_DATABASE_NULL = "No se ha definido el nombre de la base de datos.";
	public static final String ERROR_DATABASE_EMPTY = "El nombre de la base de datos no puede estar vacia.";
	public static final String ERROR_USERNAME_NULL = "No se ha definido un usuario para la conexion con la base de datos.";
	public static final String ERROR_USERNAME_EMPTY = "El nombre del usuario no puede estar vacio.";
	public static final String ERROR_PASSWORD_NULL = "No se ha definido una contrase\u00f1a para establecer la conexion.";
	public static final String ERROR_PASSWORD_EMPTY = "La contrase\u00f1a no puede estar vacia.";
	public static final String ERROR_NO_VALID_OPTION = "Opcion no valida.";
	
	// EndPoint DB
	public static final String HOSTNAME_PARAMETER_END_POINT = "%%hostName%%";
	public static final String PORT_NAME_PARAMETER_END_POINT = "%%portName%%";
	public static final String DATABASE_PARAMETER_END_POINT = "%%dataBaseName%%";
	public static final String SQL_SERVER_END_POINT = "jdbc:sqlserver://" + HOSTNAME_PARAMETER_END_POINT + ":"
			+ PORT_NAME_PARAMETER_END_POINT + ";databaseName=" + DATABASE_PARAMETER_END_POINT;
	public static final String MY_SQL_END_POINT = "jdbc:mysql://" + HOSTNAME_PARAMETER_END_POINT + ":"
			+ PORT_NAME_PARAMETER_END_POINT + "/" + DATABASE_PARAMETER_END_POINT;
	public static final String ORACLE_END_POINT = "jdbc:oracle:thin:@" + HOSTNAME_PARAMETER_END_POINT + ":"
			+ PORT_NAME_PARAMETER_END_POINT + ":" + DATABASE_PARAMETER_END_POINT;

	// Databases types
	public static final String[] DB_TYPES = { "SQL Server", "MySQL", "Oracle" };

	// Titles Panels
	public static final String PANEL_TITLE_CONNECTION = "Connection String";
	public static final String PANEL_TITLE_LOG_EVENTS = "Agregar Evento";
	public static final String PANEL_TITLE_QUERY = "Query";
	
	// Tab
	public static final String TAB_RESULT = "Resultado ";
	
	// Labels
	public static final String LABEL_SERVER = "Servidor: ";
	public static final String LABEL_DSN = "DSN: ";
	public static final String LABEL_USER = "Usuario: ";
	public static final String LABEL_PASSWORD = "Password: ";
	public static final String LABEL_PORT = "Puerto: ";
	public static final String LABEL_TYPE_DB = "Motor DB: ";
	public static final String LABEL_TIME_EXECUTION = "Tiempo M\u00e1ximo Ejecuci\u00f3n: ";
	public static final String LABEL_SECONDS = "(segundos)";
	public static final String LABEL_EXECUTION = "Ejecutando: ";
	public static final String LABEL_QUEUE = "Procesos en cola: ";
	public static final String LABEL_COPYRIGHT = "Copyright \u2117 2018 Todos los derechos reservados.";
	
	// Radios
	public static final String RADIO_TABLE_RESULT = "Retornar Tabla del Resultado";
	public static final String RADIO_TEXT_RESULT = "Retornar Texto del Resultado";
	public static final String RADIO_SENTENCE_SQL = "Ejecutar Sentencia SQL";
	
	// Buttons
	public static final String BUTTON_SAVE_CONFIGURATION = "Guardar configuraci\u00f3n";
	public static final String BUTTON_EXECUTION_SENTENCE = "Ejecutar Sentencia";
	
	// Logs
	public static final String LOG_EXECUTION_SQL_START = "Ejecutando sentencia SQL INICIANDO - %s - %s";
	public static final String LOG_EXECUTION_SQL_END = "Ejecutando sentencia SQL FINALIZADO - %s - %s Total: %s registros.";
	public static final String LOG_EXECUTION_ERROR_SQL_END = "Ejecutando sentencia SQL FINALIZADO ERROR - %s - %s Error: %s";
	
	// Executes
	public static final String ERROR_QUERY_EMPTY = "El query no puede estar vacio.";
	public static final String QUESTION_SAVE_CONFIGURATION = "¿Desea guardar la configuraci\u00f3n?";
	
	// Titles Messages
	public static final String TITLE_MESSAGE_UPDATE_PARAMETERS = "Actualizar Paranetros";
	public static final String TITLE_MESSAGE_SAVE_CONFIGURATION = "Guardar configuraci\u00f3n";
	public static final String TITLE_MESSAGE_ADD_RESULT = "Agregar Resultado";


}
