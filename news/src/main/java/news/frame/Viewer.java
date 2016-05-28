package news.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.*;

import org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton;
import news.dao.NewsDao;
import news.entities.News;
import news.entities.Rubric;
import news.parser.E1;
import news.parser.Ria;

public class Viewer extends Frame implements ActionListener, ItemListener {

	/**
	 * @param args
	 */
	private JTable table; // таблица на основе списка контрагентов
	private JButton bloadNews, bAllNews, bSelect, bFind, bClear;
	private JComboBox jcbRubric;
	private TextField tfDate, tfSearch;
	private JCalendarButton jcbDate;
	
	Viewer() {
		setLayout(new BorderLayout());
		add("North", getPanel_Button());
		add("Center", getPanel_Table());
		Panel pReport = new Panel();
		pReport.setLayout(new BorderLayout());
		add("South", pReport);
		// Создание окна
		setTitle("Обработка финансовых документов");
		setSize(950, 750);
		setBackground(Color.getHSBColor(100, 200, 50));
		setForeground(Color.black);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Viewer v = new Viewer();
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		NewsDao dao = new NewsDao();
		if (ae.getSource() == bClear) {
			tfDate.setText("");
			tfSearch.setText("");
			jcbRubric.setSelectedIndex(0);
		}
		if (ae.getSource() == bloadNews) {
			Ria ria = new Ria();
			E1 e1 = new E1();
			JOptionPane.showMessageDialog(new JFrame(), "Загрузка новостей в систему завершена.", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
		if (ae.getSource() == bAllNews) {
			List<News> newsList = dao.listNews();
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
	        dtm.setRowCount(0);
	        for (News news : newsList) {
	       		Vector<String> row = new Vector<String>();
	       		row.add(news.getRubric().getName());
	       		row.add(news.getDat().toString());
	       		row.add("<html>" + news.getHeader() + "<br>" + news.getBody() + "</html>");
	       		dtm.addRow(row); //добавляем в таблицу вектор 
	        }
		}
		if (ae.getSource() == bSelect && tfDate.getText() != null && !tfDate.getText().equals("")) {
			String str = tfDate.getText();
			Date dat = java.sql.Date.valueOf(str.substring(6, 10) + "-" + str.substring(3, 5) + "-" + str.substring(0, 2));
			List<News> newsList = dao.findNews(dat);
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
	        dtm.setRowCount(0);
	        for (News news : newsList) {
	       		Vector<String> row = new Vector<String>();
	       		row.add(news.getRubric().getName());
	       		row.add(news.getDat().toString());
	       		row.add("<html>" + news.getHeader() + "<br>" + news.getBody() + "</html>");
	       		dtm.addRow(row); //добавляем в таблицу вектор 
	        }
		}
		if (ae.getSource() == bSelect && (tfDate.getText() == null || tfDate.getText().equals(""))) {
			String name = (String) jcbRubric.getSelectedItem();
			Rubric rubric = dao.getRubric(name);
			List<News> newsList = rubric.getNewsList();
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
	        dtm.setRowCount(0);
	        for (News news : newsList) {
	       		Vector<String> row = new Vector<String>();
	       		row.add(news.getRubric().getName());
	       		row.add(news.getDat().toString());
	       		row.add("<html>" + news.getHeader() + "<br>" + news.getBody() + "</html>");
	       		dtm.addRow(row); //добавляем в таблицу вектор 
	        }
		}
		if (ae.getSource() == bFind && tfSearch.getText() != null && !tfSearch.getText().equals("")) {
			String strSearch = tfSearch.getText();
	        System.out.println(strSearch);
/*			strSearch = strSearch.replaceAll(".", " ");
			strSearch = strSearch.replaceAll(",", " ");
			strSearch = strSearch.replaceAll("-", " ");
			strSearch = strSearch.replaceAll("!", " ");
			strSearch = strSearch.replaceAll(":", " ");
			strSearch = strSearch.replaceAll(";", " "); */
			String[] temp = strSearch.split(" ");
			String text = "";
			for (String s : temp) {
				text += s + " & ";
			}
			if (text.length() == 0)
				text = strSearch;
			else
				text = text.substring(0, text.length() - 3);
	        System.out.println(text);
			List<News> newsList = dao.findNews(text);
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();
	        dtm.setRowCount(0);
	        for (News news : newsList) {
	       		Vector<String> row = new Vector<String>();
	       		row.add(news.getRubric().getName());
	       		row.add(news.getDat().toString());
	       		row.add("<html>" + news.getHeader() + "<br>" + news.getBody() + "</html>");
	       		dtm.addRow(row); //добавляем в таблицу вектор 
	        }
		}
	}

	private Panel getPanel_Table() {
		Panel panel = new Panel(new GridBagLayout());
		table = new JTable(getDataNull(), getHeader()) {
			@Override
			public boolean isCellEditable ( int row, int column ) {
				return false;
			}
		};
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		Panel pFilter = new Panel();
		final TextField tfFilter = new TextField("", 40);
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
		panel.add(pFilter, new GridBagConstraints(0, 0, 1, 1, 0, 1, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(2, 5, 1, 5), 0, 0));
		table.setRowHeight(100);
		table.getTableHeader().setReorderingAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn column = null;
		for (int i = 0; i < 3; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i == 0)
				column.setPreferredWidth(120); //third column is bigger
			if (i == 1)
				column.setPreferredWidth(80); //third column is bigger
			if (i == 2)
				column.setPreferredWidth(750);
		}
		table.doLayout();
		table.setFont(new Font("Serif", Font.ROMAN_BASELINE, 14));
		panel.add(new JScrollPane(table), new GridBagConstraints(0, 1, 1, 1, 1, 7, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
		return panel;
	}
	
	private Panel getPanel_Button() {
		Panel panel = new Panel(new GridBagLayout());
		bloadNews = new JButton("Загрузить новости");
		bloadNews.addActionListener(this);
		panel.add(bloadNews, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		bAllNews = new JButton("Показать все новости");
		bAllNews.addActionListener(this);
		panel.add(bAllNews, new GridBagConstraints(4, 0, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		bClear = new JButton("Очистить поля");
		bClear.addActionListener(this);
		panel.add(bClear, new GridBagConstraints(5, 0, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		jcbRubric = new JComboBox(getRubricList());
		jcbRubric.setSelectedIndex(0);
		jcbRubric.addItemListener(this);
		panel.add(new Label("Рубрика:"), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(jcbRubric, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		tfDate = new TextField("", 10);
		tfDate.setEditable(false);
		tfDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                String date = tfDate.getText();
                setDate(date);
            }
        });
		panel.add(new Label("Дата:"), new GridBagConstraints(3, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(tfDate, new GridBagConstraints(4, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		jcbDate = new JCalendarButton();
		jcbDate.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if (evt.getNewValue() instanceof Date)
                    setDate((Date)evt.getNewValue());
            }
        });
		panel.add(jcbDate, new GridBagConstraints(5, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		bSelect = new JButton("Выполнить выборку");
		bSelect.addActionListener(this);
		panel.add(bSelect, new GridBagConstraints(6, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		tfSearch = new TextField("", 80);
		panel.add(new Label("Поиск:"), new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(tfSearch, new GridBagConstraints(1, 2, 5, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		bFind = new JButton("Выполнить поиск");
		bFind.addActionListener(this);
		panel.add(bFind, new GridBagConstraints(6, 2, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(new Label("Источники: E1.ru, РИА-новости"), new GridBagConstraints(0, 3, 4, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		return panel;
	}
	
	public static DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
    /**
     * Validate and set the datetime field on the screen given a datetime string.
     * @param dateTime The datetime string
     */
	public void setDate(String dateString) {
		Date date = null;
		try	{
            if ((dateString != null) && (dateString.length() > 0))
                date = dateFormat.parse(dateString);
		} catch (Exception e)	{
            date = null;
		}
		this.setDate(date);
    }
    /**
     * Validate and set the datetime field on the screen given a date.
     * @param dateTime The datetime object
     */
	public void setDate(Date date) {
        String dateString = "";
        if (date != null)
    		dateString = dateFormat.format(date);
       	tfDate.setText(dateString);
       	jcbDate.setTargetDate(date);
    }

    private Vector<String> getHeader() {
		Vector<String> column = new Vector<String>();
		column.add("Рубрика");
		column.add("Дата");
		column.add("Новость");
		return column;
	}
	
	// содержимое таблицы (пусто)
	private Vector getDataNull() {
		Vector dat = new Vector();
		return dat;
	}
	
	private String[] getRubricList() {
		String[] rerult;
		NewsDao dao = new NewsDao();
		List<Rubric> list = dao.listRubric();
		if (list.size() > 0) {
			rerult = new String[list.size() + 1];
			rerult[0] = "";
			for (int i = 1; i <= list.size(); i++)
				rerult[i] = (String) list.get(i - 1).getName();
		} else {
			rerult = new String[1];
			rerult[0] = "";
		}
		return rerult;
	}
	
}
