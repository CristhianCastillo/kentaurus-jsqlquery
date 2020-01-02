package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int numeroDeProceso;

	private JLabel lblEjecutando;
	private JLabel lblProcesosDescripcion;
	private JLabel lblProcesosEnCola;
	private JProgressBar barraProcesos;
	private JSeparator linea;

	public StatusPanel() {
		this.setLayout(new BorderLayout());
		this.numeroDeProceso = 0;
		lblEjecutando = new JLabel("Ejecutando: ");
		lblProcesosDescripcion = new JLabel("Procesos en cola: ");
		lblProcesosEnCola = new JLabel("0");
		barraProcesos = new JProgressBar();
		barraProcesos.setEnabled(false);
		linea = new JSeparator(JSeparator.VERTICAL);
		linea.setPreferredSize(new Dimension(1, 20));

		JPanel pnlIzquierda = new JPanel();
		pnlIzquierda.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnlIzquierda.add(lblEjecutando);
		pnlIzquierda.add(barraProcesos);
		pnlIzquierda.add(linea);
		pnlIzquierda.add(lblProcesosDescripcion);
		pnlIzquierda.add(lblProcesosEnCola);

		JPanel pnlDerecha = new JPanel();
		pnlDerecha.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnlDerecha.add(new JLabel("Copyright � 2018 Todos los derechos reservados."));

		this.add(pnlIzquierda, BorderLayout.WEST);
		this.add(pnlDerecha, BorderLayout.EAST);
	}

	public void iniciarBarraDeEstado() {
		this.barraProcesos.setIndeterminate(true);
	}

	public void detenerBarraDeEstado() {
		this.barraProcesos.setIndeterminate(false);
	}

	public void agregarProceso() {
		this.numeroDeProceso++;
		this.lblProcesosEnCola.setText(numeroDeProceso + "");
		if (!barraProcesos.isIndeterminate())
			this.iniciarBarraDeEstado();
	}

	public void eliminarProceso() {
		this.numeroDeProceso--;
		this.lblProcesosEnCola.setText(numeroDeProceso + "");
		if (numeroDeProceso <= 0)
			this.detenerBarraDeEstado();
	}
}
