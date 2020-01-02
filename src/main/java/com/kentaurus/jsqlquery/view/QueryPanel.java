package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class QueryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextArea txtQuery;

	public QueryPanel() {
		this.setLayout(new BorderLayout());
		txtQuery = new JTextArea(10, 50);
		txtQuery.setLineWrap(true);
		txtQuery.setWrapStyleWord(true);
		txtQuery.setFont(new Font("Arial", Font.BOLD, 12));
		JScrollPane scroll = new JScrollPane(txtQuery);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll, BorderLayout.CENTER);
	}

	public String obtenerQuery() {
		String querySeleccionado = txtQuery.getSelectedText();
		String query = txtQuery.getText();
		if (querySeleccionado != null && !querySeleccionado.trim().equals(""))
			return querySeleccionado;
		else
			return query;
	}

}
