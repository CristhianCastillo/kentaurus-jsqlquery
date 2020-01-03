package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.kentaurus.jsqlquery.constants.AppConstants;
import com.kentaurus.jsqlquery.controller.ControllerApp;

public class InterfazApp extends JFrame {

	private static final long serialVersionUID = 1L;

	private ControllerApp ctrl;
	private ConnectionStringPanel pnlConnectionString;
	private OptionsPanel pnlOptionsQuery;
	private QueryPanel pnlQuery;
	private ResultQueryPanel pnlResultQuery;
	private LogEventsPanel pnlLogEvents;
	private StatusPanel pnlStatus;

	public InterfazApp() {

		try {
			this.ctrl = new ControllerApp();

			Image icon = Toolkit.getDefaultToolkit().getImage(AppConstants.ICON_APPLICATION);
			this.setIconImage(icon);
			this.setTitle(AppConstants.TITLE_APPLICATION);
			this.setLayout(new BorderLayout());

			this.pnlConnectionString = new ConnectionStringPanel();
			this.pnlOptionsQuery = new OptionsPanel(ctrl);

			this.pnlQuery = new QueryPanel();
			this.pnlResultQuery = new ResultQueryPanel();
			this.pnlLogEvents = new LogEventsPanel();

			this.pnlStatus = new StatusPanel();

			JPanel pnlNorth = new JPanel();
			pnlNorth.setLayout(new BorderLayout());
			pnlNorth.add(pnlConnectionString, BorderLayout.NORTH);
			pnlNorth.add(pnlOptionsQuery, BorderLayout.CENTER);

			JSplitPane splitPaneSecondary = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, pnlQuery, pnlLogEvents);
			splitPaneSecondary.setOneTouchExpandable(true);
			splitPaneSecondary.setDividerLocation(300);
			JSplitPane splitPanePrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, splitPaneSecondary,
					pnlResultQuery);

			JPanel pnlCenter = new JPanel();
			pnlCenter.setLayout(new BorderLayout());
			pnlCenter.add(splitPanePrincipal, BorderLayout.CENTER);

			this.add(pnlNorth, BorderLayout.NORTH);
			this.add(pnlCenter, BorderLayout.CENTER);
			this.add(pnlStatus, BorderLayout.SOUTH);

			this.setExtendedState(MAXIMIZED_BOTH);
			this.setMinimumSize(new Dimension(1000, 600));
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			this.ctrl.conect(pnlConnectionString, pnlQuery, pnlResultQuery, pnlStatus, pnlLogEvents);
			this.ctrl.updateViewParameters();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), AppConstants.TITLE_APPLICATION,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		InterfazApp sp = new InterfazApp();
		sp.setVisible(true);
	}

}
