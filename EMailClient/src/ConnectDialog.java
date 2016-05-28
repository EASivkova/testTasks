import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ConnectDialog extends JDialog {

	private static final String[] TYPES = {"pop3", "imap"};
	private JComboBox typeComboBox;
	private JTextField serverTextField, usernameTextField, smtpServerTextField;
	private JPasswordField passwordField;
	
	public ConnectDialog(Frame parent) {
		super(parent, true);
		setTitle("Connect");
		addWindowListener(new WindowAdapter() {
			public void WindowClosing(WindowEvent e) {
				actionCancel();
			}
		});
		JPanel settingsPanel = new JPanel();
		settingsPanel.setBorder(BorderFactory.createTitledBorder("Connectio Settings"));
		GridBagConstraints constraints;
		GridBagLayout layout = new GridBagLayout();
		settingsPanel.setLayout(layout);
		JLabel typeLabel = new JLabel("Type:");
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(5, 5, 0, 0);
		layout.setConstraints(typeLabel, constraints);
		settingsPanel.add(typeLabel);
		typeComboBox = new JComboBox(TYPES);
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 5, 0, 5);
		constraints.weightx = 1.0D;
		layout.setConstraints(typeComboBox, constraints);
		settingsPanel.add(typeComboBox);
		JLabel serverLabel = new JLabel("Server:");
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(5, 5, 0, 0);
		layout.setConstraints(serverLabel, constraints);
		settingsPanel.add(serverLabel);
		serverTextField = new JTextField(25);
		constraints = new GridBagConstraints();
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 5, 0, 5);
		constraints.weightx = 1.0D;
		layout.setConstraints(serverTextField, constraints);
		settingsPanel.add(serverTextField);
		JLabel usernameLabel = new JLabel("Username:");
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(5, 5, 0, 0);
		layout.setConstraints(usernameLabel, constraints);
		settingsPanel.add(usernameLabel);
		usernameTextField = new JTextField();
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 5, 0, 5);
		constraints.weightx = 1.0D;
		layout.setConstraints(usernameTextField, constraints);
		settingsPanel.add(usernameTextField);
		JLabel passwordLabel = new JLabel("Password:");
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(5, 5, 0, 0);
		layout.setConstraints(passwordLabel, constraints);
		settingsPanel.add(passwordLabel);
		passwordField = new JPasswordField();
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.weightx = 1.0D;
		layout.setConstraints(passwordField, constraints);
		settingsPanel.add(passwordField);
		JLabel smtpServerLabel = new JLabel("SMTP Server:");
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(5, 5, 5, 0);
		layout.setConstraints(smtpServerLabel, constraints);
		settingsPanel.add(smtpServerLabel);
		smtpServerTextField = new JTextField(25);
		constraints = new GridBagConstraints();
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 5, 5, 5);
		constraints.weightx = 1.0D;
		layout.setConstraints(smtpServerTextField, constraints);
		settingsPanel.add(smtpServerTextField);
		JPanel buttonsPanel = new JPanel();
		JButton connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionConnect();
			}
		});
		buttonsPanel.add(connectButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionCancel();
			}
		});
		buttonsPanel.add(cancelButton);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(settingsPanel, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(parent);
	}
	
	private void actionConnect() {
		if (serverTextField.getText().trim().length() < 1 || usernameTextField.getText().trim().length() < 1 
				|| passwordField.getPassword().length < 1 || smtpServerTextField.getText().trim().length() < 1) {
			JOptionPane.showMessageDialog(this, "One or more settings is missing", "Missig Setting(s)", JOptionPane.ERROR_MESSAGE);
			return;
		}
		dispose();
	}
	
	private void actionCancel() {
		System.exit(0);
	}
	
	public String getType() {
		return (String) typeComboBox.getSelectedItem();
	}
	
	public String getServer() {
		return serverTextField.getText();
	}
	
	public String getUsername() {
		return usernameTextField.getText();
	}
	
	public String getPassword() {
		return new String(passwordField.getPassword());
	}
	
	public String getSmtpServer() {
		return smtpServerTextField.getText();
	}
}
