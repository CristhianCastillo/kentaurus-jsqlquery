package com.kentaurus.jsqlquery.controller;

import java.awt.Font;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.kentaurus.jsqlquery.connection.ConnectionSQL;
import com.kentaurus.jsqlquery.constants.AppConstants;
import com.kentaurus.jsqlquery.model.Encrypt;
import com.kentaurus.jsqlquery.view.ConnectionStringPanel;
import com.kentaurus.jsqlquery.view.LogEventsPanel;
import com.kentaurus.jsqlquery.view.QueryPanel;
import com.kentaurus.jsqlquery.view.ResultQueryPanel;
import com.kentaurus.jsqlquery.view.StatusPanel;

public class ControllerApp {
	private ConnectionStringPanel pnlConnection;
	private QueryPanel pnlQuery;
	private ResultQueryPanel pnlResultQuery;
	private StatusPanel pnlStatus;
	private LogEventsPanel pnlEvents;

	public ControllerApp() throws Exception {
	}

	public void conect(ConnectionStringPanel pnlConnection, QueryPanel pnlQuery, ResultQueryPanel pnlResultQuery,
			StatusPanel pnlStatus, LogEventsPanel pnlEvents) throws Exception {
		try {
			this.pnlConnection = pnlConnection;
			this.pnlQuery = pnlQuery;
			this.pnlResultQuery = pnlResultQuery;
			this.pnlStatus = pnlStatus;
			this.pnlEvents = pnlEvents;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void updateViewParameters() throws Exception {
		try {
			this.pnlConnection.updateParameters();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public int executeJudgmentTableResult(int timeOut) throws Exception {
		Connection connection = null;
		PreparedStatement queryValidate = null;
		ResultSet resultSet = null;
		int numberRowsAfected = 0;
		try {
			String query = pnlQuery.getQuery();
			if (query != null && !query.trim().equals("")) {
				ConnectionSQL connectionSql = pnlConnection.getConnectionSQL();
				connection = DriverManager.getConnection(connectionSql.getDataBaseUrl(), connectionSql.getUserName(),
						connectionSql.getPassword());
				queryValidate = connection.prepareStatement(query);
				queryValidate.setQueryTimeout(timeOut);
				resultSet = queryValidate.executeQuery();
				ResultSetMetaData metaData = resultSet.getMetaData();
				DefaultTableModel tableModel = new DefaultTableModel();
				int numberColumns = metaData.getColumnCount();
				Object[] labels = new Object[numberColumns];
				for (int i = 0; i < numberColumns; i++) {
					labels[i] = metaData.getColumnLabel(i + 1);
				}
				tableModel.setColumnIdentifiers(labels);
				while (resultSet.next()) {
					Object[] row = new Object[numberColumns];
					for (int i = 0; i < numberColumns; i++)
						row[i] = resultSet.getObject(i + 1);
					tableModel.addRow(row);
				}
				JTable tableResults = new JTable();
				tableResults.setColumnSelectionAllowed(false);
				tableResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableResults.setFont(new Font("Arial", Font.BOLD, 12));
				tableResults.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				tableResults.getTableHeader().setReorderingAllowed(false);

				JScrollPane scroll = new JScrollPane();
				scroll.setViewportView(tableResults);
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				tableResults.setModel(tableModel);
				numberRowsAfected = tableModel.getRowCount();
				pnlResultQuery.addResult(scroll);
			} else
				throw new Exception(AppConstants.ERROR_QUERY_EMPTY);
		} catch (Exception ex) {
			numberRowsAfected = 0;
			throw new Exception(ex.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (queryValidate != null) {
				queryValidate.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return numberRowsAfected;
	}

	public int executeSentenceTextResult(int timeOut) throws Exception {
		Connection connection = null;
		PreparedStatement queryValidate = null;
		ResultSet resultSet = null;
		int numberRowsAfected = 0;
		try {
			String result = "";
			String query = pnlQuery.getQuery();
			if (query != null && !query.trim().equals("")) {
				ConnectionSQL connectionSql = pnlConnection.getConnectionSQL();
				connection = DriverManager.getConnection(connectionSql.getDataBaseUrl(), connectionSql.getUserName(),
						connectionSql.getPassword());
				queryValidate = connection.prepareStatement(query);
				queryValidate.setQueryTimeout(timeOut);
				resultSet = queryValidate.executeQuery();
				ResultSetMetaData metaData = resultSet.getMetaData();

				int numberColumns = metaData.getColumnCount();
				while (resultSet.next()) {
					numberRowsAfected++;
					for (int i = 0; i < numberColumns; i++) {
						String res = resultSet.getObject(i + 1).toString();
						result = result + String.format("%-8s\t", res);
					}
					result += "\n";
				}
				JTextArea txtLogEvents = new JTextArea(10, 50);
				txtLogEvents.setEditable(false);
				txtLogEvents.setText(result);

				JScrollPane scroll = new JScrollPane();
				scroll.setViewportView(txtLogEvents);
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				pnlResultQuery.addResult(scroll);
			} else
				throw new Exception(AppConstants.ERROR_QUERY_EMPTY);
		} catch (Exception ex) {
			numberRowsAfected = 0;
			throw new Exception(ex.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (queryValidate != null) {
				queryValidate.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return numberRowsAfected;
	}

	public int executeSQLstatement(int timeOut) throws Exception {
		Connection connection = null;
		PreparedStatement queryValidate = null;
		int numberRowsAfected = 0;
		try {
			String query = pnlQuery.getQuery();
			if (query != null && !query.trim().equals("")) {
				ConnectionSQL connectionSql = pnlConnection.getConnectionSQL();
				connection = DriverManager.getConnection(connectionSql.getDataBaseUrl(), connectionSql.getUserName(),
						connectionSql.getPassword());
				queryValidate = connection.prepareStatement(query);
				queryValidate.setQueryTimeout(timeOut);
				numberRowsAfected = queryValidate.executeUpdate();
			} else
				throw new Exception(AppConstants.ERROR_QUERY_EMPTY);
		} catch (Exception ex) {
			numberRowsAfected = 0;
			throw new Exception(ex.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (queryValidate != null) {
				queryValidate.close();
			}
		}
		return numberRowsAfected;
	}

	public void addProcess(String description) throws Exception {
		try {
			this.pnlEvents.addEvent(description);
			this.pnlStatus.addProcess();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void deleteProcess(String descripcion) throws Exception {
		try {
			this.pnlEvents.addEvent(descripcion);
			this.pnlStatus.deleteProcess();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void saveSettings() throws Exception {
		try {
			Encrypt encrypt = new Encrypt(AppConstants.ENCRYPTION_PASSWORD);
			ConnectionSQL connectionSave = pnlConnection.getConnectionSQL();
			Properties properties = new Properties();
			try (OutputStream output = new FileOutputStream(AppConstants.CONNECTION_FILE)) {
				properties.setProperty(AppConstants.HOSTNAME_PARAMETER, encrypt.encryptString(connectionSave.getHostName()));
				properties.setProperty(AppConstants.PORT_NAME_PARAMETER, encrypt.encryptString(connectionSave.getPortName()));
				properties.setProperty(AppConstants.DATABASE_PARAMETER, encrypt.encryptString(connectionSave.getDataBaseName()));
				properties.setProperty(AppConstants.USERNAME_PARAMETER, encrypt.encryptString(connectionSave.getUserName()));
				properties.setProperty(AppConstants.PASSWORD_PARAMETER, encrypt.encryptString(connectionSave.getPassword()));
				properties.store(output, null);
			}

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

}
