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
		this.txtQuery = new JTextArea(10, 50);
		this.txtQuery.setLineWrap(true);
		this.txtQuery.setWrapStyleWord(true);
		this.txtQuery.setFont(new Font("Arial", Font.BOLD, 12));
		JScrollPane scroll = new JScrollPane(txtQuery);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll, BorderLayout.CENTER);
	}

	public String getQuery() {
		String querySelected = txtQuery.getSelectedText();
		String query = txtQuery.getText();
		if (querySelected != null && !querySelected.trim().equals(""))
			return querySelected;
		else
			return query;
	}

}
