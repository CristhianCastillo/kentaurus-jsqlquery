package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.kentaurus.jsqlquery.constants.AppConstants;

public class ResultQueryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int numberResult;
	private JTabbedPane tabs;

	public ResultQueryPanel() {
		this.numberResult = 0;
		this.setLayout(new BorderLayout());
		this.tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		this.add(this.tabs, BorderLayout.CENTER);
	}

	public void addResult(JComponent component) {
		try {
			if (component != null) {
				this.numberResult++;
				this.tabs.addTab(AppConstants.TAB_RESULT + this.numberResult, null, component, AppConstants.TAB_RESULT + this.numberResult);
				int count = tabs.getTabCount();
				this.tabs.setSelectedIndex(count - 1);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), AppConstants.TITLE_MESSAGE_ADD_RESULT, JOptionPane.ERROR_MESSAGE);
		}
	}
}
