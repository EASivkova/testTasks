package data_capture;

import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

import org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton;

import ru.svrw.eivc.date.Calendar;

public class OptionalPeriod extends Frame implements ActionListener, ItemListener {

	private Button bStart;
	private TextField tfEndDay, tfStartDay;
	// Последний день периода, Первый день периода
	private JCalendarButton jcbStartDay, jcbEndDay;
	// кнопка календаря для начальной даты, для конечной даты периода
	private Checkbox сbESPP, сbKASANT, сbKASAT, сbDO13, сbDO14, сbDO24;
	private Label result = new Label("");
	private Label bug = new Label("");
	private Log log = new Log();

	OptionalPeriod() {
		setLayout(new GridBagLayout());
		add(new Label("Укажите период для синхронизации данных:"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
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
		add(pPeriod, new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		add(new Label("Укажите системы для синхронизации данных:"), new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
			сbESPP = new Checkbox("ЕСПП");
			сbESPP.addItemListener(this);
		add(сbESPP, new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			сbKASANT = new Checkbox("КАСАНТ");
			сbKASANT.addItemListener(this);
		add(сbKASANT, new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			сbKASAT = new Checkbox("КАСАТ");
			сbKASAT.addItemListener(this);
		add(сbKASAT, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			сbDO13 = new Checkbox("ДО-13");
			сbDO13.addItemListener(this);
		add(сbDO13, new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			сbDO14 = new Checkbox("ДО-14");
			сbDO14.addItemListener(this);
		add(сbDO14, new GridBagConstraints(0, 7, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			сbDO24 = new Checkbox("ДО-24ВЦ");
			сbDO24.addItemListener(this);
		add(сbDO24, new GridBagConstraints(0, 8, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(1, 5, 1, 5), 0, 0));
			bStart = new Button("Синхронизировать данные");
			bStart.addActionListener(this);
		add(bStart, new GridBagConstraints(0, 9, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		add(result, new GridBagConstraints(0, 10, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		add(bug, new GridBagConstraints(0, 11, 1, 1, 0, 0, GridBagConstraints.NORTH,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
		// Создание окна
		setTitle("Загрузка данных из систем за произвольный период");
		setSize(350, 380);
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
		OptionalPeriod opt = new OptionalPeriod();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == bStart) {
			bug.setText("");
			String startDay = tfStartDay.getText();
			String endDay = tfEndDay.getText();
			if (!endDay.equals("") && endDay != null && !startDay.equals("") && startDay != null) {
				result.setText("Пожалуйста, подождите. Программа работает...");
				boolean synchronization = false;
				Calendar today = new Calendar();
				int start_day = Integer.parseInt(startDay.substring(0, 2));
				int start_month = Integer.parseInt(startDay.substring(3, 5));
				int end_day = Integer.parseInt(endDay.substring(0, 2));
				int end_month = Integer.parseInt(endDay.substring(3, 5));
				if (start_day == 1 && (end_day == today.getDay() && start_month == end_month || end_day == 1 && ((end_month - start_month) == 1 && end_month != 1 || end_month == 1 && start_month == 12))) {
					synchronization = true;
				}
				Timestamp begin = Timestamp.valueOf(startDay.substring(6) + "-" + startDay.substring(3, 5) + "-" + startDay.substring(0, 2) + " 00:00:00.000000000");
				Timestamp end = Timestamp.valueOf(endDay.substring(6) + "-" + endDay.substring(3, 5) + "-" + endDay.substring(0, 2) + " 00:00:00.000000000");
				WorkReport bdReport = new WorkReport();
				if (bdReport.testingCon()) {
					log.setConsole("OptionalPeriod.actionPerformed", "БД Report работает");
					if (сbESPP.getSelectedObjects() != null) {
						WorkESPP bdESPP = new WorkESPP();
						if (bdESPP.testingCon()) {
							log.setConsole("OptionalPeriod.actionPerformed", "БД ЕСПП работает");
							bdReport.deleteIncidents(begin, end);
							bdESPP.readDataFromTableIncidets(begin, end);
						} else {
							log.setConsole("OptionalPeriod.actionPerformed", "БД ЕСПП не работает");
							bug.setText("БД ЕСПП не работает");
						}
					}
					if (сbKASANT.getSelectedObjects() != null || сbKASAT.getSelectedObjects() != null || сbDO13.getSelectedObjects() != null || сbDO14.getSelectedObjects() != null || сbDO24.getSelectedObjects() != null) {
						WorkXLS xls = new WorkXLS();
						int countDay = bdReport.countDay(begin, end);
						String[] ddmmyyyy = new String[countDay];
						Timestamp[] yyyy_mm_dd = new Timestamp[countDay];
						Calendar temp = new Calendar();
						temp.setDate(Integer.parseInt(startDay.substring(6)), Integer.parseInt(startDay.substring(3, 5)), Integer.parseInt(startDay.substring(0, 2)));
						temp.setTime(0, 0, 0);
						int sumKASANT = 0;
						for (int i = 0; i < countDay; i++) {
							yyyy_mm_dd[i] = Timestamp.valueOf(temp.getDBDate() + " 00:00:00.000000000");
							if (temp.getDay() < 10)
								ddmmyyyy[i] = "0";
							else
								ddmmyyyy[i] = "";
							ddmmyyyy[i] += temp.getDay();
							if (temp.getMonth() < 10)
								ddmmyyyy[i] += "0";
							ddmmyyyy[i] += temp.getMonth() + "" + temp.getYear();
							temp.addDay(1);
							if (сbDO13.getSelectedObjects() != null) {
								int countDO13 = xls.setCountDO13(ddmmyyyy[i]);
								if (countDO13 > 0)
									bdReport.updEVENT(yyyy_mm_dd[i], countDO13, "ДО-13");
							}
							if (сbDO14.getSelectedObjects() != null) {
								int countDO14 = xls.setCountDO14(ddmmyyyy[i]);
								if (countDO14 > 0)
									bdReport.updEVENT(yyyy_mm_dd[i], countDO14, "ДО-14");
							}
							if (сbDO24.getSelectedObjects() != null) {
								int countDO24 = xls.setCountDO24(ddmmyyyy[i]);
								if (countDO24 > 0)
									bdReport.updEVENT(yyyy_mm_dd[i], countDO24, "ДО-24ВЦ");
							}
							WorkKASANT bdKASANT = new WorkKASANT();
							if (сbKASANT.getSelectedObjects() != null || сbKASAT.getSelectedObjects() != null) {
								if (bdKASANT.testingCon()) {
									log.setConsole("OptionalPeriod.actionPerformed", "БД KASANT работает");
									if (сbKASANT.getSelectedObjects() != null) {
										int countKASANT = bdKASANT.countVIOL(yyyy_mm_dd[i], Timestamp.valueOf(temp.getDBDate() + " 00:00:00.000000000"));
										sumKASANT += countKASANT;
										if (countKASANT > 0)
											bdReport.updEVENT(yyyy_mm_dd[i], countKASANT, "КАСАНТ");
										if (sumKASANT < bdReport.summEvent(begin, Timestamp.valueOf(temp.getDBDate() + " 00:00:00.000000000"), "КАСАНТ")) {
											Calendar temp2 = temp;
											temp2.addDay(-1);
											bdReport.deleteEVENT(Timestamp.valueOf(temp2.getDBDate() + " 00:00:00.000000000"), "КАСАНТ");
										}
									}
									if (сbKASAT.getSelectedObjects() != null) {
										int countKASAT = bdKASANT.countTechBreak(yyyy_mm_dd[i], Timestamp.valueOf(temp.getDBDate() + " 00:00:00.000000000"));
										if (countKASAT > 0)
											bdReport.updEVENT(yyyy_mm_dd[i], countKASAT, "КАСАТ");
									}
								} else {
									log.setConsole("OptionalPeriod.actionPerformed", "БД KASANT не работает");
									bug.setText("БД KASANT не работает");
								}
							}
						}
					}
				} else {
					log.setConsole("OptionalPeriod.actionPerformed", "БД Report не работает");
					bug.setText("БД Report не работает");
				}
				if (synchronization) {
					if (сbESPP.getSelectedObjects() != null) {
						bdReport.synchronization(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 0, "ESPP");
					}
					if (сbKASANT.getSelectedObjects() != null) {
						bdReport.synchronization(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 0, "KASANT");
					}
					if (сbKASAT.getSelectedObjects() != null) {
						bdReport.synchronization(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 0, "KASAT");
					}
					if (сbDO13.getSelectedObjects() != null) {
						bdReport.synchronization(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 0, "DO13");
					}
					if (сbDO14.getSelectedObjects() != null) {
						bdReport.synchronization(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 0, "DO14");
					}	
					if (сbDO24.getSelectedObjects() != null) {
						bdReport.synchronization(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 0, "DO24");
					}
				}
				result.setText("Программа закончила работу.");
			} else {
				bug.setText("Пожалуйста, укажите период синхронизации данных.");
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {
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

}
