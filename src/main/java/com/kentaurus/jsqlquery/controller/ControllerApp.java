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
import com.kentaurus.jsqlquery.model.Encripta;
import com.kentaurus.jsqlquery.view.ConnectionStringPanel;
import com.kentaurus.jsqlquery.view.LogEventsPanel;
import com.kentaurus.jsqlquery.view.QueryPanel;
import com.kentaurus.jsqlquery.view.ResultQueryPanel;
import com.kentaurus.jsqlquery.view.StatusPanel;

public class ControllerApp {
	private ConnectionStringPanel pnlConnection;
	private QueryPanel pnlQuery;
	private ResultQueryPanel pnlResultadoQuery;
	private StatusPanel pnlStatus;
	private LogEventsPanel pnlEventos;

	public ControllerApp() throws Exception {
		try {
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void conectar(ConnectionStringPanel pnlConnection, QueryPanel pnlQuery, ResultQueryPanel pnlResultadoQuery,
			StatusPanel pnlStatus, LogEventsPanel pnlEventos) throws Exception {
		try {
			this.pnlConnection = pnlConnection;
			this.pnlQuery = pnlQuery;
			this.pnlResultadoQuery = pnlResultadoQuery;
			this.pnlStatus = pnlStatus;
			this.pnlEventos = pnlEventos;
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void actualizarVistaParametros() throws Exception {
		try {
			this.pnlConnection.actualizarParametros();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public int ejecutarSentenciaTablaResultado(int timeOut) throws Exception {
		Connection connection = null;
		PreparedStatement queryValidado = null;
		ResultSet resultSet = null;
		int numeroFilasAfectadas = 0;
		try {
			String query = pnlQuery.obtenerQuery();
			if (query != null && !query.trim().equals("")) {
				ConnectionSQL conexionSql = pnlConnection.obtenerCadenaSQL();
				connection = DriverManager.getConnection(conexionSql.getDataBaseUrl(), conexionSql.getUserName(),
						conexionSql.getPassword());
				queryValidado = connection.prepareStatement(query);
				queryValidado.setQueryTimeout(timeOut);
				resultSet = queryValidado.executeQuery();
				ResultSetMetaData metaDatos = resultSet.getMetaData();
				DefaultTableModel tableModel = new DefaultTableModel();
				int numeroColumnas = metaDatos.getColumnCount();
				Object[] etiquetas = new Object[numeroColumnas];
				for (int i = 0; i < numeroColumnas; i++) {
					etiquetas[i] = metaDatos.getColumnLabel(i + 1);
				}
				tableModel.setColumnIdentifiers(etiquetas);
				while (resultSet.next()) {
					Object[] fila = new Object[numeroColumnas];
					for (int i = 0; i < numeroColumnas; i++)
						fila[i] = resultSet.getObject(i + 1);
					tableModel.addRow(fila);
				}
				JTable tablaResultados = new JTable();
				tablaResultados.setColumnSelectionAllowed(false);
				tablaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tablaResultados.setFont(new Font("Arial", Font.BOLD, 12));
				tablaResultados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				tablaResultados.getTableHeader().setReorderingAllowed(false);

				JScrollPane scroll = new JScrollPane();
				scroll.setViewportView(tablaResultados);
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				tablaResultados.setModel(tableModel);
				numeroFilasAfectadas = tableModel.getRowCount();
				pnlResultadoQuery.agregarResultado(scroll);
			} else
				throw new Exception("El query no puede estar vacio.");
		} catch (Exception ex) {
			numeroFilasAfectadas = 0;
			throw new Exception(ex.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (queryValidado != null) {
				queryValidado.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return numeroFilasAfectadas;
	}

	public int ejecutarSentenciaTextoResultado(int timeOut) throws Exception {
		Connection connection = null;
		PreparedStatement queryValidado = null;
		ResultSet resultSet = null;
		int numeroFilasAfectadas = 0;
		try {
			String resultado = "";
			String query = pnlQuery.obtenerQuery();
			if (query != null && !query.trim().equals("")) {
				ConnectionSQL conexionSql = pnlConnection.obtenerCadenaSQL();
				connection = DriverManager.getConnection(conexionSql.getDataBaseUrl(), conexionSql.getUserName(),
						conexionSql.getPassword());
				queryValidado = connection.prepareStatement(query);
				queryValidado.setQueryTimeout(timeOut);
				resultSet = queryValidado.executeQuery();
				ResultSetMetaData metaDatos = resultSet.getMetaData();

				int numeroColumnas = metaDatos.getColumnCount();
				while (resultSet.next()) {
					numeroFilasAfectadas++;
					for (int i = 0; i < numeroColumnas; i++) {
						String res = resultSet.getObject(i + 1).toString();
						resultado = resultado + String.format("%-8s\t", res);
					}
					resultado += "\n";
				}
				JTextArea txtLogEvents = new JTextArea(10, 50);
				txtLogEvents.setEditable(false);
				txtLogEvents.setText(resultado);

				JScrollPane scroll = new JScrollPane();
				scroll.setViewportView(txtLogEvents);
				scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
				pnlResultadoQuery.agregarResultado(scroll);
			} else
				throw new Exception("El query no puede estar vacio.");
		} catch (Exception ex) {
			numeroFilasAfectadas = 0;
			throw new Exception(ex.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (queryValidado != null) {
				queryValidado.close();
			}
			if (resultSet != null) {
				resultSet.close();
			}
		}
		return numeroFilasAfectadas;
	}

	public int ejecutarSentenciaSQL(int timeOut) throws Exception {
		Connection connection = null;
		PreparedStatement queryValidado = null;
		int numeroFilasAfectadas = 0;
		try {
			String query = pnlQuery.obtenerQuery();
			if (query != null && !query.trim().equals("")) {
				ConnectionSQL conexionSql = pnlConnection.obtenerCadenaSQL();
				connection = DriverManager.getConnection(conexionSql.getDataBaseUrl(), conexionSql.getUserName(),
						conexionSql.getPassword());
				queryValidado = connection.prepareStatement(query);
				queryValidado.setQueryTimeout(timeOut);
				numeroFilasAfectadas = queryValidado.executeUpdate();
			} else
				throw new Exception("El query no puede estar vacio.");
		} catch (Exception ex) {
			numeroFilasAfectadas = 0;
			throw new Exception(ex.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (queryValidado != null) {
				queryValidado.close();
			}
		}
		return numeroFilasAfectadas;
	}

	public void agregarProceso(String descripcion) throws Exception {
		try {
			this.pnlEventos.agregarEvento(descripcion);
			this.pnlStatus.agregarProceso();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void eliminarProceso(String descripcion) throws Exception {
		try {
			this.pnlEventos.agregarEvento(descripcion);
			this.pnlStatus.eliminarProceso();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public void guardarConfiguracion() throws Exception {
		try {
			Encripta encripta = new Encripta(AppConstants.ENCRYPTION_PASSWORD);
			ConnectionSQL connectionSave = pnlConnection.obtenerCadenaSQL();
			Properties propiedades = new Properties();
			try (OutputStream output = new FileOutputStream(AppConstants.CONNECTION_FILE)) {
				propiedades.setProperty("hostName", encripta.encriptaCadena(connectionSave.getHostName()));
				propiedades.setProperty("portName", encripta.encriptaCadena(connectionSave.getPortName()));
				propiedades.setProperty("dataBaseName", encripta.encriptaCadena(connectionSave.getDataBaseName()));
				propiedades.setProperty("userName", encripta.encriptaCadena(connectionSave.getUserName()));
				propiedades.setProperty("password", encripta.encriptaCadena(connectionSave.getPassword()));
				propiedades.store(output, null);
			}

		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

}
