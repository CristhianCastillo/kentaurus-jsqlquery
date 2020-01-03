package com.kentaurus.jsqlquery.view;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.kentaurus.jsqlquery.connection.ConnectionSQL;
import com.kentaurus.jsqlquery.constants.AppConstants;

public class ConnectionStringPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private ConnectionSQL connectionSQL;
	private JLabel lblServer;
	private JLabel lblDNS;
	private JLabel lblUser;
	private JLabel lblPassword;
	private JLabel lblPort;
	private JLabel lblDBTypes;
	private JTextField txtServer;
	private JTextField txtDNS;
	private JTextField txtUser;
	private JPasswordField txtPassword;
	private JTextField txtPort;
	private JComboBox<String> cmbDBTypes;

	public ConnectionStringPanel() {
		this.setBorder(new TitledBorder(AppConstants.PANEL_TITLE_CONNECTION));
		GroupLayout group = new GroupLayout(this);
		this.setLayout(group);

		this.lblServer = new JLabel(AppConstants.LABEL_SERVER);
		this.lblDNS = new JLabel(AppConstants.LABEL_DSN);
		this.lblUser = new JLabel(AppConstants.LABEL_USER);
		this.lblPassword = new JLabel(AppConstants.LABEL_PASSWORD);
		this.lblPort = new JLabel(AppConstants.LABEL_PORT);
		this.lblDBTypes = new JLabel(AppConstants.LABEL_TYPE_DB);

		this.txtServer = new JTextField();
		this.txtDNS = new JTextField();
		this.txtUser = new JTextField();
		this.txtPassword = new JPasswordField();
		this.txtPort = new JTextField();
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>(AppConstants.DB_TYPES);
		this.cmbDBTypes = new JComboBox<>(modelo);

		group.setAutoCreateContainerGaps(true);
		group.setAutoCreateGaps(true);

		group.setVerticalGroup(group.createSequentialGroup()
				.addGroup(group.createParallelGroup().addComponent(this.lblServer).addComponent(this.txtServer))
				.addGroup(group.createParallelGroup().addComponent(this.lblDNS).addComponent(this.txtDNS))
				.addGroup(group.createParallelGroup().addComponent(this.lblUser).addComponent(this.txtUser))
				.addGroup(group.createParallelGroup().addComponent(this.lblPassword).addComponent(this.txtPassword))
				.addGroup(group.createParallelGroup().addComponent(this.lblPort).addComponent(this.txtPort))
				.addGroup(group.createParallelGroup().addComponent(this.lblDBTypes).addComponent(this.cmbDBTypes)));

		group.setHorizontalGroup(group.createSequentialGroup()
				.addGroup(group.createParallelGroup().addComponent(this.lblServer).addComponent(this.lblDNS)
						.addComponent(this.lblUser).addComponent(this.lblPassword).addComponent(this.lblPort)
						.addComponent(this.lblDBTypes))

				.addGroup(group.createParallelGroup().addComponent(this.txtServer).addComponent(this.txtDNS)
						.addComponent(this.txtUser).addComponent(this.txtPassword).addComponent(this.txtPort)
						.addComponent(this.cmbDBTypes)));
	}

	public void updateParameters() {
		try {
			this.connectionSQL = new ConnectionSQL();
			this.txtServer.setText(this.connectionSQL.getHostName());
			this.txtDNS.setText(this.connectionSQL.getDataBaseName());
			this.txtUser.setText(this.connectionSQL.getUserName());
			this.txtPassword.setText(this.connectionSQL.getPassword());
			this.txtPort.setText(this.connectionSQL.getPortName());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), AppConstants.TITLE_MESSAGE_UPDATE_PARAMETERS, JOptionPane.ERROR_MESSAGE);
		}
	}

	public ConnectionSQL getConnectionSQL() {
		this.connectionSQL.setHostName(this.txtServer.getText());
		this.connectionSQL.setDataBaseName(this.txtDNS.getText());
		this.connectionSQL.setUserName(this.txtUser.getText());
		char[] pass = this.txtPassword.getPassword();
		this.connectionSQL.setPassword(new String(pass));
		this.connectionSQL.setPortName(this.txtPort.getText());
		String dbType = this.cmbDBTypes.getSelectedItem().toString();
		if (dbType.equals(AppConstants.DB_TYPES[0]))
			this.connectionSQL.setMotorBD(dbType);
		else if (dbType.equals(AppConstants.DB_TYPES[1]))
			this.connectionSQL.setMotorBD(dbType);
		else
			this.connectionSQL.setMotorBD(dbType);
		return this.connectionSQL;
	}
}
