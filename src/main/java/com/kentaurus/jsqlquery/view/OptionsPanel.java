package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.kentaurus.jsqlquery.controller.ControllerApp;

public class OptionsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final String EJECUTAR_SENTENCIA_SQL = "Ejecutar sentencia sql";
	public static final String DETENER_EJECUCION_SENTENCIA_SQL = "Detener sentencia sql";
	public static final String GUARDAR_CONFIGURACION = "Guardar configuracion";

	private ControllerApp ctrl;
	private int numeroSentenciaSql;
	private int timeOut;

	private JLabel lblTiempoEjecucion;
	private JLabel lblSegundos;
	private JTextField txtTiempoEjecucion;
	private JRadioButton rbtnRetornarTablaResultado;
	private JRadioButton rbtnRetornarTextoResultado;
	private JRadioButton rbtnEjecutarSentenciaSql;
	private JButton btnEjecutar;
	private ButtonGroup grupoRadios;
	private JButton btnGuardarConfiguracion;

	public OptionsPanel(ControllerApp ctrl) {
		this.timeOut = 0;
		this.numeroSentenciaSql = 0;
		this.ctrl = ctrl;
		this.setBorder(new TitledBorder("Query"));
		this.setLayout(new BorderLayout());

		lblTiempoEjecucion = new JLabel("Tiempo M�ximo Ejecuci�n: ");
		lblSegundos = new JLabel("(segundos)");
		txtTiempoEjecucion = new JTextField(7);

		rbtnRetornarTablaResultado = new JRadioButton("Retornar Tabla del Resultado", true);
		rbtnRetornarTextoResultado = new JRadioButton("Retornar Texto del Resultado");
		rbtnEjecutarSentenciaSql = new JRadioButton("Ejecutar Sentencia SQL");

		grupoRadios = new ButtonGroup();
		grupoRadios.add(rbtnRetornarTablaResultado);
		grupoRadios.add(rbtnRetornarTextoResultado);
		grupoRadios.add(rbtnEjecutarSentenciaSql);

		btnGuardarConfiguracion = new JButton("Guardar configuraci�n");
		btnGuardarConfiguracion.setActionCommand(GUARDAR_CONFIGURACION);
		btnGuardarConfiguracion.addActionListener(this);

		JPanel pnlIzquierda = new JPanel();
		pnlIzquierda.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnlIzquierda.add(rbtnRetornarTablaResultado);
		pnlIzquierda.add(rbtnRetornarTextoResultado);
		pnlIzquierda.add(rbtnEjecutarSentenciaSql);
		pnlIzquierda.add(btnGuardarConfiguracion);

		btnEjecutar = new JButton("Ejecutar Sentencia");
		btnEjecutar.setActionCommand(EJECUTAR_SENTENCIA_SQL);
		btnEjecutar.addActionListener(this);

		JPanel pnlDerecha = new JPanel();
		pnlDerecha.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnlDerecha.add(lblTiempoEjecucion);
		pnlDerecha.add(txtTiempoEjecucion);
		pnlDerecha.add(lblSegundos);
		pnlDerecha.add(btnEjecutar);
		this.txtTiempoEjecucion.setText("30");
		this.add(pnlIzquierda, BorderLayout.WEST);
		this.add(pnlDerecha, BorderLayout.EAST);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if (comando.equals(EJECUTAR_SENTENCIA_SQL)) {
			try {
				String timeOutStr = txtTiempoEjecucion.getText().trim();
				if (timeOutStr == null || timeOutStr.equals(""))
					timeOut = 0;
				else {
					try {
						timeOut = Integer.parseInt(timeOutStr);
					} catch (Exception ex) {
						timeOut = 0;
					}
				}
				if (rbtnRetornarTablaResultado.isSelected()) {
					String numeroActual = numeroSentenciaSql++ + "";
					Runnable r = () -> {
						try {
							this.ctrl.agregarProceso(
									"Ejecutando sentencia SQL - " + numeroActual + " - " + this.obtenerFechaActual());
							int n = this.ctrl.ejecutarSentenciaTablaResultado(timeOut);
							this.ctrl.eliminarProceso("Sentencia SQL finalizada - " + numeroActual + " - "
									+ this.obtenerFechaActual() + " Total: " + n + " registros.");
						} catch (Exception ex) {
							try {
								this.ctrl.eliminarProceso("Sentencia SQL finalizada - " + numeroActual + " - "
										+ this.obtenerFechaActual() + " Error: " + ex.getMessage());
								JOptionPane.showMessageDialog(this, ex.getMessage(), "Ejecutar Sentencia SQL",
										JOptionPane.ERROR_MESSAGE);
							} catch (Exception ex1) {
								JOptionPane.showMessageDialog(this, ex1.getMessage(), "Ejecutar Sentencia SQL",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					};
					ExecutorService executor = Executors.newSingleThreadExecutor();
					Future<?> future = executor.submit(() -> {
						executor.execute(r);
					});
					while (!future.isDone()) {
					}
					executor.shutdown();
				} else if (rbtnRetornarTextoResultado.isSelected()) {
					String numeroActual = numeroSentenciaSql++ + "";
					Runnable r = () -> {
						try {
							this.ctrl.agregarProceso(
									"Ejecutando sentencia SQL - " + numeroActual + " - " + this.obtenerFechaActual());
							int n = this.ctrl.ejecutarSentenciaTextoResultado(timeOut);
							this.ctrl.eliminarProceso("Sentencia SQL finalizada - " + numeroActual + " - "
									+ this.obtenerFechaActual() + " Total: " + n + " registros.");
						} catch (Exception ex) {
							try {
								this.ctrl.eliminarProceso("Sentencia SQL finalizada - " + numeroActual + " - "
										+ this.obtenerFechaActual() + " Error: " + ex.getMessage());
								JOptionPane.showMessageDialog(this, ex.getMessage(), "Ejecutar Sentencia SQL",
										JOptionPane.ERROR_MESSAGE);
							} catch (Exception ex1) {
								JOptionPane.showMessageDialog(this, ex1.getMessage(), "Ejecutar Sentencia SQL",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					};
					ExecutorService executor = Executors.newSingleThreadExecutor();

					Future<?> future = executor.submit(() -> {
						executor.execute(r);
					});
					while (!future.isDone()) {
					}
					executor.shutdown();
				} else if (rbtnEjecutarSentenciaSql.isSelected()) {
					String numeroActual = numeroSentenciaSql++ + "";
					Runnable r = () -> {
						try {
							this.ctrl.agregarProceso(
									"Ejecutando sentencia SQL - " + numeroActual + " - " + this.obtenerFechaActual());
							int n = this.ctrl.ejecutarSentenciaSQL(timeOut);
							this.ctrl.eliminarProceso("Sentencia SQL finalizada - " + numeroActual + " - "
									+ this.obtenerFechaActual() + " Total: " + n + " registros afectados.");
						} catch (Exception ex) {
							try {
								this.ctrl.eliminarProceso("Sentencia SQL finalizada - " + numeroActual + " - "
										+ this.obtenerFechaActual() + " Error: " + ex.getMessage());
								JOptionPane.showMessageDialog(this, ex.getMessage(), "Ejecutar Sentencia SQL",
										JOptionPane.ERROR_MESSAGE);
							} catch (Exception ex1) {
								JOptionPane.showMessageDialog(this, ex1.getMessage(), "Ejecutar Sentencia SQL",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					};
					ExecutorService executor = Executors.newSingleThreadExecutor();
					Future<?> future = executor.submit(() -> {
						executor.execute(r);
					});
					while (!future.isDone()) {
					}
					executor.shutdown();
				} else {
					throw new Exception("Opcion no valida.");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Ejecutar Sentencia SQL",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (comando.equals(GUARDAR_CONFIGURACION)) {
			try {
				int confirmacion = JOptionPane.showConfirmDialog(this, "�Desea guardar la configuraci�n?",
						"Guardar configuraci�n", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (confirmacion == JOptionPane.OK_OPTION) {
					this.ctrl.guardarConfiguracion();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Guardar configuraci�n",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public String obtenerFechaActual() {
		Date fecha = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaActual = dateFormat.format(fecha);
		return fechaActual;
	}
}
