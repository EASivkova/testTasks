import java.awt.*;
import java.awt.event.*;

import javax.mail.*;
import javax.swing.*;

import com.ibm.security.pkcs9.EmailAddress;

public class MessageDialog extends JDialog {

	public static final int NEW = 0;
	public static final int REPLY = 1;
	public static final int FORWARD = 2;
	
	private JTextField fromTextField, toTextField, subjectTextField;
	private JTextArea contentTextArea;
	private boolean cancelled;
	
	public MessageDialog(Frame parent, int type, Message message) throws Exception {
		super(parent, true);
		String to = "", subject = "", content = "";
		switch (type) {
		case REPLY:
			setTitle("Reply to Message");
			Address[] senders = message.getFrom();
			if (senders != null || senders.length > 0)
				to = senders[0].toString();
			to = message.getFrom()[0].toString();
			subject = message.getSubject();
			if (subject != null && subject.length() > 0)
				subject = "Re: " + subject;
			else
				subject = "Re:";
			content = "/n-------------REPLIED TO MESSAGE-------------/n" + EmailClient.getMessageContent(message);
			break;
		case FORWARD:
			setTitle("Forward Message");
			subject = message.getSubject();
			if (subject != null && subject.length() > 0)
				subject = "FW: " + subject;
			else
				subject = "FW:";
			content = "/n-------------FORWARDED MESSAGE-------------/n" + EmailClient.getMessageContent(message);
			break;
		default:
			setTitle("New Message");
		}
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				actionCancel();
			}
		});
		JPanel fieldsPanel = new JPanel();
		GridBagConstraints constraints;
		GridBagLayout layout = new GridBagLayout();
		fieldsPanel.setLayout(layout);
		JLabel fromLabel = new JLabel("From:");
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(5, 5, 0, 0);
		layout.setConstraints(fromLabel, constraints);
		fieldsPanel.add(fromLabel);
		fromTextField = new JTextField();
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 5, 0, 0);
		layout.setConstraints(fromTextField, constraints);
		fieldsPanel.add(fromTextField);
		JLabel toLabel = new JLabel("To:");
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(5, 5, 0, 0);
		layout.setConstraints(toLabel, constraints);
		fieldsPanel.add(toLabel);
		toTextField = new JTextField();
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 5, 0, 0);
		constraints.weightx = 1.0D;
		layout.setConstraints(toTextField, constraints);
		fieldsPanel.add(toTextField);
		JLabel subjectLabel = new JLabel("Subject:");
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(5, 5, 5, 0);
		layout.setConstraints(subjectLabel, constraints);
		fieldsPanel.add(subjectLabel);
		subjectTextField = new JTextField();
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.insets = new Insets(5, 5, 5, 0);
		layout.setConstraints(subjectTextField, constraints);
		fieldsPanel.add(subjectTextField);
		JScrollPane contentPanel = new JScrollPane();
		contentTextArea = new JTextArea(content, 10, 50);
		contentPanel.setViewportView(contentTextArea);
		JPanel buttonsPanel = new JPanel();
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionSend();
			}
		});
		buttonsPanel.add(sendButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionCancel();
			}
		});
		buttonsPanel.add(cancelButton);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(fieldsPanel, BorderLayout.NORTH);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(parent);
	}
	
	private void actionSend() {
		if (fromTextField.getText().trim().length() < 1 || toTextField.getText().trim().length() < 1 
				|| subjectTextField.getText().trim().length() < 1 || contentTextArea.getText().trim().length() < 1) {
			JOptionPane.showMessageDialog(this, "One or more fields is missing.", "Missing Field(s)", JOptionPane.ERROR_MESSAGE);
			return;
		}
		dispose();
	}
	
	private void actionCancel() {
		cancelled = true;
		dispose();
	}
	
	public boolean display() {
		show();
		return !cancelled;
	}
	
	public String getFrom() {
		return fromTextField.getText();
	}
	
	public String getTo() {
		return toTextField.getText();
	}
	
	public String getSubject() {
		return subjectTextField.getText();
	}
	
	public String getContent() {
		return contentTextArea.getText();
	}
}
