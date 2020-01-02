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

public class ConnectionStringPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final String[] MOTORES_DB = { "SQL Server", "MySQL", "Oracle" };

	private ConnectionSQL cadenaConexion;

	private JLabel lblServidor;
	private JLabel lblDNS;
	private JLabel lblUsuario;
	private JLabel lblPassword;
	private JLabel lblPuerto;
	private JLabel lblMotorDB;

	private JTextField txtServidor;
	private JTextField txtDNS;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JTextField txtPuerto;
	private JComboBox<String> cmbMotorDB;

	public ConnectionStringPanel() {
		this.setBorder(new TitledBorder("Connection String"));
		GroupLayout grupo = new GroupLayout(this);
		this.setLayout(grupo);

		this.lblServidor = new JLabel("Servidor: ");
		this.lblDNS = new JLabel("DSN: ");
		this.lblUsuario = new JLabel("Usuario: ");
		this.lblPassword = new JLabel("Password: ");
		this.lblPuerto = new JLabel("Puerto: ");
		this.lblMotorDB = new JLabel("Motor DB: ");

		this.txtServidor = new JTextField();
		this.txtDNS = new JTextField();
		this.txtUsuario = new JTextField();
		this.txtPassword = new JPasswordField();
		this.txtPuerto = new JTextField();
		DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>(MOTORES_DB);
		this.cmbMotorDB = new JComboBox<>(modelo);

		grupo.setAutoCreateContainerGaps(true);
		grupo.setAutoCreateGaps(true);

		grupo.setVerticalGroup(grupo.createSequentialGroup()
				.addGroup(grupo.createParallelGroup().addComponent(lblServidor).addComponent(txtServidor))
				.addGroup(grupo.createParallelGroup().addComponent(lblDNS).addComponent(txtDNS))
				.addGroup(grupo.createParallelGroup().addComponent(lblUsuario).addComponent(txtUsuario))
				.addGroup(grupo.createParallelGroup().addComponent(lblPassword).addComponent(txtPassword))
				.addGroup(grupo.createParallelGroup().addComponent(lblPuerto).addComponent(txtPuerto))
				.addGroup(grupo.createParallelGroup().addComponent(lblMotorDB).addComponent(cmbMotorDB)));

		grupo.setHorizontalGroup(grupo.createSequentialGroup()
				.addGroup(grupo.createParallelGroup().addComponent(lblServidor).addComponent(lblDNS)
						.addComponent(lblUsuario).addComponent(lblPassword).addComponent(lblPuerto)
						.addComponent(lblMotorDB))

				.addGroup(grupo.createParallelGroup().addComponent(txtServidor).addComponent(txtDNS)
						.addComponent(txtUsuario).addComponent(txtPassword).addComponent(txtPuerto)
						.addComponent(cmbMotorDB)));
	}

	public void actualizarParametros() {
		try {
			cadenaConexion = new ConnectionSQL();
			this.txtServidor.setText(cadenaConexion.getHostName());
			this.txtDNS.setText(cadenaConexion.getDataBaseName());
			this.txtUsuario.setText(cadenaConexion.getUserName());
			this.txtPassword.setText(cadenaConexion.getPassword());
			this.txtPuerto.setText(cadenaConexion.getPortName());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Actualizar Paranetros", JOptionPane.ERROR_MESSAGE);
		}
	}

	public ConnectionSQL obtenerCadenaSQL() {
		cadenaConexion.setHostName(this.txtServidor.getText());
		cadenaConexion.setDataBaseName(this.txtDNS.getText());
		cadenaConexion.setUserName(txtUsuario.getText());
		char[] pass = txtPassword.getPassword();
		cadenaConexion.setPassword(new String(pass));
		cadenaConexion.setPortName(txtPuerto.getText());
		String tipoMotor = cmbMotorDB.getSelectedItem().toString();
		if (tipoMotor.equals(MOTORES_DB[0]))
			cadenaConexion.setMotorBD(tipoMotor);
		else if (tipoMotor.equals(MOTORES_DB[1]))
			cadenaConexion.setMotorBD(tipoMotor);
		else
			cadenaConexion.setMotorBD(tipoMotor);
		return cadenaConexion;
	}
}
