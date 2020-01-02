package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.kentaurus.jsqlquery.controller.ControllerApp;

/**
 * Clase que representa la ventana principal de la aplicaci�n.
 * 
 * @author Cristhian Eduardo Castillo Erazo.
 *
 */
public class InterfazApp extends JFrame {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	private ControllerApp ctrl;

	private ConnectionStringPanel pnlConnectionString;
	private OptionsPanel pnlOptionsQuery;

	private QueryPanel pnlQuery;
	private ResultQueryPanel pnlResultQuery;
	private LogEventsPanel pnlLogEventos;

	private StatusPanel pnlStatus;

	public InterfazApp() {

		try {
			this.ctrl = new ControllerApp();

//			URL rutaArchivos = InterfazApp.class.getResource("/jsqlquery.png");
//			Image icono = Toolkit.getDefaultToolkit().getImage(rutaArchivos);
//			this.setIconImage(icono);
			this.setTitle("JSQLQuery V1.0");
			this.setLayout(new BorderLayout());

			this.pnlConnectionString = new ConnectionStringPanel();
			this.pnlOptionsQuery = new OptionsPanel(ctrl);

			this.pnlQuery = new QueryPanel();
			this.pnlResultQuery = new ResultQueryPanel();
			this.pnlLogEventos = new LogEventsPanel();

			this.pnlStatus = new StatusPanel();

			JPanel pnlNorte = new JPanel();
			pnlNorte.setLayout(new BorderLayout());
			pnlNorte.add(pnlConnectionString, BorderLayout.NORTH);
			pnlNorte.add(pnlOptionsQuery, BorderLayout.CENTER);

			JSplitPane splitPaneSecundario = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, pnlQuery, pnlLogEventos);
			splitPaneSecundario.setOneTouchExpandable(true);
			splitPaneSecundario.setDividerLocation(300);
			JSplitPane splitPanePrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, splitPaneSecundario,
					pnlResultQuery);

			JPanel pnlCentro = new JPanel();
			pnlCentro.setLayout(new BorderLayout());
			pnlCentro.add(splitPanePrincipal, BorderLayout.CENTER);

			this.add(pnlNorte, BorderLayout.NORTH);
			this.add(pnlCentro, BorderLayout.CENTER);
			this.add(pnlStatus, BorderLayout.SOUTH);

			this.setExtendedState(MAXIMIZED_BOTH);
			this.setMinimumSize(new Dimension(1000, 600));
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
//			this.setResizable(false);

			this.ctrl.conectar(pnlConnectionString, pnlQuery, pnlResultQuery, pnlStatus, pnlLogEventos);
			this.ctrl.actualizarVistaParametros();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "JSqlQuery", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		InterfazApp sp = new InterfazApp();
		sp.setVisible(true);
	}

}
