package com.kentaurus.jsqlquery.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.kentaurus.jsqlquery.constants.AppConstants;
import com.kentaurus.jsqlquery.controller.ControllerApp;

public class OptionsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final String EXECUTE_SQL_SENTENCE_SQL = "Ejecutar sentencia sql";
	public static final String STOP_SQL_SENTENCE_EXECUTION = "Detener sentencia sql";
	public static final String SAVE_SETTINGS = "Guardar configuracion";

	private ControllerApp ctrl;
	private int numberJudgmentSql;
	private int timeOut;

	private JLabel lblTimeExecution;
	private JLabel lblSeconds;
	private JTextField txtTimeExecution;
	private JRadioButton rbtnReturnTableResult;
	private JRadioButton rbtnReturnTextResult;
	private JRadioButton rbtnExecuteSentenceSql;
	private JButton btnRun;
	private ButtonGroup groupRadios;
	private JButton btnSaveConfiguration;

	public OptionsPanel(ControllerApp ctrl) {
		this.timeOut = 0;
		this.numberJudgmentSql = 0;
		this.ctrl = ctrl;
		this.setBorder(new TitledBorder(AppConstants.PANEL_TITLE_QUERY));
		this.setLayout(new BorderLayout());

		this.lblTimeExecution = new JLabel(AppConstants.LABEL_TIME_EXECUTION);
		this.lblSeconds = new JLabel(AppConstants.LABEL_SECONDS);
		this.txtTimeExecution = new JTextField(7);

		this.rbtnReturnTableResult = new JRadioButton(AppConstants.RADIO_TABLE_RESULT, true);
		this.rbtnReturnTextResult = new JRadioButton(AppConstants.RADIO_TEXT_RESULT);
		this.rbtnExecuteSentenceSql = new JRadioButton(AppConstants.RADIO_SENTENCE_SQL);

		this.groupRadios = new ButtonGroup();
		this.groupRadios.add(this.rbtnReturnTableResult);
		this.groupRadios.add(this.rbtnReturnTextResult);
		this.groupRadios.add(this.rbtnExecuteSentenceSql);

		this.btnSaveConfiguration = new JButton(AppConstants.BUTTON_SAVE_CONFIGURATION);
		this.btnSaveConfiguration.setActionCommand(SAVE_SETTINGS);
		this.btnSaveConfiguration.addActionListener(this);

		JPanel pnlLeft = new JPanel();
		pnlLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnlLeft.add(this.rbtnReturnTableResult);
		pnlLeft.add(this.rbtnReturnTextResult);
		pnlLeft.add(this.rbtnExecuteSentenceSql);
		pnlLeft.add(this.btnSaveConfiguration);

		this.btnRun = new JButton(AppConstants.BUTTON_EXECUTION_SENTENCE);
		this.btnRun.setActionCommand(EXECUTE_SQL_SENTENCE_SQL);
		this.btnRun.addActionListener(this);

		JPanel pnlRight = new JPanel();
		pnlRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnlRight.add(this.lblTimeExecution);
		pnlRight.add(this.txtTimeExecution);
		pnlRight.add(this.lblSeconds);
		pnlRight.add(this.btnRun);
		this.txtTimeExecution.setText("30");
		this.add(pnlLeft, BorderLayout.WEST);
		this.add(pnlRight, BorderLayout.EAST);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(EXECUTE_SQL_SENTENCE_SQL)) {
			try {
				String timeOutStr = this.txtTimeExecution.getText().trim();
				if (timeOutStr == null || timeOutStr.equals(""))
					this.timeOut = 0;
				else {
					try {
						this.timeOut = Integer.parseInt(timeOutStr);
					} catch (Exception ex) {
						this.timeOut = 0;
					}
				}
				if (this.rbtnReturnTableResult.isSelected()) {
					String numberJudgment = this.numberJudgmentSql++ + "";
					Runnable r = () -> {
						try {
							this.ctrl.addProcess(String.format(AppConstants.LOG_EXECUTION_SQL_START, numberJudgment,
									this.getCurrentDate()));
							int n = this.ctrl.executeJudgmentTableResult(this.timeOut);
							this.ctrl.deleteProcess(String.format(AppConstants.LOG_EXECUTION_SQL_END, numberJudgment,
									this.getCurrentDate(), n));
						} catch (Exception ex) {
							try {
								this.ctrl.deleteProcess(String.format(AppConstants.LOG_EXECUTION_ERROR_SQL_END,
										numberJudgment, this.getCurrentDate(), ex.getMessage()));
								JOptionPane.showMessageDialog(this, ex.getMessage(), AppConstants.RADIO_SENTENCE_SQL,
										JOptionPane.ERROR_MESSAGE);
							} catch (Exception ex1) {
								JOptionPane.showMessageDialog(this, ex1.getMessage(), AppConstants.RADIO_SENTENCE_SQL,
										JOptionPane.ERROR_MESSAGE);
							}
						}
					};
					ExecutorService executor = Executors.newSingleThreadExecutor();
					Future<?> future = executor.submit(() -> {
						executor.execute(r);
					});
					while (!future.isDone()) {
					}
					executor.shutdown();
				} else if (this.rbtnReturnTextResult.isSelected()) {
					String actualNumber = this.numberJudgmentSql++ + "";
					Runnable r = () -> {
						try {
							this.ctrl.addProcess(String.format(AppConstants.LOG_EXECUTION_SQL_START, actualNumber,
									this.getCurrentDate()));
							int n = this.ctrl.executeSentenceTextResult(this.timeOut);
							this.ctrl.deleteProcess(String.format(AppConstants.LOG_EXECUTION_SQL_END, actualNumber,
									this.getCurrentDate(), n));
						} catch (Exception ex) {
							try {
								this.ctrl.deleteProcess(String.format(AppConstants.LOG_EXECUTION_ERROR_SQL_END,
										actualNumber, this.getCurrentDate(), ex.getMessage()));
								JOptionPane.showMessageDialog(this, ex.getMessage(), AppConstants.RADIO_SENTENCE_SQL,
										JOptionPane.ERROR_MESSAGE);
							} catch (Exception ex1) {
								JOptionPane.showMessageDialog(this, ex1.getMessage(), AppConstants.RADIO_SENTENCE_SQL,
										JOptionPane.ERROR_MESSAGE);
							}
						}
					};
					ExecutorService executor = Executors.newSingleThreadExecutor();

					Future<?> future = executor.submit(() -> {
						executor.execute(r);
					});
					while (!future.isDone()) {
					}
					executor.shutdown();
				} else if (this.rbtnExecuteSentenceSql.isSelected()) {
					String actualNumber = numberJudgmentSql++ + "";
					Runnable r = () -> {
						try {
							this.ctrl.addProcess(String.format(AppConstants.LOG_EXECUTION_SQL_START, actualNumber,
									this.getCurrentDate()));
							int n = this.ctrl.executeSQLstatement(this.timeOut);
							this.ctrl.deleteProcess(String.format(AppConstants.LOG_EXECUTION_SQL_END, actualNumber,
									this.getCurrentDate(), n));
						} catch (Exception ex) {
							try {
								this.ctrl.deleteProcess(String.format(AppConstants.LOG_EXECUTION_ERROR_SQL_END,
										actualNumber, this.getCurrentDate(), ex.getMessage()));
								JOptionPane.showMessageDialog(this, ex.getMessage(), AppConstants.RADIO_SENTENCE_SQL,
										JOptionPane.ERROR_MESSAGE);
							} catch (Exception ex1) {
								JOptionPane.showMessageDialog(this, ex1.getMessage(), AppConstants.RADIO_SENTENCE_SQL,
										JOptionPane.ERROR_MESSAGE);
							}
						}
					};
					ExecutorService executor = Executors.newSingleThreadExecutor();
					Future<?> future = executor.submit(() -> {
						executor.execute(r);
					});
					while (!future.isDone()) {
					}
					executor.shutdown();
				} else {
					throw new Exception(AppConstants.ERROR_NO_VALID_OPTION);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), AppConstants.RADIO_SENTENCE_SQL,
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (command.equals(SAVE_SETTINGS)) {
			try {
				int confirmation = JOptionPane.showConfirmDialog(this, AppConstants.QUESTION_SAVE_CONFIGURATION,
						AppConstants.TITLE_MESSAGE_SAVE_CONFIGURATION, JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				if (confirmation == JOptionPane.OK_OPTION) {
					this.ctrl.saveSettings();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), AppConstants.TITLE_MESSAGE_SAVE_CONFIGURATION,
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public String getCurrentDate() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat(AppConstants.DATE_FORMAT);
		return dateFormat.format(date);
	}
}
