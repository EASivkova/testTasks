package doc;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.xml.parsers.*;

import org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton;
import org.w3c.dom.*;

import ru.svrw.eivc.date.Calendar;
import ru.svrw.eivc.util.Properties;
import ru.svrw.eivc.util.StringUtils;

import jxl.*;

public class Interface extends Frame implements ActionListener, ItemListener {
	private Button submit, bObzor_listContractors, bObzor_workCatalog, bCreateFileForPPonTable, bSendByAllMail;
// Выполнить, Обзор.., выбор рабочего каталога, Создание файла д/ПП на основе таблицы, общая рассылка
	private TextField tfAdress_workCatalog, tfAdres_listContractors;
// адрес рабочей папки, адрес файла Excel (список контрагентов)
	private JComboBox jcbMonth, jcbYear;
// месяца, года
	private TextArea taError, taBody;
// поле вывода инф-ции об ошибках, текст сообщения при общей рассылке
	private String workCatalog, address_listContractors, sError = "", code_user, login, YYYYmm, name_user;
// адрес рабочей папки, адрес файла Excel (список контрагентов), инф-ция об ошибках, пользователь (код), фамилия пользователя, месяц-год за коорые обрабатываюся документы
	Checkbox cbAdd, cbSendMail, cbIdentifyFiles, cbIdentifyFilesGVC, cbCreateFileForSortDoc, cbCreateFileForPP, cbSendFiles, сbParseCatUser, сbParseCat;
// дописывать в существующий файл, отправить на E-mail, распознание файлов, создание файла д/сорт.документов, создание файла д/ПП, рассылка файлов, отчет по указаной папке архива, отчет по всем папкам архива за указаный месяц
	private JRadioButton rbCleaner, rbSelectAll;
// очистить выделение, выделить все
	private Label result = new Label(""), summKolLetter = new Label("0");
// отчет о результате работы программы, суммарное кол-во конвертов для ПП
	private JTable tableContractors;
// таблица на основе списока контрагентов
	private Button bLoadBD, bCreateFile;
	// Выполнить, Обзор.., выбор рабочего каталога, загрузка данных из ПП, создание файла со списком состояний конвертов
	private TextField tfEndDay, tfStartDay, tfEmail;
	// адрес рабочей папки, адрес файла Excel (список контрагентов), Последний день периода, Первый день периода, email для списка
	private JTable table_lastID, table_allID;
	// таблица последних конвертов по предприятию, все конверты по выбранному предприяию
	private JCalendarButton jcbStartDay, jcbEndDay;
	// кнопка календаря для начальной даты, для конечной даты периода
	private Calendar today = new Calendar();
	
	private Panel pReport;
	
	private String nameF = "";
	private String adrF = "";
	
	private String[] itemUser;
	private String[] itemYear;

	private StringUtils su = new StringUtils(); 
	private Properties prop = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));

	// Интерфейс
	Interface(String[] itemUser, String[] itemYear, String login) {
		this.itemUser = itemUser;
		this.itemYear = itemYear;
		this.login = login;
		getInfoUser(login);
		setLayout(new BorderLayout());
		add("North", getPanel_Header());
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("Обработка документов", getPanel_DataProcessing());
		tabPane.addTab("Отдельные конверты", getPanel_OneLetter());
		tabPane.addTab("Общая рассылка", getPanel_AllMail());
		tabPane.addTab("Поиск ID конвертов", getPanel_SearchIDletter());
		add("Center", tabPane);
		pReport = new Panel();
		pReport.setLayout(new BorderLayout());
			result = new Label();
		pReport.add("North", result);
			taError = new TextArea(4, 45);
			taError.setVisible(false);
		pReport.add("Center", taError);
		pReport.add("West", new Label(" "));
		pReport.add("East", new Label(" "));
		pReport.add("South", new Label(" "));
		add("South", pReport);
		// Создание окна
		setTitle("Обработка финансовых документов");
		setSize(682, 750);
		setBackground(Color.getHSBColor(100, 200, 50));
		setForeground(Color.black);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	/* настройка пенели (вкладка) Обработки данных */
	private Panel getPanel_DataProcessing() {
		Panel panel = new Panel(new GridBagLayout());
			cbIdentifyFiles = new Checkbox("Распознание и сортировка файлов по предприятиям");
			cbIdentifyFiles.addItemListener(this);
		panel.add(cbIdentifyFiles, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH,
	            GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			cbIdentifyFilesGVC = new Checkbox("Распознание и сортировка файлов ГВЦ по предприятиям");
			cbIdentifyFilesGVC.addItemListener(this);
		panel.add(cbIdentifyFilesGVC, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			cbSendFiles = new Checkbox("Рассылка файлов по e-mail");
			cbSendFiles.addItemListener(this);
		panel.add(cbSendFiles, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.NORTH,
	            GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			cbCreateFileForPP = new Checkbox("Формирование файла для печати конвертов");
			cbCreateFileForPP.addItemListener(this);
		panel.add(cbCreateFileForPP, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.NORTH,
	            GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			cbCreateFileForSortDoc = new Checkbox("Формирование файла для сортировки документов");
			cbCreateFileForSortDoc.addItemListener(this);
		panel.add(cbCreateFileForSortDoc, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.NORTH,
	            GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			сbParseCatUser = new Checkbox("Формирование отчета по архиву у данного пользователя");
			сbParseCatUser.addItemListener(this);
		panel.add(сbParseCatUser, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			сbParseCat = new Checkbox("Формирование отчета по архиву");
			сbParseCat.addItemListener(this);
		panel.add(сbParseCat, new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			submit = new Button("Выполнить");
			submit.addActionListener(this);
		panel.add(submit, new GridBagConstraints(1, 1, 1, 7, 0, 0, GridBagConstraints.NORTH,
	            GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		return panel;
	}
	
	/* настройка панели (вкладка) Отдельные конверты */
	private Panel getPanel_OneLetter() {
		Panel panel = new Panel(new GridBagLayout());
		cbAdd = new Checkbox("Дописывать в существующий файл");
		cbAdd.addItemListener(this);
		panel.add(cbAdd, new GridBagConstraints(0, 0, 1, 1, 0, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 2, 5), 0, 0));
		JTableDefault jtd = new JTableDefault();
		tableContractors = new JTable(jtd.getDataNull(), jtd.getHeader("OneLetter")) {
			@Override
			public boolean isCellEditable ( int row, int column ) {
				return false;
			}
		};
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableContractors.getModel());
		tableContractors.setRowSorter(sorter);
		Panel pFilter = new Panel();
		final TextField tfFilter = new TextField("", 20);
		pFilter.add(new Label("Фильтр:"));
		pFilter.add(tfFilter);
		tfFilter.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				String text = tfFilter.getText();
				char[] choices = text.toCharArray();
				text = "";
				for (int i = 0; i < choices.length; i++) {
				text += "(" + choices[i] + "|" + Character.toLowerCase(choices[i]) + "|" + Character.toUpperCase(choices[i]) + ")";
				}
				if (text.length() == 0) {
					sorter.setRowFilter(null);
				} else {
					try {
						sorter.setRowFilter(RowFilter.regexFilter(text));
					} catch (PatternSyntaxException pse) {
						System.err.println("Bad regex pattern");
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		panel.add(pFilter, new GridBagConstraints(0, 2, 1, 1, 0, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(2, 5, 1, 5), 0, 0));
		tableContractors.setRowHeight(35);
		tableContractors.getTableHeader().setReorderingAllowed(false);
		tableContractors.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn column = null;
		for (int i = 0; i < 3; i++) {
			column = tableContractors.getColumnModel().getColumn(i);
			if (i == 0)
				column.setPreferredWidth(572); //third column is bigger
			if (i == 1)
				column.setPreferredWidth(40);
			if (i == 2)
				column.setPreferredWidth(20);
		}
		tableContractors.doLayout();
		tableContractors.setFont(new Font("Serif", Font.ROMAN_BASELINE, 14));
		tableContractors.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int row = tableContractors.getSelectedRow();  // определяем какая строка выбранна
				int column = tableContractors.getSelectedColumn(); // определяем какой столбец выбран
				int sum = Integer.parseInt(summKolLetter.getText());
				int kol = (Integer) tableContractors.getValueAt(row, 1);
				if (column == 0) {
					if (kol == 0)
						tableContractors.setValueAt("\"-\"", row, 2);
					tableContractors.setValueAt(kol+1, row, 1);
					summKolLetter.setText("" + (sum+1));
				}
				if (column == 2) {
					if (kol == 1)
						tableContractors.setValueAt("", row, 2);
					if (kol > 0) {
						tableContractors.setValueAt(kol-1, row, 1);
						summKolLetter.setText("" + (sum-1));
					}
				}
			}
		});
		panel.add(new JScrollPane(tableContractors), new GridBagConstraints(0, 3, 1, 1, 1, 7, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			Panel pRadioButton = new Panel();
			rbCleaner = new JRadioButton("Очистить все");
			rbCleaner.addActionListener(this);
			pRadioButton.add(rbCleaner);
			rbSelectAll = new JRadioButton("Отметить все");
			rbSelectAll.addActionListener(this);
			pRadioButton.add(rbSelectAll);
			ButtonGroup bg = new ButtonGroup();
			bg.add(rbCleaner);
			bg.add(rbSelectAll);
		panel.add(pRadioButton, new GridBagConstraints(0, 4, 1, 1, 0, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			Panel itog = new Panel();
			itog.add(new Label("Кол-во конвертов: "));
			itog.add(summKolLetter);
			bCreateFileForPPonTable = new Button("Cформировать файл для печати конвертов");
			bCreateFileForPPonTable.addActionListener(this);
			itog.add(bCreateFileForPPonTable);
		panel.add(itog, new GridBagConstraints(0, 5, 1, 1, 0, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 5, 5), 0, 0));
		return panel;
	}
	
	/* настройка пенели (вкладка) Общая рассылка */
	private Panel getPanel_AllMail() {
		Panel panel = new Panel(new GridBagLayout());
		panel.add(new Label(" "), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH,
	            GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
		panel.add(new Label("Текст сообщения для общей рассылки:"), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
	            GridBagConstraints.BOTH, new Insets(5, 5, 1, 5), 0, 0));
			taBody = new TextArea(10, 70);
			taBody.setText("Уважаемые пользователи!\n\n" +
					"Направляем Вам Уведомление о плановом повышении тарифов с 01.01.2012 г.\n" +
					"По всем вопросам Вы можете связаться с экономистами договорного отдела по телефону (343) 372-61-53, по личным адресам электронной почты: Anna@svrw.ru, Ksana@svrw.ru, Angelina@svrw.ru или с сектором информационного обслуживания по телефону (343) 353-99-60.");
		panel.add(taBody, new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			bSendByAllMail = new Button("Выполнить рассылку уведомнения по всем адресам");
			bSendByAllMail.addActionListener(this);
		panel.add(bSendByAllMail, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		return panel;
	}
	
	/* панель Заголовок */
	private Panel getPanel_Header() {
		Calendar today = new Calendar();
		Panel panel = new Panel(new GridBagLayout());
		panel.add(new Label("Пользователь: " + name_user), new GridBagConstraints(0, 0, 6, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		jcbMonth = new JComboBox(DocumentStart.MONTHS);
		jcbMonth.setSelectedIndex(today.getMonth() - 2);
		jcbMonth.addItemListener(this);
		panel.add(new Label("Месяц:"), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(jcbMonth, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		jcbYear = new JComboBox(itemYear);
		jcbYear.setSelectedItem(today.getYear());
		panel.add(new Label("Год:"), new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(jcbYear, new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		Panel pAdress = new Panel();
//			tfAdress_workCatalog = new TextField(prop.getString("adres.workCatalog"), 42);
			tfAdress_workCatalog = new TextField(workCatalog, 42);
		pAdress.add(new Label("Путь к каталогу с файлами:"));
		pAdress.add(tfAdress_workCatalog);
			bObzor_workCatalog = new Button("Обзор..");
			bObzor_workCatalog.addActionListener(this);
		pAdress.add(bObzor_workCatalog);
		panel.add(pAdress, new GridBagConstraints(0, 2, 6, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		Panel obzor = new Panel();
			tfAdres_listContractors = new TextField(prop.getString("adres.workCatalogFile") + "Список контрагентов.xls", 50);
		obzor.add(new Label("Путь к файлу БД:"));
		obzor.add(tfAdres_listContractors);
			bObzor_listContractors = new Button("Обзор..");
			bObzor_listContractors.addActionListener(this);
		obzor.add(bObzor_listContractors);
		panel.add(obzor, new GridBagConstraints(0, 3, 6, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		return panel;
	}
	
	/* настройка панели (вкладка) Поиска ID конвертов и информации по ним (вручено-невручено) */
	private Panel getPanel_SearchIDletter() {
		Panel panel = new Panel(new GridBagLayout());
		bLoadBD = new Button("Загрузить данные из БД Партионной почты");
		bLoadBD.addActionListener(this);
		panel.add(bLoadBD, new GridBagConstraints(0, 0, 2, 1, 0, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		JTableDefault jtd = new JTableDefault();
		table_lastID = new JTable(jtd.getDataNull(), jtd.getHeader("LastID")) {
			@Override
			public boolean isCellEditable ( int row, int column ) {
				return false;
			}
		};
		final TableRowSorter<TableModel> sorter1 = new TableRowSorter<TableModel>(table_lastID.getModel());
		table_lastID.setRowSorter(sorter1);
		Panel pFilter = new Panel();
		final TextField filter = new TextField("", 20);
		pFilter.add(new Label("Фильтр:"));
		pFilter.add(filter);
		filter.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				String text = filter.getText();
				char[] choices = text.toCharArray();
				text = "";
				for (int i = 0; i < choices.length; i++) {
					text += "(" + choices[i] + "|" + Character.toLowerCase(choices[i]) + "|" + Character.toUpperCase(choices[i]) + ")";
				}
				if (text.length() == 0) {
					sorter1.setRowFilter(null);
				} else {
					try {
						sorter1.setRowFilter(RowFilter.regexFilter(text));
					} catch (PatternSyntaxException pse) {
						System.err.println("Bad regex pattern");
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		panel.add(pFilter, new GridBagConstraints(0, 1, 2, 1, 0, 1, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 1, 5), 0, 0));
		table_lastID.setRowHeight(35);
		table_lastID.getTableHeader().setReorderingAllowed(false);
		table_lastID.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn column1 = null;
		for (int i = 0; i < 3; i++) {
			column1 = table_lastID.getColumnModel().getColumn(i);
			if (i == 0)
				column1.setPreferredWidth(470); //third column is bigger
			if (i == 1)
				column1.setPreferredWidth(140);
			if (i == 2)
				column1.setPreferredWidth(21); //third column is bigger
		}
		table_lastID.doLayout();
		table_lastID.setFont(new Font("Serif", Font.ROMAN_BASELINE, 14));
		table_lastID.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int row = table_lastID.getSelectedRow();  // определяем какая строка выбранна
				int column = table_lastID.getSelectedColumn(); // определяем какой столбец выбран
				String firma = (String) table_lastID.getValueAt(row, 0);
				nameF = firma.substring(6, firma.indexOf("<br>"));
				adrF = firma.substring(firma.indexOf("<br>") + 17, firma.indexOf("</font>"));
				if (column == 0) {
		    		result.setText("Программа работает. Пожалуйста, подождите...");
					getDataAllID(nameF, adrF);
				}
				if (column == 1) {
		    		result.setText("Программа работает. Пожалуйста, подождите...");
		    		String barcode = (String) table_lastID.getValueAt(row, 1);
					Internet conInternet = new Internet();
					conInternet.setBarcode(barcode);
					String otvet = conInternet.forXLS();
					createFileIDletterOnLine(barcode, " ", nameF, adrF, otvet);
					if (otvet.indexOf("соединения") >= 0)
			    		result.setText("Нет соединения с Internet. Удостовертесь, что VIPNet установлен в режим Внешняя конфигурация. ");
					else {
						if (otvet.indexOf("Информация") > -1)
							otvet = "?";
						if (otvet.indexOf("Вручение") > -1)
							otvet = "+";
						if (otvet.indexOf("Не вручено") > -1)
							otvet = "-";
						table_lastID.setValueAt(otvet, row, 2);
						result.setText("");
					}
				} 
			}
		});
		panel.add(new JScrollPane(table_lastID), new GridBagConstraints(0, 2, 2, 1, 0, 13, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 5, 5), 0, 0));
		table_allID = new JTable(jtd.getDataNull(), jtd.getHeader("AllID")) {
			@Override
			public boolean isCellEditable ( int row, int column ) {
					return false;
			}
		};
		table_allID.setRowHeight(20);
		table_allID.getTableHeader().setReorderingAllowed(false);
		table_allID.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn column2 = null;
		for (int i = 0; i < 3; i++) {
			column2 = table_allID.getColumnModel().getColumn(i);
			if (i == 0)
				column2.setPreferredWidth(156); //third column is bigger
			if (i == 1)
				column2.setPreferredWidth(156);
			if (i == 2)
				column2.setPreferredWidth(25);
		}
		table_allID.doLayout();
		table_allID.setFont(new Font("Serif", Font.ROMAN_BASELINE, 14));
		table_allID.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int row = table_allID.getSelectedRow();  // определяем какая строка выбранна
				int column = table_allID.getSelectedColumn(); // определяем какой столбец выбран
				if (column == 1) {
		    		result.setText("Программа работает. Пожалуйста, подождите...");
		    		String barcode = (String) table_allID.getValueAt(row, 1);
					Internet conInternet = new Internet();
					conInternet.setBarcode(barcode);
					String otvet = conInternet.forXLS();
					createFileIDletterOnLine(barcode, (String) table_allID.getValueAt(row, 0), nameF, adrF, otvet);
					if (otvet.indexOf("соединения") >= 0) {
			    		result.setText("Нет соединения с Internet. Удостовертесь, что VIPNet установлен в режим Внешняя конфигурация. ");
					} else {
						if (otvet.indexOf("Информация") > -1)
							otvet = "?";
						if (otvet.indexOf("Вручение") > -1)
							otvet = "+";
						if (otvet.indexOf("Не вручено") > -1)
							otvet = "-";
						table_allID.setValueAt(otvet, row, 2);
			    		result.setText("");
					}
				} 
			}
		});
		panel.add(new JScrollPane(table_allID), new GridBagConstraints(0, 3, 1, 5, 3, 4, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(new Label("Проверить за период: "), new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 1, 5), 0, 0));
		Panel pPeriod = new Panel();
		tfStartDay = new TextField("", 10);
		tfStartDay.setEditable(false);
		tfStartDay.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                String date = tfStartDay.getText();
                setDate(date, 0);
            }
        });
		pPeriod.add(tfStartDay);
		jcbStartDay = new JCalendarButton();
		jcbStartDay.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if (evt.getNewValue() instanceof Date)
                    setDateStart((Date)evt.getNewValue());
            }
        });
		pPeriod.add(jcbStartDay);
		pPeriod.add(new Label(" - "));
		tfEndDay = new TextField("", 10);
		tfEndDay.setEditable(false);
		tfEndDay.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                String date = tfEndDay.getText();
                setDate(date, 1);
            }
        });
		pPeriod.add(tfEndDay);
		jcbEndDay = new JCalendarButton();
		jcbEndDay.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if (evt.getNewValue() instanceof Date)
                    setDateEnd((Date)evt.getNewValue());
            }
        });
		pPeriod.add(jcbEndDay);
		panel.add(pPeriod, new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
		cbSendMail = new Checkbox("отправиь файл по e-mail:");
		cbSendMail.addItemListener(this);
		panel.add(cbSendMail, new GridBagConstraints(1, 5, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
		tfEmail = new TextField("", 20);
		panel.add(tfEmail, new GridBagConstraints(1, 6, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
		bCreateFile = new Button("Сформировать файл");
		bCreateFile.addActionListener(this);
		panel.add(bCreateFile, new GridBagConstraints(1, 7, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(1, 5, 5, 5), 0, 0));
		return panel;
	}
	
    public static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    /**
     * Validate and set the datetime field on the screen given a datetime string.
     * @param dateTime The datetime string
     */
    public void setDate(String dateString, int status) {
		Date date = null;
		try	{
            if ((dateString != null) && (dateString.length() > 0))
                date = dateFormat.parse(dateString);
		} catch (Exception e)	{
            date = null;
		}
		if (status == 0)
			this.setDateStart(date);
		else
			this.setDateEnd(date);
    }
    /**
     * Validate and set the datetime field on the screen given a date.
     * @param dateTime The datetime object
     */
    public void setDateStart(Date date) {
        String dateString = "";
        if (date != null)
    		dateString = dateFormat.format(date);
        tfStartDay.setText(dateString);
        jcbStartDay.setTargetDate(date);
    }
    /**
     * Validate and set the datetime field on the screen given a date.
     * @param dateTime The datetime object
     */
    public void setDateEnd(Date date) {
        String dateString = "";
        if (date != null)
    		dateString = dateFormat.format(date);
        tfEndDay.setText(dateString);
        jcbEndDay.setTargetDate(date);
    }

    // Обработка события Нажатие кнопки
	public void actionPerformed(ActionEvent ae) {
		sError = "";
		taError.setText("");
		taError.setVisible(false);
		result.setText("");
		if (ae.getSource() == bObzor_workCatalog) {
			FileDialog fd = new FileDialog(this);
			fd.setVisible(true);
			workCatalog = fd.getDirectory();
			workCatalog = workCatalog.replaceAll("/", File.separator);
			tfAdress_workCatalog.setText(workCatalog);
		}
		if (ae.getSource() == bObzor_listContractors) {
			FileDialog fd = new FileDialog(this);
			fd.setVisible(true);
			address_listContractors = fd.getDirectory();
			address_listContractors += fd.getFile();
			address_listContractors = address_listContractors.replaceAll("/", File.separator);
			tfAdres_listContractors.setText(address_listContractors);
			if (address_listContractors.substring(address_listContractors.lastIndexOf(".") + 1).equals("xls"))
				getDataTable_OneLetter();
			else
				getDataTable_OneLetter_XML();
			summKolLetter.setText("0");
		}
		workCatalog = tfAdress_workCatalog.getText();
		address_listContractors = tfAdres_listContractors.getText();
		File xlsBD = new File(address_listContractors);
		if (!xlsBD.exists()) {
			result.setText("Не найден файл " + address_listContractors);
		} else {
			if (ae.getSource() == submit) {
				result.setText("Программа работает. Пожалуйста, подождите...");
				if (cbIdentifyFiles.getSelectedObjects() != null) {
					Identification id = new Identification(address_listContractors, workCatalog, YYYYmm, login, jcbMonth.getSelectedIndex());
					id.parseTXT();
					sError = id.getSError();
		    		result.setText("Обработка файлов закончена.");
					if (sError != null && !sError.equals(""))
						result.setText(result.getText() + " Есть ошибки.");
				}
				if (cbIdentifyFilesGVC.getSelectedObjects() != null) {
					IdentificationGVC id = new IdentificationGVC(address_listContractors, workCatalog, YYYYmm, login, jcbMonth.getSelectedIndex());
					id.parseTXT();
					sError = id.getSError();
		    		result.setText("Обработка файлов закончена.");
					if (sError != null && !sError.equals(""))
						result.setText(result.getText() + " Есть ошибки.");
				}
				if (cbCreateFileForPP.getSelectedObjects() != null) {
					ListCover listCover = new ListCover(address_listContractors, workCatalog, code_user);
					if (address_listContractors.indexOf(".xml") > -1)
						listCover.createFileForPP_XML();
					else
						listCover.createFileForPP();
					result.setText(listCover.getSResult());
					sError = listCover.getSError();
					if (sError != null && !sError.equals(""))
						result.setText(result.getText() + "Есть не занесенные в файл.");
				}
				if (cbSendFiles.getSelectedObjects() != null) {
					EMail em = new EMail();
					em.setWorkCatalog(workCatalog);
					if (address_listContractors.indexOf(".xml") > -1)
						em.sendFiles_XML(address_listContractors, login);
					else
						em.sendFiles(address_listContractors, login);
					sError = em.getSError();
					result.setText("Рассылка зевершена.");
					if (sError != null && !sError.equals(""))
						result.setText(result.getText() + " Есть не отправленные.");
				}
				if (cbCreateFileForSortDoc.getSelectedObjects() != null) {
					Sorter sort = new Sorter(workCatalog, address_listContractors, code_user);
					sort.sortDocumentNonLetter();
					result.setText(sort.getResult());
				}
				if (сbParseCatUser.getSelectedObjects() != null) {
					ReportOnArchives archive = new ReportOnArchives(itemUser, YYYYmm);
					archive.parseCatalogUser(prop.getString("adres.archives") + login + File.separator, (String) jcbYear.getSelectedItem(), jcbMonth.getSelectedIndex());
					result.setText(archive.getResult());
				}
				if (сbParseCat.getSelectedObjects() != null) {
					if (authorization()) {
						ReportOnArchives archive = new ReportOnArchives(itemUser, YYYYmm);
						if (address_listContractors.indexOf(".xml") > -1)
							archive.parseCatalog_XML(prop.getString("adres.archives"), prop.getString("adres.archives"), (String) jcbYear.getSelectedItem(), jcbMonth.getSelectedIndex(), address_listContractors);
						else
							archive.parseCatalog(prop.getString("adres.archives"), prop.getString("adres.archives"), (String) jcbYear.getSelectedItem(), jcbMonth.getSelectedIndex(), address_listContractors);
						result.setText(archive.getResult());
						sError = archive.getSError();
					} else
						result.setText("Не верный пароль");
				}
			}
			if (ae.getSource() == bSendByAllMail) {
			    int j = (int) JOptionPane.showConfirmDialog(new Frame(), "Вы уверены, что хотите выполнить общую рассылку?", "Запрос подтверждения", JOptionPane.OK_CANCEL_OPTION);
				if (j == 0) {
					result.setText("Программа работает. Пожалуйста, подождите...");
					EMail em = new EMail();
					em.setWorkCatalog(workCatalog);
					if (address_listContractors.indexOf(".xml") > -1)
						em.sendByAllMail_XML(address_listContractors, taBody.getText());
					else
						em.sendByAllMail(address_listContractors, taBody.getText());
					sError = em.getSError();
					result.setText("Рассылка зевершена.");
					if (sError != null && !sError.equals(""))
						result.setText(result.getText() + "Есть не отправленные.");
				}
			}
			if (ae.getSource() == bCreateFileForPPonTable) {
				result.setText("Программа работает. Пожалуйста, подождите...");
				createFileForPPbyTable();
			}
			if (ae.getSource() == rbCleaner)
				selectedList(false);
			if (ae.getSource() == rbSelectAll)
				selectedList(true);
		}
		if (ae.getSource() == bLoadBD) {
			result.setText("Программа работает. Пожалуйста, подождите...");
			getDataTable_LastID();
		}
		if (ae.getSource() == bCreateFile) {
			result.setText("Программа работает. Пожалуйста, подождите...");
			String startDay = tfStartDay.getText();
			String endDay = tfEndDay.getText();
			if (startDay.equals("") || startDay == null)
				startDay = today.getDBDate() + " 00:00:00.0";
			else
				startDay = startDay.substring(6) + "-" + startDay.substring(3, 5) + "-" + startDay.substring(0, 2) + " 00:00:00.0";
			if (endDay.equals("") || endDay == null)
				endDay = today.getDBDate() + " 23:59:59.0";
			else
				endDay = endDay.substring(6) + "-" + endDay.substring(3, 5) + "-" + endDay.substring(0, 2) + " 23:59:59.0";
			String mameFile = "List letter " + startDay.substring(8, 10) + "-" + endDay.substring(8, 10) + "." + endDay.substring(5, 7) + "." + endDay.substring(0, 4) + " on " + today.getDBDate();
			createFileIDletter(startDay, endDay, mameFile);
			if (cbSendMail.getSelectedObjects() != null && result.getText().indexOf("сформирован") >= 0 && !tfEmail.getText().equals("") && tfEmail.getText() != null) {
				EMail em = new EMail();
				em.setWorkCatalog(workCatalog);
				String subject = "List letter";
				String body = "Список конвертов за " + startDay + "-" + endDay + " на " + today.getDBDate() + ".";
				em.sendMail(mameFile, tfEmail.getText(), subject, body);
				sError = em.getSError();
			}
		}
		if (sError != null && !sError.equals("")) {
			taError.insert(sError, 0);
			taError.setVisible(true);
			Log log = new Log();
			log.setFile(workCatalog, code_user, sError);
		}
	}
	
	// Обработка события изменения состояния ячеек Checkbox и Radiobutton
	public void itemStateChanged(ItemEvent ie) {
        String mm = "" + (jcbMonth.getSelectedIndex() + 1);
        if (mm.length() == 1)
        	mm = "0" + mm;
		YYYYmm = jcbYear.getSelectedItem() + "." + mm;
	}
	
	/* загрузка содержимого таблицы всех ID конвертов по выбранному предприяию при нажатии на кнопку */
	private void getDataAllID(String nameF, String adrF) {
		DefaultTableModel dtm = (DefaultTableModel) table_allID.getModel();
        dtm.setRowCount(0);
        To_BD bdFirebird = new To_BD();
        bdFirebird.getDataAllID(nameF, adrF, dtm);
   		result.setText(bdFirebird.report);
	}
	
	// загрузка содержимого таблицы на панели поиска ID конвертов по нажатию на кнопку
	private void getDataTable_LastID() {
		DefaultTableModel dtm = (DefaultTableModel) table_lastID.getModel(); //получили модель таблицы (с вашими колонками, в часности) 
        dtm.setRowCount(0); //удалили все строки 
        To_BD bdFirebird = new To_BD();
        bdFirebird.getDataTable_LastID(dtm);
   		result.setText(bdFirebird.report);
	}
	
	// содержимое таблицы на панели обработки одиночных конвертов
	private void getDataTable_OneLetter() {
		DefaultTableModel dtm = (DefaultTableModel) tableContractors.getModel();
        dtm.setRowCount(0);
        try {
        	WorkbookSettings wbSettings = new WorkbookSettings();
    		wbSettings.setLocale(new Locale("en", "EN"));
        	Workbook workbook = Workbook.getWorkbook(new File(tfAdres_listContractors.getText())); 
        	Sheet sheet;
        	int kolSheet = workbook.getNumberOfSheets();
        	if (kolSheet > 1) {
        		String response = (String) JOptionPane.showInputDialog(this, 
			    	"В файле " + tfAdres_listContractors.getText() + " более 1 листа...",
			        "Выбор листа для загрузки данных", JOptionPane.QUESTION_MESSAGE, null, 
			        workbook.getSheetNames(), // Array of choices
			        workbook.getSheet(0)); // Initial choice
        		sheet = workbook.getSheet(response); 
        	} else {
            	sheet = workbook.getSheet(0); 
        	}
           	for (int q = 1; q < sheet.getRows(); q++) {
           		if (!sheet.getCell(21, q).getContents().trim().equals("") && !sheet.getCell(9, q).getContents().trim().equals("") && !sheet.getCell(10, q).getContents().trim().equals("")) {
           			Vector row = new Vector();
           			row.add("<html>" + (sheet.getCell(21, q).getContents().trim() + " " + sheet.getCell(11, q).getContents().trim()) + " (" + sheet.getCell(5, q).getContents().trim() + " - " + sheet.getCell(1, q).getContents().trim() + ") " + sheet.getCell(17, q).getContents().trim() + "<br><font size=2>" + sheet.getCell(9, q).getContents() + ", " + sheet.getCell(10, q).getContents().trim() + "</font></html>");
           			row.add(0);
           			row.add("");
           			dtm.addRow(row); //добавляем в таблицу вектор 
           		}
           	}
        	workbook.close();
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	private void getDataTable_OneLetter_XML() {
		DefaultTableModel dtm = (DefaultTableModel) tableContractors.getModel();
        dtm.setRowCount(0);

        int id;
        String[] tagName = {"ivc", "manager", "name", "full_name", "r3", "asufr", "email1", "email2", "index", "address", "addressee", "inn", "kpp", "view_sending", "name_for_cover", "status_dogovor"};
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(address_listContractors));
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("contractor");
            for (int je = 0; je < nodeLst.getLength(); je++) {
                Node fstNode = nodeLst.item(je);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elj = (Element)fstNode;
                    id = Integer.parseInt(elj.getAttribute("contractorid"));
                    String ivc = elj.getElementsByTagName("ivc").item(0).getChildNodes().item(0).getNodeValue();
                    if (elj.getElementsByTagName("name_for_cover").item(0).getChildNodes().item(0) != null && elj.getElementsByTagName("index").item(0).getChildNodes().item(0) != null && elj.getElementsByTagName("address").item(0).getChildNodes().item(0) != null) {
                    	String name_for_cover = elj.getElementsByTagName("name_for_cover").item(0).getChildNodes().item(0).getNodeValue();
                    	String index = elj.getElementsByTagName("index").item(0).getChildNodes().item(0).getNodeValue();
                    	String address = elj.getElementsByTagName("address").item(0).getChildNodes().item(0).getNodeValue();
                    	if (!name_for_cover.trim().equals("") && !index.trim().equals("") && !address.trim().equals("")) {
                    		String addressee = "";
                    		if (elj.getElementsByTagName("addressee").item(0).getChildNodes().item(0) != null)
                    			addressee = elj.getElementsByTagName("addressee").item(0).getChildNodes().item(0).getNodeValue();
                    		String name = "";
                    		if (elj.getElementsByTagName("name").item(0).getChildNodes().item(0) != null)
                    			name = elj.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
                    		String manager = "";
                    		if (elj.getElementsByTagName("manager").item(0).getChildNodes().item(0) != null)
                    			manager = elj.getElementsByTagName("manager").item(0).getChildNodes().item(0).getNodeValue();
                    		String r3 = "";
                    		if (elj.getElementsByTagName("r3").item(0).getChildNodes().item(0) != null)
                    			r3 = elj.getElementsByTagName("r3").item(0).getChildNodes().item(0).getNodeValue();
                    		Vector row = new Vector();
                    		row.add("<html>" + (name_for_cover.trim() + " " + addressee.trim()) + " (" + name.trim() + " - " + manager.trim() + ") " + r3.trim() + "<br><font size=2>" + index + ", " + address + "</font></html>");
                    		row.add(0);
                    		row.add("");
                    		dtm.addRow(row); //добавляем в таблицу вектор 
                    	}
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
	}

	private void selectedList(boolean pr) {
		int sum = Integer.parseInt(summKolLetter.getText());
		for (int i = 0; i < tableContractors.getRowCount(); i++) {
			if (pr) {
				tableContractors.setValueAt("\"-\"", i, 2);
				tableContractors.setValueAt(1, i, 1);
				sum++;
			} else {
				tableContractors.setValueAt("", i, 2);
				tableContractors.setValueAt(0, i, 1);
				sum = 0;
			}
		}
		summKolLetter.setText("" + sum);
	}
	
	/* создание (или дозапись) файла для ПП из таблицы на основе списка контрагентов */
	private void createFileForPPbyTable() {
        int num = 1;
        String[] title = {"NUM", "INDEXTO", "REGION", "AREA", "CITY", "ADRES", "ADRESAT", "MASS", "VALUE", "PAYMENT", "COMMENT"};
        try {
        	WorkXLS xls = new WorkXLS(workCatalog + code_user + "конверты.xls", 11);
			File wb = new File(workCatalog + code_user + "конверты.xls");
			if (cbAdd.getSelectedObjects() != null && wb.exists()) {
	        	WorkXLS xls_old = new WorkXLS(workCatalog + code_user + "конверты.xls", 11);
	        	xls_old.parametrsFile(0);
	        	num = xls_old.getCountRow();
			} else {
				xls.createWorkbook(title);
			}
			for (int row = 0; row < tableContractors.getRowCount(); row++) {
				int kol = (Integer) tableContractors.getValueAt(row, 1);
				if (kol > 0) {
	        		for (int j = 0; j < kol; j++) {
	        			String temp = (String) tableContractors.getValueAt(row, 0);
						ListCover listCover = new ListCover(address_listContractors, workCatalog, code_user);
						listCover.writeToFileForPP(num, temp.substring(temp.indexOf("<br>") + 17, temp.indexOf(", ")), temp.substring(temp.indexOf(", ") + 2, temp.indexOf("</")), temp.substring(6, temp.indexOf(" (")));
						sError = listCover.getSError();
						result.setText(listCover.getSResult());
	        			num++;
	        		}
				}
			}
           	result.setText("Файл для импорта в Партионную почту создан. ");
        } catch (Exception e) {
            System.out.println(e);
            result.setText("Ошибка формирования файла со списком конвертов. ");
        }
	}
	
	/* ввод и проверка пароля на формирование отчета по архиву */
	private boolean authorization() {
//	    String pass = (String) JOptionPane.showInputDialog(this, "Введите пароль:", "Проверка", JOptionPane.QUESTION_MESSAGE); // Initial choice
//	    if (login.equals("Охоткина") || login.equals("Шиляева") || login.equals("Паташова"))
	    	return true;
//	    else
//	    	return false;
	}
	
	private void getInfoUser(String login) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(su.replaceSystemParameter("${pathProject}/conf/users.xml")));
            doc.getDocumentElement().normalize();
            NodeList nodeLst = doc.getElementsByTagName("user");
            for (int je = 0; je < nodeLst.getLength(); je++) {
                Node fstNode = nodeLst.item(je);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element elj = (Element)fstNode;
                    if (elj.getElementsByTagName("login").item(0).getChildNodes().item(0).getNodeValue().equals(login) || elj.getElementsByTagName("login").item(0).getChildNodes().item(0).getNodeValue().equals("_" + login)) {
                    	code_user = elj.getElementsByTagName("code").item(0).getChildNodes().item(0).getNodeValue() + "_";
                    	workCatalog = "C:" + File.separator + elj.getElementsByTagName("work_catalog").item(0).getChildNodes().item(0).getNodeValue() + File.separator;
                    	name_user = elj.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
	/* создание списока состояний отправленых конвертов за период на текущий день (коннект с Инетом). */
/*	private void createFileIDletter(String startDay, String endDay, String mameFile) {
    	String[] title = {"ID конверта", "Дата отправки", "Наименование предприятия", "адрес", "состояние"};
		String otvet;
		To_BD bdFirebird = new To_BD();
		if (bdFirebird.testingCon()) {
			Vector list = bdFirebird.createListIdLetter(startDay, endDay);
			if (list.size() > 0) {
				WorkXLS xls = new WorkXLS(itemMonth, workCatalog + File.separator + mameFile + ".xls", 5);
				try {
					xls.createWorkbook(title);
					xls.open(0);
					for (int i = 0; i < list.size(); i++) {
						Vector element = (Vector) list.elementAt(i);
						for (int j = 0; j < element.size(); j++) {
							xls.writeLabel(j, i+1, (String) element.elementAt(j));
						}
					}
					xls.close();
				} catch(Exception e) { System.out.println(e); }
				JOptionPane.showMessageDialog(this, "Установите VIPNet в режим Внешняя конфигурация");
				Internet conInternet = new Internet();
				if (conInternet.testing()) {
					try {
						Workbook workbookR = Workbook.getWorkbook(new File(workCatalog + File.separator + mameFile + ".xls"));
						Sheet sheetR = workbookR.getSheet(0); 
						xls.open(0);
						for (int j = 1; j < sheetR.getRows(); j++) {
							conInternet.setBarcode(sheetR.getCell(0, j).getContents());
							otvet = conInternet.forXLS();
							xls.writeLabel(4, j, otvet);
						}
						xls.close();
						workbookR.close();
					} catch(Exception e) { System.out.println(e); }
					result.setText("Файл " + mameFile + " сформирован. ");
				} else {
					result.setText("Нет соединения с Internet. Проверка состояния отправлений не была произведена. ");
				}
			} else {
				result.setText("За указанный период отправления отсутствуют. ");
			}
		} else {
			result.setText("Нет соединения с БД Партионной почты. Удостовертесь, что VIPNet установлен в режим Внутренняя конфигурация. ");
		}
	} */
	
	/* создание списока состояний отправленых конвертов за период на текущий день (коннект с Инетом). */
	private void createFileIDletter(String startDay, String endDay, String nameFile) {
    	String[] title = {"ID конверта", "Дата отправки", "Наименование предприятия", "адрес", "состояние"};
		String otvet;
		To_BD bdFirebird = new To_BD();
		Internet conInternet = new Internet();
		if (bdFirebird.testingCon() && conInternet.testing()) {
			Vector list = bdFirebird.createListIdLetter(startDay, endDay);
			if (list.size() > 0) {
				WorkXLS xls = new WorkXLS(workCatalog + File.separator + nameFile + ".xls", 5);
				try {
					xls.createWorkbook(title);
					xls.open(0);
					for (int i = 0; i < list.size(); i++) {
						Vector element = (Vector) list.elementAt(i);
						for (int j = 0; j < element.size(); j++) {
							xls.writeLabel(j, i+1, (String) element.elementAt(j));
							if (j == 0) {
								conInternet.setBarcode((String) element.elementAt(j));
								otvet = conInternet.forXLS();
								xls.writeLabel(4, i+1, otvet);
							}
						}
					}
					xls.close();
				} catch(Exception e) { System.out.println(e); }
				result.setText("Файл " + nameFile + " сформирован. ");
			} else {
				result.setText("За указанный период отправления отсутствуют. ");
			}
		} else {
			result.setText("Нет соединения с БД Партионной почты или с Internet. Проверка состояния отправлений не была произведена. ");
		}
	}
	
	private void createFileIDletterOnLine(String barcode, String dapo, String nameF, String adrF, String otvet) {
    	String[] title = {"ID конверта", "Дата отправки", "Наименование предприятия", "адрес", "состояние"};
		String nameFile = "List letter on " + today.getDBDate();
		WorkXLS xls = new WorkXLS(workCatalog + File.separator + nameFile + ".xls", 5);
		try {
			File fileList = new File(workCatalog + File.separator + nameFile + ".xls");
			if (!fileList.exists())
				xls.createWorkbook(title);
			xls.parametrsFile(0);
			int row = xls.getCountRow();
			xls.open(0);
			xls.writeLabel(0, row, barcode);
			xls.writeLabel(1, row, dapo);
			xls.writeLabel(2, row, nameF);
			xls.writeLabel(3, row, adrF);
			xls.writeLabel(4, row, otvet);
			xls.close();
		} catch(Exception e) { System.out.println(e); }
	}
	
}