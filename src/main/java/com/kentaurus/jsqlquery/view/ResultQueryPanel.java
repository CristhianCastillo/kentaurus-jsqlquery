package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ResultQueryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int numeroResultado;
	private JTabbedPane tabs;

	public ResultQueryPanel() {
		this.numeroResultado = 0;
		this.setLayout(new BorderLayout());
		this.tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		this.add(tabs, BorderLayout.CENTER);
	}

	public void agregarResultado(JComponent componente) {
		try {
			if (componente != null) {
				numeroResultado++;
				tabs.addTab("Resultado " + numeroResultado, null, componente, "Resultado " + numeroResultado);
				int count = tabs.getTabCount();
				tabs.setSelectedIndex(count - 1);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Agregar Resultado", JOptionPane.ERROR_MESSAGE);
		}
	}
}
