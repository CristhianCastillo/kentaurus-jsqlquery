package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogEventsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int numeroLineas;
	private JTextArea txtLogEvents;

	public LogEventsPanel() {
		numeroLineas = 0;
		this.setLayout(new BorderLayout());
		txtLogEvents = new JTextArea(10, 50);
		txtLogEvents.setEditable(false);
		txtLogEvents.setBackground(Color.ORANGE);
		// txtLogEvents.setLineWrap(true);
		// txtLogEvents.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(txtLogEvents);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll, BorderLayout.CENTER);
	}

	public void agregarEvento(String descripcion) {
		try {
			if (numeroLineas == 0)
				txtLogEvents.append(descripcion);
			else
				txtLogEvents.append("\n" + descripcion);
			txtLogEvents.setCaretPosition(txtLogEvents.getDocument().getLength());
			numeroLineas++;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Agregar Evento", JOptionPane.ERROR_MESSAGE);
		}
	}

}
