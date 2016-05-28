import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import javax.swing.event.*;

public class EmailClient extends JFrame {

	private MessagesTableModel tableModel;
	private JTable table;
	private JTextArea messageTextArea;
	private JSplitPane splitPane;
	private JButton replyButton, forwardButton, deleteButton;
	private Message selectedMessage;
	private boolean deleting;
	private Session session;
	
	public EmailClient() {
		setTitle("E-mail Client");
		setSize(640, 480);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				actionExit();
			}
		});
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		fileExitMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionExit();
			}
		});
		fileMenu.add(fileExitMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		JPanel buttonPanel = new JPanel();
		JButton newButton = new JButton("New Messsage");
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionNew();
			}
		});
		buttonPanel.add(newButton);
		tableModel = new MessagesTableModel();
		table = new JTable(tableModel);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				tableSelectionChanged();
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel emailsPanel = new JPanel();
		emailsPanel.setBorder(BorderFactory.createTitledBorder("E-mails"));
		messageTextArea = new JTextArea();
		messageTextArea.setEditable(false);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(table), new JScrollPane(messageTextArea));
		emailsPanel.setLayout(new BorderLayout());
		emailsPanel.add(splitPane, BorderLayout.CENTER);
		JPanel buttonPanel2 = new JPanel();
		replyButton = new JButton("Reply");
		replyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionReply();
			}
		});
		replyButton.setEnabled(false);
		buttonPanel2.add(replyButton);
		forwardButton = new JButton("Forward");
		forwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionForward();
			}
		});
		forwardButton.setEnabled(false);
		buttonPanel2.add(forwardButton);
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionDelete();
			}
		});
		deleteButton.setEnabled(false);
		buttonPanel2.add(deleteButton);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(buttonPanel, BorderLayout.NORTH);
		getContentPane().add(emailsPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel2, BorderLayout.SOUTH);
	}

	private void actionExit() {
		System.exit(0);
	}
	
	private void actionNew() {
		sendMessage(MessageDialog.NEW, null);
	}
	
	private void tableSelectionChanged() {
		if (!deleting) {
			selectedMessage = tableModel.getMessage(table.getSelectedRow());
			showSelectedMessage();
			updateButtons();
		}
	}
	
	private void actionReply() {
		sendMessage(MessageDialog.REPLY, selectedMessage);
	}
	
	private void actionForward() {
		sendMessage(MessageDialog.FORWARD, selectedMessage);
	}
	
	private void actionDelete() {
		deleting = true;
		try {
			selectedMessage.setFlag(Flags.Flag.DELETED, true);
			Folder folder = selectedMessage.getFolder();
			folder.close(true);
			folder.open(Folder.READ_WRITE);
		} catch (Exception e) {
			showError("Unable to delete message.", false);
		}
		tableModel.deleteMessage(table.getSelectedRow());
		messageTextArea.setText("");
		deleting = false;
		selectedMessage = null;
		updateButtons();
	}
	
	private void sendMessage(int type, Message message) {
		MessageDialog dialog;
		try {
			dialog = new MessageDialog(this, type, message);
			if (!dialog.display())
				return;
		} catch (Exception e) {
			showError("Unable to send message.", false);
			return;
		}
		try {
			Message newMessage = new MimeMessage(session);
			newMessage.setFrom(new InternetAddress(dialog.getFrom()));
			newMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(dialog.getTo()));
			newMessage.setSubject(dialog.getSubject());
			newMessage.setSentDate(new Date());
			newMessage.setText(dialog.getContent());
			Transport.send(newMessage);
		} catch (Exception e) {
			showError("Unable to send message.", false);
		}
	}
	
	private void showSelectedMessage() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try {
			messageTextArea.setText(getMessageContent(selectedMessage));
			messageTextArea.setCaretPosition(0);
		} catch (Exception e) {
			showError("Unable to load message.", false);
		} finally {
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private void updateButtons() {
		if (selectedMessage != null) {
			replyButton.setEnabled(true);
			forwardButton.setEnabled(true);
			deleteButton.setEnabled(true);
		} else {
			replyButton.setEnabled(false);
			forwardButton.setEnabled(false);
			deleteButton.setEnabled(false);
		}
	}
	
	public void show() {
		super.show();
		splitPane.setDividerLocation(.5);
	}
	
	public void connect() {
		ConnectDialog dialog = new ConnectDialog(this);
		dialog.show();
		StringBuffer connectionUrl = new StringBuffer();
		connectionUrl.append(dialog.getType() + "://");
		connectionUrl.append(dialog.getUsername() + ":");
		connectionUrl.append(dialog.getPassword() + "@");
		connectionUrl.append(dialog.getServer() + "/");
		final DownloadingDialog downloadingDialog = new DownloadingDialog(this);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				downloadingDialog.show();
			}
		});
		Store store = null;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", dialog.getSmtpServer());
			session = Session.getDefaultInstance(props, null);
			URLName urln = new URLName(connectionUrl.toString());
			store = session.getStore(urln);
			store.connect();
		} catch (Exception e) {
			downloadingDialog.dispose();
			showError("Unable to connect.", true);
		}
		try {
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);
			Message[] messages = folder.getMessages();
			FetchProfile profile = new FetchProfile();
			profile.add(FetchProfile.Item.ENVELOPE);
			folder.fetch(messages, profile);
			tableModel.setMessages(messages);
		} catch (Exception e) {
			downloadingDialog.dispose();
			showError("Unable to download messages.", true);
		}
		downloadingDialog.dispose();
	}
	
	private void showError(String message, boolean exit) {
		JOptionPane.showConfirmDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
		if (exit)
			System.exit(0);
	}
	
	public static String getMessageContent(Message message) throws Exception {
		Object content = message.getContent();
		if (content instanceof Multipart) {
			StringBuffer messageContent = new StringBuffer();
			Multipart multipart = (Multipart) content;
			for (int i = 0; i < multipart.getCount(); i++) {
				Part part = (Part) multipart.getBodyPart(i);
				if (part.isMimeType("text/plain"))
					messageContent.append(part.getContent().toString());
			}
			return messageContent.toString();
		} else {
			return content.toString();
		}
	}
	
	public static void main(String[] args) {
		EmailClient client = new EmailClient();
		client.show();
		client.connect();
	}

}
