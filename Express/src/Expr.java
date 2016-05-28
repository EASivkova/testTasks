import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.BoxLayout;

import ru.svrw.eivc.util.*;
import ru.svrw.eivc.date.Calendar;
import ru.svrw.logged.Log;
import ru.svrw.sql.ds.BufferPool;
import ru.svrw.sql.rowset.CachedRowSet;
import ru.svrw.util.Properties;

import jxl.*;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Expr extends Frame implements ActionListener, ItemListener {
	
	Button submitButton, formFile, subObzor;
	TextField doroga, month, year, adrFiles;
	int dor, y;
	String adres, mon;
	String error = "", n = "";
	Checkbox[] cbMovement = new Checkbox[3];
	Checkbox[] cbViewPass = new Checkbox[11];
	Label result = new Label("");
	BufferedReader br;
	boolean parse = false, pass = false, itog = false;
	boolean[] bViewPass = {false, false, false, false, false, false, false, false, false, false, false};
    static Expr f;
    // создадим пул коннектов с логированием, параметры возьмем из файла свойств пусть у нас в одном файле лежат настройки для лога и пула
    Log log = new Log("${pathProject}/conf/BufferPool.properties");
    BufferPool bp;
	// Создадим еще один объект логирования для наших запросов лучше не смешивать логи работы этого класса с пулом коннектов
	// пусть у нас в одном файле лежат и настройки логирования и SQL
    Log log2 = new Log("${pathProject}/conf/CachRowSet_.properties");
	// объект для работы с фалами свойств, будем оттуда загружать SQL чтобы не хранить SQL в коде. Все содержимое файлов свойств см. после примера.
    Properties prop = new Properties("${pathProject}/conf/CachRowSet_.properties");
    CachedRowSet crs;
    
    Expr() {
		setLayout(new BorderLayout());
		Panel opisanie = new Panel();
			doroga = new TextField("", 5);
				opisanie.add(new Label("Дорога:"));
				opisanie.add(doroga);
			month = new TextField("", 5);
				opisanie.add(new Label("Месяц:"));
				opisanie.add(month);
			year = new TextField("", 5);
				opisanie.add(new Label("Год:"));
				opisanie.add(year);
		add("North", opisanie);
		Panel param = new Panel();
		Panel obzor = new Panel();
			adrFiles = new TextField("C:" + File.separator + "_Express" + File.separator, 30);
				obzor.add(new Label("Путь к каталогу с файлами:"));
				obzor.add(adrFiles);
			subObzor = new Button("Обзор..");
				subObzor.addActionListener(this);
				obzor.add(subObzor);
		param.add("North", obzor);
		Panel checkboxs = new Panel();
			checkboxs.setLayout(new GridLayout(3, 1));
			cbMovement[0] = new Checkbox("Чтение txt-файлов и запись их содержимого в БД");
				cbMovement[0].addItemListener(this);
				checkboxs.add(cbMovement[0]);
			cbMovement[1] = new Checkbox("Формирование файлов по видам пассажиров");
				cbMovement[1].addItemListener(this);
				checkboxs.add(cbMovement[1]);
			cbMovement[2] = new Checkbox("Формирование итогового файла");
				cbMovement[2].addItemListener(this);
				checkboxs.add(cbMovement[2]);
		param.add("Center", checkboxs);
		add("Center", param);
		Panel buttons = new Panel();
			buttons.setLayout(new BorderLayout());
			submitButton = new Button("Выполнить");
				submitButton.addActionListener(this);
				buttons.add("North", submitButton);
			Panel chbox = new Panel();
			chbox.setLayout(new GridLayout(7, 2));
				chbox.add(new Label());
				chbox.add(new Label());
			cbViewPass[0] = new Checkbox("работники орг-ций, финанс. ОАО РЖД");
				cbViewPass[0].addItemListener(this);
				chbox.add(cbViewPass[0]);
			cbViewPass[1] = new Checkbox("дети, на иждевении раб. ОАО РЖД");
				cbViewPass[1].addItemListener(this);
				chbox.add(cbViewPass[1]);
			cbViewPass[2] = new Checkbox("пенсионеры");
				cbViewPass[2].addItemListener(this);
				chbox.add(cbViewPass[2]);
			cbViewPass[3] = new Checkbox("работники ОАО РЖД");
				cbViewPass[3].addItemListener(this);
				chbox.add(cbViewPass[3]);
			cbViewPass[4] = new Checkbox("иные лица");
				cbViewPass[4].addItemListener(this);
				chbox.add(cbViewPass[4]);
			cbViewPass[5] = new Checkbox("дети, родители которых погибли");
				cbViewPass[5].addItemListener(this);
				chbox.add(cbViewPass[5]);
			cbViewPass[6] = new Checkbox("дети, на иждевении у нераб. ОАО РЖД");
				cbViewPass[6].addItemListener(this);
				chbox.add(cbViewPass[6]);
			cbViewPass[7] = new Checkbox("пенсионеры OAO ФПК");
				cbViewPass[7].addItemListener(this);
				chbox.add(cbViewPass[7]);
			cbViewPass[8] = new Checkbox("работники ОАО ФПК");
				cbViewPass[8].addItemListener(this);
				chbox.add(cbViewPass[8]);
			cbViewPass[9] = new Checkbox("дети, на иждевении раб. ОАО ФПК");
				cbViewPass[9].addItemListener(this);
				chbox.add(cbViewPass[9]);
			cbViewPass[10] = new Checkbox("дети, на иждевении у нераб. ОАО ФПК");
				cbViewPass[10].addItemListener(this);
				chbox.add(cbViewPass[10]);
				buttons.add("Center", chbox);
			formFile = new Button("Сформировать файлы");
				formFile.addActionListener(this);
				buttons.add("South", formFile);
		param.add("South", buttons);
		Panel status = new Panel();
		result = new Label("");
			status.add(result);
		add("South", status);
		// Создание окна
		setTitle("Обработка реестра пригорода");
		setSize(465, 450);
		setBackground(Color.lightGray);
		setForeground(Color.black);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == subObzor) {
			FileDialog fd = new FileDialog(f);
			fd.setVisible(true);
			adres = fd.getDirectory();
			adres = adres.replaceAll("/", File.separator);
			adrFiles.setText(adres);
		}
		if (ae.getSource() == submitButton) {
			dor = Integer.parseInt(doroga.getText());
			mon = month.getText();
			y = Integer.parseInt(year.getText());
			adres = adrFiles.getText();
	        Calendar start = new Calendar();
	        bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
	        crs = new CachedRowSet(log2);
	        if (parse)
	        	readTXT();
	        if (pass && error.equals(""))
	        	createFilePass();
	        if (itog && error.equals(""))
	        	itogo();
			bp.close();
	        Calendar end = new Calendar();
	        int dx = (end.getYear() - start.getYear())*12*31*24*60 + (end.getMonth() - start.getMonth())*31*24*60 + (end.getDay() - start.getDay())*24*60 + (end.getHour() - start.getHour())*60 + (end.getMinute() - start.getMinute());
			if (!error.equals(""))
				result.setText("Ошибка: " + error + ". ");
	        result.setText(result.getText() + "Обработка файлов закончена. Время работы программы: " + dx/60 + " часов " + dx%60 + " минут");
		}
		if (ae.getSource() == formFile) {
			dor = Integer.parseInt(doroga.getText());
			mon = month.getText();
			y = Integer.parseInt(year.getText());
			adres = adrFiles.getText();
	        Calendar start = new Calendar();
	        bp = new BufferPool("${pathProject}/conf/BufferPool.properties", log);
	        crs = new CachedRowSet(log2);
	        for (int i = 0; i < cbViewPass.length; i++)
	        	if (bViewPass[i])
	        		createFilePass(i+1);
			bp.close();
	        Calendar end = new Calendar();
	        int dx = (end.getYear() - start.getYear())*12*31*24*60 + (end.getMonth() - start.getMonth())*31*24*60 + (end.getDay() - start.getDay())*24*60 + (end.getHour() - start.getHour())*60 + (end.getMinute() - start.getMinute());
			result.setText("Обработка файлов закончена. Время работы программы: " + dx/60 + " часов " + dx%60 + " минут");
		}
	}
	
	public void itemStateChanged(ItemEvent ie) {
		if (ie.getSource() == cbMovement[0]) {
			if (cbMovement[0].getSelectedObjects() != null)
				parse = true;
			else
				parse = false;
		}
		if (ie.getSource() == cbMovement[1]) {
			if (cbMovement[1].getSelectedObjects() != null)
				pass = true;
			else
				pass = false;
		}
		if (ie.getSource() == cbMovement[2]) {
			if (cbMovement[2].getSelectedObjects() != null)
				itog = true;
			else
				itog = false;
		}
		for (int i = 0; i < cbViewPass.length; i++)
			if (ie.getSource() == cbViewPass[i]) {
				if (cbViewPass[i].getSelectedObjects() != null)
					bViewPass[i] = true;
				else
					bViewPass[i] = false;
			}
	}
	
	public static void main(String[] args) {
		f = new Expr();
	}
	
	public void readTXT() {
        String[] nameFile = "".split("");
        String perevozchik, viewTr = "", viewPas;
        boolean oneFio = true;
    	File file, f;
        try {
        	file = new File(adres);
        	if (file.isDirectory())
        		nameFile = file.list(new FilenameFilter() {
        			private Pattern p = Pattern.compile("\\w+\\.txt");
        			public boolean accept(File dir, String name) {
        				return p.matcher(name).matches();
        			}
        		});
        	for (int j = 0; j < nameFile.length; j++) {
       			f = new File(adres + File.separator + nameFile[j]);
       			if (f.length() < 3000 || nameFile[j].equals("PP.txt") || nameFile[j].equals("PR.txt") || nameFile[j].indexOf("RZD.txt") > -1)
       				if (!f.delete())
       					System.out.println("Файл " + nameFile[j] + " не удалось удалить");
        	}
        	file = new File(adres);
        	if (file.isDirectory())
        		nameFile = file.list(new FilenameFilter() {
        			private Pattern p = Pattern.compile("\\w+\\.txt");
        			public boolean accept(File dir, String name) {
        				return p.matcher(name).matches();
        			}
        		});
        	for (int j = 0; j < nameFile.length; j++) {
        		if (nameFile[j].indexOf("1") > 0)
        			perevozchik = "ОАО ФПК";
        		else
        			perevozchik = "ОАО РЖД";
        		viewPas = "работники " + perevozchik;
        		if (nameFile[j].indexOf("BSL") == 0)
        			viewTr = "по решению руководства " + perevozchik;
        		if (nameFile[j].indexOf("DSL") == 0)
        			viewTr = "по договорам с " + perevozchik;
        		if (nameFile[j].indexOf("LN") == 1 && nameFile[j].indexOf("PLN") < 0)
        			viewTr = "по личным надобностям";
        		if (nameFile[j].indexOf("ROM") == 0)
        			viewTr = "служебные по открытым и маршрутным листам";
        		if (nameFile[j].indexOf("RRU") == 0)
        			viewTr = "от дома до работы/учебы";
        		if (nameFile[j].indexOf("RSN") == 0)
        			viewTr = "служебные";
        		if (nameFile[j].indexOf("BSL") == 0 || nameFile[j].indexOf("DSL") == 0)
        			viewPas = "иные лица";
        		if (nameFile[j].indexOf("FSL") == 0)
        			viewPas = "работники организаций, финансируемых " + perevozchik;
        		if (nameFile[j].indexOf("ILN") == 0)
        			viewPas = "дети, находящиеся на иждевении работников и пенсионеров " + perevozchik;
        		if (nameFile[j].indexOf("GLN") == 0)
        			viewPas = "дети, находящиеся на иждевении у неработающих пенсионеров " + perevozchik;
        		if (nameFile[j].indexOf("NLN") == 0 && nameFile[j].indexOf("1") < 0)
        			viewPas = "дети, родители которых погибли в результате ж.д. аварий";
        		if (nameFile[j].indexOf("NLN") == 0 && nameFile[j].indexOf("1") > -1)
        			viewPas = "дети, родители которых являлись сотрудниками ФПК и погибли в результате ж.д. аварий";
        		if (nameFile[j].indexOf("PLN") == 0)
        			viewPas = "пенсионеры " + perevozchik;
        		if (nameFile[j].indexOf("ILN") == 0 || nameFile[j].indexOf("GLN") == 0)
        			oneFio = false;
                try {
                	n = "";
                    br = new BufferedReader(new FileReader(adres + File.separator + nameFile[j])); // берем в буфер содержимое файла - nameFileRead
                    if ((nameFile[j].indexOf("SP.") > -1) || (nameFile[j].indexOf("SP1.") > -1))
                    	parse(7, 0, 10, 11, 12, 13, 5, oneFio, nameFile[j], 13, 2, 1, 3, 4, 0, 7000, 0, viewTr, viewPas, 8, 9, 6);
                    else
                    	parse(7, 8, 13, 14, 15, 0, 5, oneFio, nameFile[j], 15, 2, 1, 3, 4, 10, 6000, 11, viewTr, viewPas, 9, 12, 6);
                } catch (Exception e) {
                    System.out.println("Error open file: " + nameFile[j]);
                    error += nameFile[j] + "(" + n + "), ";
                }
                br.close();
                if (error.indexOf(nameFile[j]) < 0) {
           			f = new File(adres + File.separator + nameFile[j]);
       				if (!f.delete())
       					System.out.println("Файл " + nameFile[j] + " не удалось удалить");
                }
                oneFio = true;
                viewTr = "";
        	}
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	
    /* номера колонок с датами, с назв. станций (отправ., назначения), со стоимостью, c доплатой, с фамилией, признак одной фамилии, название файла, 
     * количество колонок в строке сохраняемой в БД, номер колонки с формой трансп. требования, с наименованием перевозчика, 
     * с кодом организации в к-й работает, с кодом подразделения в к-м работает, с указанием способа оформления, номер поезда (6000-е или 7000-е), 
     * номер колонки с указанием вида документа, вид перевозок (служебные и т.д.), вид пассажира, номер колонки с указанием номера документа, 
     * с кол-вом билетов, с номером требования
     */
    private void parse(int dat, int dat_st, int st_ot, int st_n, int fare, int summ, int fio, boolean oneFio, String nameFileRead, int countColumns, 
    		int form_tr, int transp, int code_org, int code_podr, int registr, int num_p, int view_doc, String view_tr, String view_pass, int num_doc, 
    		int kol, int num_tr) {
        String[] strArr = "".split("");
        String strTmp = "";
        try {
	       String sql = prop.getString("sql.select");
	       boolean first = true;
	       int numStrCell = 0;
	       int for_ind = 0, tra_ind = 0, org_ind = 0, pod_ind = 0, reg_ind = 0, vd_ind = 0, vt_ind = 0, vp_ind = 0;
	       String[] st = "".split("");
	       String sta_otp = "", sta_n = "";
	       String famOne = "", famTwo = "";
	       int kolRow = 0;
	       if (br != null)
	    	   while ((strTmp = br.readLine()) != null) { // прочитали строку из файла (если не пустая то цикл)
	    		   numStrCell++;
	    		   if (strTmp.indexOf("-0") > -1) strTmp = strTmp.replaceAll(" -0", " !-");
	    		   if (strTmp.indexOf("--") >= 0) kolRow++;
	    		   if (kolRow >= 3) {
	    			   if (strTmp.indexOf("--") > -1) { // поиск в строке номера положения указаной подстроки (то есть проверка условия наличия такой подстроки в строке)
	    				   numStrCell = 0;
	    				   if (strArr.length == countColumns + 1) {
	    					   strArr[0] = null;
	    					   for (int i = 1; i < strArr.length; i++) { // записываем значение элементов массива в базу данных
	    						   if (strArr[i].length() == -1 || strArr[i].trim().equals("") || strArr[i].trim().equals("''")) strArr[i] = null;
	    						   if (i == form_tr) {
	    							   sql = prop.getString("sql.sel_FormTr");
	    							   if (sql == null) {
	    								   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    								   break;
	    							   }
	    							   crs.setSQL(sql); // установим SQL и параметры
	    							   crs.addParameter(strArr[i].trim());
	    							   if (bp.executeQuery(crs) && !crs.isError()) { // если запрос выполнился успешно и не было ошибок, то ... 
	    								   if (crs.size() > 0) { // если количество записей больше 0, то ...
	    									   while (crs.next()) for_ind = crs.getInteger(1);
	    								   } else {
	    									   sql = prop.getString("sql.sel_maxFormTr");
	    									   if (sql == null) {
	    										   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    										   break;
	    									   }
	    									   crs.setSQL(sql);
	    									   if (bp.executeQuery(crs) && !crs.isError()) {
	    										   while (crs.next()) for_ind = crs.getInteger(1,0) + 1;
	    										   sql = prop.getString("sql.ins_FormTr");
	    										   if (sql == null) {
	    											   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    											   break;
	    										   }
	    										   crs.setSQL(sql);
	    										   crs.addParameter(strArr[i].trim());
	    										   crs.addParameter(for_ind);
	    										   if (!bp.updateQuery(crs) || crs.isError()) { // выполним SQL, если были ошибки, то завершаем работу
	    											   log2.error(crs.getException());
	    											   break;
	    										   }
	    									   } else { // если была ошибка, то распечатаем 
	    										   log2.error(crs.getException());
	    										   break;
	    									   }
	    								   }
	    							   } else {
	    								   log2.error(crs.getException());
	    								   break;
	    							   }
	    						   }
	    						   if (i == transp) {
	    							   sql = prop.getString("sql.sel_transp");
	    							   if (sql == null) {
	    								   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    								   break;
	    							   }
	    							   crs.setSQL(sql);
	    							   if ((strArr[i].indexOf("6000") > -1) || (strArr[i].indexOf("7000") > -1))
	    								   crs.addParameter((strArr[i].substring(0, (strArr[i].trim()).length()-4)).trim());
	    							   else
	    								   crs.addParameter(strArr[i].trim());
	    							   if (strArr[i].indexOf("6000") > -1) num_p = 6000;
	    							   if (strArr[i].indexOf("7000") > -1) num_p = 7000;
	    							   if (bp.executeQuery(crs) && !crs.isError()) {
	    								   if (crs.size() > 0) {
	    									   while (crs.next()) tra_ind = crs.getInteger(1);
	    								   } else {
	    									   sql = prop.getString("sql.sel_maxTransp");
	    									   if (sql == null) {
	    										   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    										   break;
	    									   }
	    									   crs.setSQL(sql);
	    									   if (bp.executeQuery(crs) && !crs.isError()) {
	    										   while (crs.next()) tra_ind = crs.getInteger(1,0) + 1;
	    										   sql = prop.getString("sql.ins_transp");
	    										   if (sql == null) {
	    											   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    											   break;
	    										   }
	    										   crs.setSQL(sql);
	    										   if (strArr[i].indexOf("6000") > -1)
	    											   crs.addParameter((strArr[i].substring(0, (strArr[i].trim()).length()-4)).trim());
	    										   else
	    											   crs.addParameter(strArr[i].trim());
	    										   crs.addParameter(tra_ind);
	    										   if (!bp.updateQuery(crs) || crs.isError()) {
	    											   log2.error(crs.getException());
	    											   break;
	    										   }
	    									   } else {
	    										   log2.error(crs.getException());
	    										   break;
	    									   }
	    								   }
	    							   } else {
	    								   log2.error(crs.getException());
	    								   break;
	    							   }
	    						   }
	    						   if (i == code_org) {
	    							   sql = prop.getString("sql.sel_codeOrg");
	    							   if (sql == null) {
	    								   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    								   break;
	    							   }
	    							   crs.setSQL(sql);
	    							   crs.addParameter(strArr[i].trim());
	    							   if (bp.executeQuery(crs) && !crs.isError()) {
	    								   if (crs.size() > 0) {
	    									   while (crs.next()) org_ind = crs.getInteger(1);
	    								   } else {
	    									   sql = prop.getString("sql.sel_maxCodeOrg");
	    									   if (sql == null) {
	    										   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    										   break;
	    									   }
	    									   crs.setSQL(sql);
	    									   if (bp.executeQuery(crs) && !crs.isError()) {
	    										   while (crs.next()) org_ind = crs.getInteger(1,0) + 1;
	    										   sql = prop.getString("sql.ins_codeOrg");
	    										   if (sql == null) {
	    											   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    											   break;
	    										   }
	    										   crs.setSQL(sql);
	    										   crs.addParameter(strArr[i].trim());
	    										   crs.addParameter(org_ind);
	    										   if (!bp.updateQuery(crs) || crs.isError()) {
	    											   log2.error(crs.getException());
	    											   break;
	    										   }
	    									   } else {
	    										   log2.error(crs.getException());
	    										   break;
	    									   }
	    								   }
	    							   } else {
	    								   log2.error(crs.getException());
	    								   break;
	    							   }
	    						   }
	    						   if (i == code_podr) {
	    							   sql = prop.getString("sql.sel_codePodr");
	    							   if (sql == null) {
	    								   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    								   break;
	    							   }
	    							   crs.setSQL(sql);
	    							   crs.addParameter(strArr[i].trim());
	    							   if (bp.executeQuery(crs) && !crs.isError()) {
	    								   if (crs.size() > 0) {
	    									   while (crs.next()) pod_ind = crs.getInteger(1);
	    								   } else {
	    									   sql = prop.getString("sql.sel_maxCodePodr");
	    									   if (sql == null) {
	    										   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    										   break;
	    									   }
	    									   crs.setSQL(sql);
	    									   if (bp.executeQuery(crs) && !crs.isError()) {
	    										   while (crs.next()) pod_ind = crs.getInteger(1,0) + 1;
	    										   sql = prop.getString("sql.ins_codePodr");
	    										   if (sql == null) {
	    											   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    											   break;
	    										   }
	    										   crs.setSQL(sql);
	    										   crs.addParameter(strArr[i].trim());
	    										   crs.addParameter(pod_ind);
	    										   if (!bp.updateQuery(crs) || crs.isError()) {
	    											   log2.error(crs.getException());
	    											   break;
	    										   }
	    									   } else {
	    										   log2.error(crs.getException());
	    										   break;
	    									   }
	    								   }
	    							   } else {
	    								   log2.error(crs.getException());
	    								   break;
	    							   }
	    						   }
	    						   if (i == registr) {
	    							   sql = prop.getString("sql.sel_registr");
	    							   if (sql == null) {
	    								   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    								   break;
	    							   }
	    							   crs.setSQL(sql);
	    							   crs.addParameter(strArr[i].trim());
	    							   if (bp.executeQuery(crs) && !crs.isError()) {
	    								   if (crs.size() > 0) {
	    									   while (crs.next()) reg_ind = crs.getInteger(1);
	    								   } else {
	    									   sql = prop.getString("sql.sel_maxRegistr");
	    									   if (sql == null) {
	    										   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    										   break;
	    									   }
	    									   crs.setSQL(sql);
	    									   if (bp.executeQuery(crs) && !crs.isError())
	    									   {
	    										   while (crs.next()) reg_ind = crs.getInteger(1,0) + 1;
	    										   sql = prop.getString("sql.ins_registr");
	    										   if (sql == null) {
	    											   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    											   break;
	    										   }
	    										   crs.setSQL(sql);
	    										   crs.addParameter(strArr[i].trim());
	    										   crs.addParameter(reg_ind);
	    										   if (!bp.updateQuery(crs) || crs.isError()) {
	    											   log2.error(crs.getException());
	    											   break;
	    										   }
	    									   } else {
	    										   log2.error(crs.getException());
	    										   break;
	    									   }
	    								   }
	    							   } else {
	    								   log2.error(crs.getException());
	    								   break;
	    							   }
	    						   }
	    						   if (i == view_doc) {
	    							   sql = prop.getString("sql.sel_viewDoc");
	    							   if (sql == null) {
	    								   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    								   break;
	    							   }
	    							   crs.setSQL(sql);
	    							   crs.addParameter(strArr[i].trim());
	    							   if (bp.executeQuery(crs) && !crs.isError()) {
	    								   if (crs.size() > 0) {
	    									   while (crs.next()) vd_ind = crs.getInteger(1);
	    								   } else {
	    									   sql = prop.getString("sql.sel_maxViewDoc");
	    									   if (sql == null) {
	    										   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    										   break;
	    									   }
	    									   crs.setSQL(sql);
	    									   if (bp.executeQuery(crs) && !crs.isError()) {
	    										   while (crs.next()) vd_ind = crs.getInteger(1,0) + 1;
	    										   sql = prop.getString("sql.ins_viewDoc");
	    										   if (sql == null) {
	    											   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    											   break;
	    										   }
	    										   crs.setSQL(sql);
	    										   crs.addParameter(strArr[i].trim());
	    										   crs.addParameter(vd_ind);
	    										   if (!bp.updateQuery(crs) || crs.isError()) {
	    											   log2.error(crs.getException());
	    											   break;
	    										   }
	    									   } else {
	    										   log2.error(crs.getException());
	    										   break;
	    									   }
	    								   }
	    							   } else {
	    								   log2.error(crs.getException());
	    								   break;
	    							   }
	    						   }
	    						   if (i == st_ot) {
	    							   st = strArr[i].split(";");
	    							   if (st[0].indexOf("'") > -1)
	    								   st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
	    							   sql = prop.getString("sql.sel_station");
	    							   if (sql == null) {
	    								   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    								   break;
	    							   }
	    							   crs.setSQL(sql);
	    							   crs.addParameter(st[0].trim());
	    							   if (bp.executeQuery(crs) && !crs.isError()) {
	    								   if (crs.size() > 0) {
	    									   while (crs.next()) sta_otp = crs.getString(1);
	    								   } else {
	    									   sta_otp = st[1];
	    									   sql = prop.getString("sql.ins_station");
	    									   if (sql == null) {
	    										   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    										   break;
	    									   }
	    									   crs.setSQL(sql);
	    									   crs.addParameter(st[0].trim());
	    									   crs.addParameter(st[1]);
	    									   if (!bp.updateQuery(crs) || crs.isError()) {
	    										   log2.error(crs.getException());
	    										   error += " " + sql + " " + st[0].trim() + ", " + st[1];
	    										   break;
	    									   }
	    								   }
	    							   } else {
	    								   log2.error(crs.getException());
	    								   break;
	    							   }
	    						   }
	    						   if (i == st_n) {
	    							   st = strArr[i].split(";");
	    							   if (st[0].indexOf("'") > -1)
	    								   st[0] = st[0].substring(0, st[0].indexOf("'")) + "`" + st[0].substring(st[0].lastIndexOf("'") + 1);
	    							   sql = prop.getString("sql.sel_station");
	    							   if (sql == null) {
	    								   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    								   break;
	    							   }
	    							   crs.setSQL(sql);
	    							   crs.addParameter(st[0].trim());
	    							   if (bp.executeQuery(crs) && !crs.isError()) {
	    								   if (crs.size() > 0) {
	    									   while (crs.next()) sta_n = crs.getString(1);
	    								   } else {
	    									   sta_n = st[1];
	    									   sql = prop.getString("sql.ins_station");
	    									   if (sql == null) {
	    										   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    										   break;
	    									   }
	    									   crs.setSQL(sql);
	    									   crs.addParameter(st[0].trim());
	    									   crs.addParameter(st[1]);
	    									   if (!bp.updateQuery(crs) || crs.isError()) {
	    										   log2.error(crs.getException());
	    										   break;
	    									   }
	    								   }
	    							   } else {
	    								   log2.error(crs.getException());
	    								   break;
	    							   }
	    						   }
	    						   if (i == num_tr) {
	    							   strArr[i] = "'" + strArr[i] + "'";
	    						   }
	    						   if (i == fio) {
    								   while (strArr[i].indexOf("  ") > -1) {
    									   strArr[i] = strArr[i].replaceAll("  ", " ");
    								   }
	    							   if (!oneFio || strArr[i].indexOf(";") > -1) {
	    								   String[] z = strArr[i].split(";");
	    								   if (strArr[i].indexOf("=") > -1) {
	    									   famOne = z[0].trim();
	    									   famTwo = "'" + z[1].trim() + "'";
	    								   } else {
	    									   famOne = z[0].substring(0, z[0].indexOf(" ")).trim() + " " + z[0].substring(z[0].indexOf(" ") + 1);
	    									   famTwo = "'" + z[1].substring(0, z[1].indexOf(" ")).trim() + " " + z[1].substring(z[1].indexOf(" ") + 1) + "'";
	    								   }
	    							   } else {
	    								   if (strArr[i].indexOf("=") > -1) 
	    									   famOne = strArr[i].trim();
	    								   else 
	    									   famOne = strArr[i].substring(0,strArr[i].length() - 2).trim() + " " + strArr[i].substring(strArr[i].length() - 2);
	    								   famTwo = null;
	    							   }
	    						   }
	    					   }
	    					   sql = prop.getString("sql.sel_viewTr");
	    					   if (sql == null) {
	    						   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    						   break;
	    					   }
	    					   crs.setSQL(sql);
	    					   crs.addParameter(view_tr);
	    					   if (bp.executeQuery(crs) && !crs.isError()) {
	    						   if (crs.size() > 0) {
	    							   while (crs.next()) vt_ind = crs.getInteger(1);
	    						   } else {
	    							   sql = prop.getString("sql.sel_maxViewTr");
	    							   if (sql == null) {
	    								   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    								   break;
	    							   }
	    							   crs.setSQL(sql);
	    							   if (bp.executeQuery(crs) && !crs.isError()) {
	    								   while (crs.next()) vt_ind = crs.getInteger(1,0) + 1;
	    								   sql = prop.getString("sql.ins_viewTr");
	    								   if (sql == null) {
	    									   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    									   break;
	    								   }
	    								   crs.setSQL(sql);
	    								   crs.addParameter(view_tr);
	    								   crs.addParameter(vt_ind);
	    								   if (!bp.updateQuery(crs) || crs.isError()) {
	    									   log2.error(crs.getException());
	    									   break;
	    								   }
	    							   } else {
	    								   log2.error(crs.getException());
	    								   break;
	    							   }
	    						   }
	    					   } else {
	    						   log2.error(crs.getException());
	    						   break;
	    					   }
	    					   sql = prop.getString("sql.sel_viewPass");
	    					   if (sql == null) {
	    						   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    						   break;
	    					   }
	    					   crs.setSQL(sql);
	    					   crs.addParameter(view_pass);
	    					   if (bp.executeQuery(crs) && !crs.isError()) {
	    						   if (crs.size() > 0) {
	    							   while (crs.next()) vp_ind = crs.getInteger(1);
	    						   } else {
	    							   sql = prop.getString("sql.sel_maxViewPass");
	    							   if (sql == null) {
	    								   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    								   break;
	    							   }
	    							   crs.setSQL(sql);
	    							   if (bp.executeQuery(crs) && !crs.isError()) {
	    								   while (crs.next()) vp_ind = crs.getInteger(1,0) + 1;
	    								   sql = prop.getString("sql.ins_viewPass");
	    								   if (sql == null) {
	    									   log2.error("Error get SQL from file: " + log2.getAbsoluteFileName());
	    									   break;
	    								   }
	    								   crs.setSQL(sql);
	    								   crs.addParameter(view_pass);
	    								   crs.addParameter(vp_ind);
	    								   if (!bp.updateQuery(crs) || crs.isError()) {
	    									   log2.error(crs.getException());
	    									   break;
	    								   }
	    							   } else {
	    								   log2.error(crs.getException());
	    								   break;
	    							   }
	    						   }
	    					   } else {
	    						   log2.error(crs.getException());
	    						   break;
	    					   }
	    					   if (registr == 0) {
	    						   if (num_p == 7000) 
	    							   reg_ind = 2; 
	    					   }
	    					   n = strArr[num_doc];
	    					   if (vd_ind == 0)
	    						   if (reg_ind == 0) 
	    							   crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
	    								sta_n + ", " + vt_ind + ", null, " + num_p + ", null, " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
	    								vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
	    								famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + dor + ")");
	    						   else 
	    							   crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
	    								sta_n + ", " + vt_ind + ", null, " + num_p + ", " + reg_ind + ", " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
	    								vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
	    								famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + dor + ")");
	    					   else
	    						   if (reg_ind == 0) 
	    							   crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
	    								sta_n + ", " + vt_ind + ", " + vd_ind + ", " + num_p + ", null, " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
	    								vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
	    								famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + dor + ")");
	    						   else
	    							   crs.setSQL("insert into bundle values (" + strArr[dat] + ", '" + strArr[num_doc] + "', " + sta_otp + ", null, " + 
	    								sta_n + ", " + vt_ind + ", " + vd_ind + ", " + num_p + ", " + reg_ind + ", " + pod_ind + ", " + org_ind + ", " + tra_ind + ", " + 
	    								vp_ind + ", " + for_ind + ", null, " + strArr[kol].trim() + ", " + strArr[fare] + ", " + strArr[summ] + ", '" + 
	    								famOne + "', " + famTwo + ", " + strArr[dat_st] + ", null, " + strArr[num_tr] + "," + dor + ")");
	    					   if (!bp.updateQuery(crs) || crs.isError()) {
	    						   log2.error(crs.getException());
	    						   log2.debug("SQL:\n\t" + crs.getSQLAndParameters());
	    						   break;
	    					   }
	    				   }
	    				   strArr = "".split("");
	    				   first = true;
	    				   continue;
	    			   }
	    			   if (first) { // первая строка после "--"
	    				   first = false;
	    				   strArr = strTmp.split("!"); // массив значений между "!"
	    			   } else { // выполняется если в строке нет "--"
	    				   String[] tmp = strTmp.split("!"); // массив значений между "!"
	    				   for (int i = 0; i < tmp.length; i++) {
//                    		if ((i == fio) && (numStrCell == 2) && (strArr[i].length() == 15) && (strArr[i].indexOf(" ") < 0))
//                    			strArr[i] += " ";
	    					   if ((i == fio) && (numStrCell == 3))
	    						   strArr[i] += ";";
	    					   if (numStrCell == 2) {
	    						   if (st_ot == i || st_n == i)
	    							   strArr[i] = strArr[i] + ";"; // записываем разделитель между названием и номером станции (они храняться в однои элементе массива)
	    						   if (view_doc == i)
	    							   strArr[i] = strArr[i].trim() + " ";
	    					   }
	    					   if ((i == fio) && ((numStrCell == 2) || (numStrCell == 4)))
	    						   strArr[i] = strArr[i] + tmp[i];
	    					   else
	    						   strArr[i] = strArr[i] + tmp[i].trim();
                        // Для правильного представления даты: 08.01.2009  (ДД.ММ.ГГГГ)                         
	    					   if (numStrCell == 2)
	    						   if (dat == i || dat_st == i)
	    							   if (strArr[i].length() > 5)
	    								   strArr[i] = "'" + strArr[i].substring(0,2) + "." + strArr[i].substring(2,4) + ".20" + strArr[i].substring(4) + "'";
	    				   }                                    
	    			   }
	    		   }
	    	   }
        } catch (IOException ioe) {
            System.out.println("Error read file: " + nameFileRead);
        }
    }
    
    public void createFilePass(int i) {
   	 	File f;
		String subDir = "";
		String fullNameFile = "";
		int j = 0;
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       
       	// создаем папку в заданной директории: viewPass
        sql = prop.getString("sql.sel_vPass"); 
        crs.setSQL(sql);
        crs.addParameter(i);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 		if (crs.next()) {
  	 			subDir = crs.getString(1);
       	 		f = new File(adres + File.separator + subDir);
       	 		f.mkdirs();
 			}
   	 	else
       		log2.error(crs.getException());
   	 	for (int k = 6000; k <= 7000; k += 1000) {
   	 		sql = prop.getString("sql.spisokOrg"); 
   	 		crs1.setSQL(sql);
  	 		crs1.addParameter(y + "-" + mon + "-01");
   	 		crs1.addParameter(y + "-" + mon + "-31");
   	 		crs1.addParameter(k);
   	 		crs1.addParameter(i);
   	 		crs1.addParameter(dor);
       	    if (bp.executeQuery(crs1) && !crs1.isError())
       	    	if (crs1.size() > 0)
       	    		while (crs1.next()) {
           	        	j = crs1.getInteger(1);
       	        		// создадим файл в папке: codeOrg_numP.xls
       	    			sql = prop.getString("sql.sel_codOrg"); 
       	    			crs.setSQL(sql);
       	    			crs.addParameter(j);
       	        	    if (bp.executeQuery(crs) && !crs.isError())
       	        	    	if (crs.size() > 0) {
       	        	    		if (crs.next()) 
       	        	    			fullNameFile = adres + File.separator + subDir + File.separator + crs.getString(1) + "_" + k + ".xls";
       	        	    	}
       	        	    	else
       	        	    		System.out.println("Нет записей");
       	        	    else
       	        	    	System.out.println(crs.getException());
       	        	    if (k == 6000)
       	        	    	write6000(fullNameFile, i, j);
       	        	    else
       	        	    	write7000(fullNameFile, i, j);
       	    		}
       	    	else
       	    		System.out.println("Нет записей");
       	    else
       	    	log2.error(crs1.getException());
   	 	}
	}

    public void createFilePass() {
   	 	File f;
		String subDir = "";
		String fullNameFile = "";
		int kolVPass = 0;
		int j = 0;
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       

   // кол-во видов пассажиров (для перебора в цикле)
        sql = prop.getString("sql.sel_countVPass"); 
        crs.setSQL(sql);
   	 	if (bp.executeQuery(crs) && !crs.isError())
   	 		if (crs.size() > 0)
   	 			if (crs.next()) kolVPass = crs.getInteger(1);
   	 		else
   	 			System.out.println("Нет записей");
   	 	else
   	 		log2.error(crs.getException());
        
	 	for (int i = 1; i <= kolVPass; i++) {
        	// создаем папку в заданной директории: viewPass
            sql = prop.getString("sql.sel_vPass"); 
            crs.setSQL(sql);
            crs.addParameter(i);
       	 	if (bp.executeQuery(crs) && !crs.isError())
 			if (crs.next()) {
       	 		subDir = crs.getString(1);
       	 		f = new File(adres + File.separator + subDir);
       	 		f.mkdirs();
 			}
       	 	else
       	 		log2.error(crs.getException());

       	 	for (int k = 6000; k <= 7000; k += 1000) {
       	 		sql = prop.getString("sql.spisokOrg"); 
       	 		crs1.setSQL(sql);
       	 		crs1.addParameter(y + "-" + mon + "-01");
       	 		crs1.addParameter(y + "-" + mon + "-31");
       	 		crs1.addParameter(k);
       	 		crs1.addParameter(i);
       	 		crs1.addParameter(dor);
        	    if (bp.executeQuery(crs1) && !crs1.isError())
        	    	if (crs1.size() > 0)
        	    		while (crs1.next()) {
            	        	j = crs1.getInteger(1);
            	        		// создадим файл в папке: codeOrg_numP.xls
        	    			sql = prop.getString("sql.sel_codOrg"); 
        	    			crs.setSQL(sql);
        	    			crs.addParameter(j);
        	        	    if (bp.executeQuery(crs) && !crs.isError())
        	        	    	if (crs.size() > 0) {
        	        	    		if (crs.next()) 
        	        	    			fullNameFile = adres + File.separator + subDir + File.separator + crs.getString(1) + "_" + k + ".xls";
        	        	    	} else
        	        	    		System.out.println("Нет записей");
        	        	    else
        	        	    	System.out.println(crs.getException());
       	    				if (k == 6000)
       	    					write6000(fullNameFile, i, j);
       	    				else
      	    					write7000(fullNameFile, i, j);
        	    		}
        	    	else
        	    		System.out.println("Нет записей");
        	    else
        	    	log2.error(crs1.getException());
       	 	}
        }
	}

    private void write6000(String fullNameFile, int vp, int codOr) {
    	String[] title = {"Наименование перевозчика", "Форма трансп тр.", "Код организации", "Код подразделения", "Фамилия, инициалы пассажира", 
    			"Номер трансп. требования", "Дата оформления ППД", "Дата начала действия требования", "Номер оформл. бланка", "Способ оформления", 
    			"Вид документа", "Количество документов", "Станция (зона)"};
    	String[] content = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    	int q = 2;
		int nstr = 0;
        jxl.write.Label lab;
        jxl.write.Number number;
        jxl.write.Formula f;
        StringBuffer buf;
        
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       
        try {            
        	WorkbookSettings wbSettings = new WorkbookSettings();
    		wbSettings.setLocale(new Locale("en", "EN"));
    		WritableWorkbook workbookWr = Workbook.createWorkbook(new File(fullNameFile), wbSettings); 
        	WritableSheet sheetWr = workbookWr.createSheet("Лист 1", 0); 
        	for (int j = 0; j < title.length; j++) {
            	lab = new jxl.write.Label(j, 0, title[j]);
            	sheetWr.addCell(lab);
            	if (j < title.length-1)
            		sheetWr.mergeCells(j, 0, j, 1);
        	}
        	lab = new jxl.write.Label(title.length+3, 0, "Стоимость");
        	sheetWr.addCell(lab);
        	sheetWr.mergeCells(title.length+3, 0, title.length+3, 1);
        	sheetWr.mergeCells(12, 0, 15, 0);
            lab = new jxl.write.Label(12, 1, "Отправления");
            sheetWr.addCell(lab);
            lab = new jxl.write.Label(14, 1, "Назначения");
            sheetWr.addCell(lab);
        	sheetWr.mergeCells(12, 1, 13, 1);
        	sheetWr.mergeCells(14, 1, 15, 1);
        		
            sql = prop.getString("sql.sel_bundle6000"); 
            crs1.setSQL(sql);
            crs1.addParameter(y + "-" + mon + "-31");
            crs1.addParameter(y + "-" + mon + "-01");
            crs1.addParameter(6000);
            crs1.addParameter(vp);
            crs1.addParameter(codOr);
            crs1.addParameter(dor);
   	 		if (bp.executeQuery(crs1) && !crs1.isError())
   	 			if (crs1.size() > 0)
   	 				while (crs1.next()) {
   	 					for (int n = 1; n <= crs1.getColumnCount(); n++) {
 							switch (n) {
 							case 12: content[n + 1] = crs1.getString(n); break;
 							case 13: content[n + 2] = crs1.getString(n); break;
 							case 14: content[n + 2] = crs1.getString(n); break;
   	 						default: if (crs1.getString(n) == null) 
	   	 								content[n] = "";
	   	 							else
	   	 								content[n] = crs1.getString(n);
   	 								break;
   	 						}
   	 					}
						sql = prop.getString("sql.sel_vDoc"); 
 						crs.setSQL(sql);
 						crs.addParameter(crs1.getInteger(10));
 						if (bp.executeQuery(crs) && !crs.isError())
							if (crs.size() > 0) 
								while (crs.next()) content[10] = crs.getString(1);
 							else
								System.out.println("Нет записей");
 						else
  	 						log2.error(crs.getException());
 						sql = prop.getString("sql.sel_reg"); 
 						crs.setSQL(sql);
 						crs.addParameter(crs1.getInteger(9));
 						if (bp.executeQuery(crs) && !crs.isError())
							if (crs.size() > 0)
								while (crs.next()) content[9] = crs.getString(1);
 							else
								System.out.println("Нет записей");
 						else
 							log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_codOrg"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(codOr);
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[2] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_codPodr"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(crs1.getInteger(3));
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[3] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_namTransp"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(crs1.getInteger(1));
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[0] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_namFormTr"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(crs1.getInteger(2));
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[1] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(crs1.getString(12));
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[12] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(crs1.getString(13));
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[14] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	   	 				for (int i = 0; i < content.length; i++) {
   	   	 					if (i == content.length - 1) {
   	   	 						number = new jxl.write.Number(i, q, Double.parseDouble(content[i]));
   	   	 						sheetWr.addCell(number);
   	   	 					} else {
   	   	 						lab = new jxl.write.Label(i, q, content[i]);
   	   	 						sheetWr.addCell(lab);
   	   	 					}
   	   	 				}
   	   	 				if (content[4].indexOf("<br>") < 0) {
   	   	 					q++;
   	   	 				} else {
   	   	 					for (int i = 0; i < content.length; i++) {
   	   	 						if (i == 4) {
   	   	 							lab = new jxl.write.Label(i, q, content[i].substring(0, content[4].indexOf("<br>")));
   	   	   	 						sheetWr.addCell(lab);
   	   	 							lab = new jxl.write.Label(i, q+1, content[i].substring(content[4].indexOf("<br>") + 4));
   	   	   	 						sheetWr.addCell(lab);
   	   	 						} else
   	   	 							sheetWr.mergeCells(i, q, i, q+1);
   	   	 					}
   	   	 					q += 2;
   	   	 				}
   	 				}
   	 			else
   	 				System.out.println("Нет записей");
   	 		else
   	 			log2.error(crs1.getException());
 			if (content[4].indexOf("<br>") < 0)
	   	 		nstr = crs1.size() + 2;
 			else
	   	 		nstr = crs1.size()*2 + 2;
 			lab = new jxl.write.Label(0, nstr, "Итого");
 			sheetWr.addCell(lab);
 			sheetWr.mergeCells(0, nstr, 15, nstr);
			buf = new StringBuffer();
			buf.append("SUM(Q3:Q" + nstr + ")");
			f = new jxl.write.Formula(16, nstr, buf.toString());
			sheetWr.addCell(f);
			workbookWr.write();
			workbookWr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void write7000(String fullNameFile, int vp, int codOr) {
    	String[] title = {"Наименование перевозчика", "Форма трансп тр.", "Код организации", "Код подразделения", 
    			"Фамилия, инициалы пассажира", "Номер трансп. требования", "Дата оформления ППД", 
    			"Номер оформл. бланка", "Количество билетов АСУ ЭКСПРЕСС", "Станция (зона)"};
    	String[] content = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    	int q = 2;
		int nstr = 0;
        jxl.write.Label lab;
        jxl.write.Number number;
        jxl.write.Formula f;
        StringBuffer buf;
        
        CachedRowSet crs1 = new CachedRowSet(log2);
        String sql;       
        try {
        	WorkbookSettings wbSettings = new WorkbookSettings();
    		wbSettings.setLocale(new Locale("en", "EN"));
    		WritableWorkbook workbookWr = Workbook.createWorkbook(new File(fullNameFile), wbSettings); 
        	WritableSheet sheetWr = workbookWr.createSheet("Лист 1", 0); 
        	for (int j = 0; j < title.length; j++) {
            	lab = new jxl.write.Label(j, 0, title[j]);
            	sheetWr.addCell(lab);
            	if (j < title.length-1)
            		sheetWr.mergeCells(j, 0, j, 1);
        	}
        	lab = new jxl.write.Label(title.length+3, 0, "Стоимость");
        	sheetWr.addCell(lab);
        	sheetWr.mergeCells(title.length+3, 0, title.length+3, 1);
    		sheetWr.mergeCells(9, 0, 12, 0);
        	lab = new jxl.write.Label(9, 1, "Отправления");
        	sheetWr.addCell(lab);
        	lab = new jxl.write.Label(11, 1, "Назначения");
        	sheetWr.addCell(lab);
    		sheetWr.mergeCells(9, 1, 10, 1);
    		sheetWr.mergeCells(11, 1, 12, 1);
        	lab = new jxl.write.Label(title.length+4, 0, "Доплата (сумма доплачивается пассажиром)");
        	sheetWr.addCell(lab);
        	sheetWr.mergeCells(title.length+4, 0, title.length+4, 1);
        		
            sql = prop.getString("sql.sel_bundle7000"); 
            crs1.setSQL(sql);
            crs1.addParameter(y + "-" + mon + "-31");
            crs1.addParameter(y + "-" + mon + "-01");
            crs1.addParameter(7000);
            crs1.addParameter(vp);
            crs1.addParameter(codOr);
            crs1.addParameter(dor);
   	 		if (bp.executeQuery(crs1) && !crs1.isError())
   	 			if (crs1.size() > 0)
   	 				while (crs1.next()) {
   	 					for (int n = 1; n <= crs1.getColumnCount(); n++) {
 							switch (n) {
 							case 9: content[n + 1] = crs1.getString(n); break;
 							case 10: content[n + 2] = crs1.getString(n); break;
 							case 11: content[n + 2] = crs1.getString(n); break;
 							case 12: content[n + 2] = crs1.getString(n); break;
   	 						default: if (crs1.getString(n) == null) 
	   	 								content[n] = "";
	   	 							else
	   	 								content[n] = crs1.getString(n);
   	 								break;
   	 						}
   	 					}
   	 					sql = prop.getString("sql.sel_codOrg"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(codOr);
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[2] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_codPodr"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(crs1.getInteger(3));
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[3] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_namTransp"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(crs1.getInteger(1));
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[0] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_namFormTr"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(crs1.getInteger(2));
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[1] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(crs1.getString(9));
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[9] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	 					sql = prop.getString("sql.sel_namStation"); 
   	 					crs.setSQL(sql);
   	 					crs.addParameter(crs1.getString(10));
   	 					if (bp.executeQuery(crs) && !crs.isError())
   	 						if (crs.size() > 0)
   	 							while (crs.next()) content[11] = crs.getString(1);
   	 						else
   	 							System.out.println("Нет записей");
   	 					else
   	 						log2.error(crs.getException());
   	   	 				for (int i = 0; i < content.length; i++) {
   	   	 					if (i == content.length - 1 || i == content.length - 2) {
   	   	 						number = new jxl.write.Number(i, q, Double.parseDouble(content[i]));
   	   	 						sheetWr.addCell(number);
   	   	 					} else {
   	   	 						lab = new jxl.write.Label(i, q, content[i]);
   	   	 						sheetWr.addCell(lab);
   	   	 					}
   	   	 				}
   	   	 				if (content[4].indexOf("<br>") < 0) {
   	   	 					q++;
   	   	 				} else {
   	   	 					for (int i = 0; i < content.length; i++) {
   	   	 						if (i == 4) {
   	   	 							lab = new jxl.write.Label(i, q, content[i].substring(0, content[4].indexOf("<br>")));
   	   	   	 						sheetWr.addCell(lab);
   	   	 							lab = new jxl.write.Label(i, q+1, content[i].substring(content[4].indexOf("<br>") + 4));
   	   	   	 						sheetWr.addCell(lab);
   	   	 						} else
   	   	 							sheetWr.mergeCells(i, q, i, q+1);
   	   	 					}
   	   	 					q += 2;
   	   	 				}
   	 				}
   	 			else
   	 				System.out.println("Нет записей");
   	 		else
   	 			log2.error(crs1.getException());
 			if (content[4].indexOf("<br>") < 0)
	   	 		nstr = crs1.size() + 2;
 			else
	   	 		nstr = crs1.size()*2 + 2;
 			lab = new jxl.write.Label(0, nstr, "Итого");
 			sheetWr.addCell(lab);
 			sheetWr.mergeCells(0, nstr, 12, nstr);
			buf = new StringBuffer();
			buf.append("SUM(N3:O" + nstr + ")");
			f = new jxl.write.Formula(13, nstr, buf.toString());
			sheetWr.addCell(f);
 			sheetWr.mergeCells(13, nstr, 14, nstr);
			workbookWr.write();
			workbookWr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void itogo() {
    	String fullNameFile = adres + File.separator + "Itogo.xls";
    	int nstr = 1;
    	int i = 0;
		int kolOrg = 0;
		int kolVPass = 0;
    	String[] title = {"Код организации", 
    			"работники организаций, финансируемых ОАО РЖД", 
    			"дети, находящиеся на иждевении работников и пенсионеров ОАО РЖД", 
    			"пенсионеры ОАО РЖД", 
    			"работники ОАО РЖД", 
    			"иные лица", 
    			"дети, родители которых погибли в результате ж.д. аварий", 
    			"дети, находящиеся на иждевении у неработающих пенсионеров ОАО РЖД", 
    			"пенсионеры ОАО ФПК", 
    			"работники ОАО ФПК", 
    			"дети, находящиеся на иждевении работников и пенсионеров ОАО ФПК", 
    			"дети, находящиеся на иждевении у неработающих пенсионеров ОАО ФПК", 
//    			"работники организаций, финансируемых ОАО ФПК", 
//       			"дети, родители которых являлись сотрудниками ФПК и погибли в результате ж.д. аварий", 
    			"ИТОГО"};
    	String[] form = {"", "B2:B", "C2:C", "D2:D", "E2:E", "F2:F", "G2:G", "H2:H", "I2:I", "J2:J", "K2:K", "L2:L", "M2:M", "N2:N"};
        jxl.write.Label lab;
        jxl.write.Number number;
        jxl.write.Formula f;
        StringBuffer buf;
		
        CachedRowSet crs1 = new CachedRowSet(log2);
        CachedRowSet crs3 = new CachedRowSet(log2);
        String sql;
        try {            
            sql = prop.getString("sql.sel_countVPass"); 
            crs.setSQL(sql);
       	 	if (bp.executeQuery(crs) && !crs.isError())
       	 		if (crs.size() > 0)
       	 			if (crs.next()) kolVPass = crs.getInteger(1);
       	 		else
       	 			System.out.println("Нет записей");
       	 	else
       	 		log2.error(crs.getException());
           	WorkbookSettings wbSettings = new WorkbookSettings();
       		wbSettings.setLocale(new Locale("en", "EN"));
       		WritableWorkbook workbookWr = Workbook.createWorkbook(new File(fullNameFile), wbSettings); 
           	WritableSheet sheetWr = workbookWr.createSheet("Лист 1", 0); 
           	for (int j = 0; j < title.length; j++) {
               	lab = new jxl.write.Label(j, 0, title[j]);
               	sheetWr.addCell(lab);
           	}
   	 		sql = prop.getString("sql.orgMonth"); 
   	 		crs3.setSQL(sql);
   	 		crs3.addParameter(y + "-" + mon + "-01");
   	 		crs3.addParameter(dor);
   	 		crs3.addParameter(y + "-" + mon + "-31");
    	    if (bp.executeQuery(crs3) && !crs3.isError())
   	    		while (crs3.next()) {
   	    			kolOrg = crs3.size();
   	    			i = crs3.getInteger(1);
   	    			sql = prop.getString("sql.sel_codOrg"); 
   	    			crs.setSQL(sql);
   	    			crs.addParameter(i);
   	    			if (bp.executeQuery(crs) && !crs.isError())
   	    				if (crs.next()) {
   	    					lab = new jxl.write.Label(0, nstr, crs.getString(1));
   	    					sheetWr.addCell(lab);
   	    					for (int j = 1; j <= kolVPass; j++) {
   	    						sql = prop.getString("sql.sel_sumVPass"); 
   	    						crs1.setSQL(sql);
   	    						crs1.addParameter(y + "-" + mon + "-01");
   	    						crs1.addParameter(j);
   	    						crs1.addParameter(i);
   	    						crs1.addParameter(dor);
   	    						crs1.addParameter(y + "-" + mon + "-31");
   	    						if (bp.executeQuery(crs1) && !crs1.isError())
   	    							if (crs1.size() > 0) {
   	    								if (crs1.next()) {
   	    									number = new jxl.write.Number(j, nstr, crs1.getDouble(1,0));
   	    									sheetWr.addCell(number);
   	    								}
   	    							} else
   	    								System.out.println("Нет суммы для указанного вида пассажира");
   	    						else
   	    							log2.error(crs1.getException());
   	    					}
   	    					buf = new StringBuffer();
   	    					buf.append("SUM(B" + (nstr+1) + ":L" + (nstr+1) + ")");
   	    					f = new jxl.write.Formula(kolVPass+1, nstr, buf.toString());
   	    					sheetWr.addCell(f);
   	    					nstr++;
   	    				} else
   	    					System.out.println("Нет организации");
   	    			else
   	    				log2.error(crs.getException());
   	    		}
			lab = new jxl.write.Label(0, kolOrg + 1, "Итого");
			sheetWr.addCell(lab);
			for (int j = 1; j <= kolVPass+1; j++) {
				buf = new StringBuffer();
				buf.append("SUM(" + form[j] + (kolOrg + 1) + ")");
				f = new jxl.write.Formula(j, kolOrg + 1, buf.toString());
				sheetWr.addCell(f);
			}
			workbookWr.write();
			workbookWr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
