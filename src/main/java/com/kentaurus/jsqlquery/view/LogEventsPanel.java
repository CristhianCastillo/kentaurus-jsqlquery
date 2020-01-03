package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.kentaurus.jsqlquery.constants.AppConstants;

public class LogEventsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int numberLines;
	private JTextArea txtLogEvents;

	public LogEventsPanel() {
		numberLines = 0;
		this.setLayout(new BorderLayout());
		this.txtLogEvents = new JTextArea(10, 50);
		this.txtLogEvents.setEditable(false);
		this.txtLogEvents.setBackground(Color.ORANGE);
		JScrollPane scroll = new JScrollPane(this.txtLogEvents);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll, BorderLayout.CENTER);
	}

	public void addEvent(String description) {
		try {
			if (this.numberLines == 0)
				this.txtLogEvents.append(description);
			else
				this.txtLogEvents.append("\n" + description);
			this.txtLogEvents.setCaretPosition(this.txtLogEvents.getDocument().getLength());
			this.numberLines++;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), AppConstants.PANEL_TITLE_LOG_EVENTS,
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
