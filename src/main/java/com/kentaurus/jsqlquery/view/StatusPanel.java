package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

import com.kentaurus.jsqlquery.constants.AppConstants;

public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int processNumber;

	private JLabel lblExecuting;
	private JLabel lblProcessesDescription;
	private JLabel lblProcessesQueue;
	private JProgressBar processBar;
	private JSeparator line;

	public StatusPanel() {
		this.setLayout(new BorderLayout());
		this.processNumber = 0;
		this.lblExecuting = new JLabel(AppConstants.LABEL_EXECUTION);
		this.lblProcessesDescription = new JLabel(AppConstants.LABEL_QUEUE);
		this.lblProcessesQueue = new JLabel("0");
		this.processBar = new JProgressBar();
		this.processBar.setEnabled(false);
		this.line = new JSeparator(JSeparator.VERTICAL);
		this.line.setPreferredSize(new Dimension(1, 20));

		JPanel pnlLeft = new JPanel();
		pnlLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnlLeft.add(this.lblExecuting);
		pnlLeft.add(this.processBar);
		pnlLeft.add(this.line);
		pnlLeft.add(this.lblProcessesDescription);
		pnlLeft.add(this.lblProcessesQueue);

		JPanel pnlRight = new JPanel();
		pnlRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnlRight.add(new JLabel(AppConstants.LABEL_COPYRIGHT));

		this.add(pnlLeft, BorderLayout.WEST);
		this.add(pnlRight, BorderLayout.EAST);
	}

	public void startStateBar() {
		this.processBar.setIndeterminate(true);
	}

	public void stopStateBar() {
		this.processBar.setIndeterminate(false);
	}

	public void addProcess() {
		this.processNumber++;
		this.lblProcessesQueue.setText(processNumber + "");
		if (!processBar.isIndeterminate())
			this.startStateBar();
	}

	public void deleteProcess() {
		this.processNumber--;
		this.lblProcessesQueue.setText(processNumber + "");
		if (processNumber <= 0)
			this.stopStateBar();
	}
}
