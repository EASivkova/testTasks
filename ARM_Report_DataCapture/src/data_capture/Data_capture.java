package data_capture;

import java.io.File;
import java.sql.*;

import ru.svrw.eivc.date.*;
import ru.svrw.eivc.util.Properties;
import ru.svrw.eivc.util.StringUtils;

public class Data_capture {
	
	private static Log log = new Log();
	
	public static void main(String[] args) {
		
		log.setConsole("Data_capture.main", "Начало работы программы.");

		Calendar today = new Calendar();
		
		String dayEnd = today.getYear() + "-";
		if (today.getMonth() < 10)
			dayEnd += "0";
		dayEnd += today.getMonth() + "-";
		if (today.getDay() < 10)
			dayEnd += "0";
		dayEnd += today.getDay() + " 00:00:00.000000000";
		Timestamp end = Timestamp.valueOf(dayEnd);
    	today.addDay(-1);
    	
		String dayBegin = today.getYear() + "-";
		if (today.getMonth() < 10)
			dayBegin += "0";
		dayBegin += today.getMonth() + "-";
		if (today.getDay() < 10)
			dayBegin += "0";
		dayBegin += today.getDay() + " 00:00:00.000000000";
    	Timestamp begin = Timestamp.valueOf(dayBegin);

    	WorkReport bdReport = new WorkReport();
		
		WorkESPP bdESPP = new WorkESPP();
		if (bdESPP.testingCon() && bdReport.testingCon()) {
			log.setConsole("Data_capture.main", "БД ЕСПП и БД Report работает");
			bdESPP.readDataFromTableIncidets(begin, end);
		} else
			log.setError("Data_capture.main", "БД ЕСПП или БД Report не работает. Данные из БД ЕСПП не были загружены в БД Report");
		
		String yyyyMMdd = today.getYear() + "-";
		if (today.getMonth() < 10)
			yyyyMMdd += "0";
		yyyyMMdd += today.getMonth() + "-";
		if (today.getDay() < 10)
			yyyyMMdd += "0";
		yyyyMMdd += today.getDay() + " 00:00:00.000000000";
    	Timestamp yyyy_mm_dd = Timestamp.valueOf(yyyyMMdd);

    	WorkKASANT bdKASANT = new WorkKASANT();
		if (bdKASANT.testingCon() && bdReport.testingCon()) {
			log.setConsole("Data_capture.main", "БД КАСАНТ и БД Report работает");
			int countKASANT = bdKASANT.countVIOL(begin, end);
			if (countKASANT > 0)
				bdReport.setEVENT(yyyy_mm_dd, countKASANT, "КАСАНТ");
			int countKASAT = bdKASANT.countTechBreak(begin, end);
			if (countKASAT > 0)
				bdReport.setEVENT(yyyy_mm_dd, countKASAT, "КАСАТ");
		} else
			log.setError("Data_capture.main", "БД КАСАНТ или БД Report не работает. Данные из БД КАСАНТ не были загружены в БД Report");
		
    	String ddmmyyyy = "";
    	if (today.getDay() < 10)
    		ddmmyyyy += "0";
    	ddmmyyyy += today.getDay();
    	if (today.getMonth() < 10)
    		ddmmyyyy += "0";
    	ddmmyyyy += today.getMonth() + "" + today.getYear();

    	WorkXLS xls = new WorkXLS();
		int countDO13 = xls.setCountDO13(ddmmyyyy);
		if (countDO13 > 0)
			bdReport.setEVENT(yyyy_mm_dd, countDO13, "ДО-13");
		int countDO14 = xls.setCountDO14(ddmmyyyy);
		if (countDO14 > 0)
			bdReport.setEVENT(yyyy_mm_dd, countDO14, "ДО-14");
		int countDO24 = xls.setCountDO24(ddmmyyyy);
		if (countDO24 > 0)
			bdReport.setEVENT(yyyy_mm_dd, countDO24, "ДО-24ВЦ"); 
		
		revize();
		
		today = new Calendar();
		if (today.getDay() == 1) { // Первого числа каждого месяца очищаем папки XLS, log
			StringUtils su = new StringUtils(); 
			Properties propSettings = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));
			deleteFiles(propSettings.getString("folder.xls"));
			deleteFiles(propSettings.getString("log.adres"));
		}
		
		log.setConsole("Data_capture.main", "Программа завершила работу.");
	}

	// сверка данных за месяц между системами
	private static void revize() {
		Calendar today = new Calendar();
    	
		Timestamp begin = Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-01 00:00:00.000000000");
		today.addMonth(1);
    	Timestamp end = Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-01 00:00:00.000000000");
    	today = new Calendar();

    	WorkReport bdReport = new WorkReport();
		
		WorkESPP bdESPP = new WorkESPP();
		if (bdESPP.testingCon() && bdReport.testingCon()) {
			log.setConsole("Data_capture.revize", "БД ЕСПП и БД Report работает");
	    	System.out.println("ESPP " + bdESPP.readDataFromTableIncidetsMonth(begin, end) + " =? " + bdReport.countIncidents(begin, end));
			if (bdESPP.readDataFromTableIncidetsMonth(begin, end) != bdReport.countIncidents(begin, end)) {
				bdReport.setTableSynchr(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 1, "ESPP");
			}
		} else
			log.setError("Data_capture.revize", "БД ЕСПП или БД Report не работает. Сверка данных между БД ЕСПП и БД Report не была выполнена");
		
    	WorkKASANT bdKASANT = new WorkKASANT();
    	WorkXLS xls = new WorkXLS();
    	end = Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000");
    	int countDay = bdReport.countDay(begin, end);
		String[] ddmmyyyy = new String[countDay];
		Timestamp[] yyyy_mm_dd = new Timestamp[countDay];
		Calendar temp = new Calendar();
		temp.setDate(today.getYear(), today.getMonth(), 1);
		temp.setTime(0, 0, 0);
		int summDO13 = 0;
		int summDO14 = 0;
		int summDO24 = 0;
		int summKASANT = 0;
		int summKASAT = 0;
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
			int countDO13 = xls.setCountDO13(ddmmyyyy[i]);
			if (countDO13 > 0)
				summDO13 += countDO13;
			int countDO14 = xls.setCountDO14(ddmmyyyy[i]);
			if (countDO14 > 0)
				summDO14 += countDO14;
			int countDO24 = xls.setCountDO24(ddmmyyyy[i]);
			if (countDO24 > 0)
				summDO24 += countDO24;
			summKASANT += bdKASANT.countVIOL(yyyy_mm_dd[i], Timestamp.valueOf(temp.getDBDate() + " 00:00:00.000000000"));
			summKASAT += bdKASANT.countTechBreak(yyyy_mm_dd[i], Timestamp.valueOf(temp.getDBDate() + " 00:00:00.000000000"));
    	}
    	if (summDO13 != bdReport.summEvent(begin, end, "ДО-13"))
			bdReport.setTableSynchr(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 1, "DO13");
    	if (summDO14 != bdReport.summEvent(begin, end, "ДО-14"))
			bdReport.setTableSynchr(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 1, "DO14");
    	if (summDO24 != bdReport.summEvent(begin, end, "ДО-24ВЦ"))
			bdReport.setTableSynchr(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 1, "DO24");
		log.setConsole("Data_capture.revize", "KASANT " + summKASANT + " =? " + bdReport.summEvent(begin, end, "КАСАНТ"));
		if (summKASANT != bdReport.summEvent(begin, end, "КАСАНТ"))
			bdReport.setTableSynchr(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 1, "KASANT");
		if (summKASAT != bdReport.summEvent(begin, end, "КАСАТ"))
			bdReport.setTableSynchr(Timestamp.valueOf(today.getYear() + "-" + ((today.getMonth() < 10)?"0":"") + today.getMonth() + "-" + ((today.getDay() < 10)?"0":"") + today.getDay() + " 00:00:00.000000000"), 1, "KASAT");
	}
	
	private static void deleteFiles(String adrFolder) {
       	File folder = new File(adrFolder);
       	File f;
       	String[] nameFile = folder.list();
       	for (int j = 0; j < nameFile.length; j++) {
       		f = new File(adrFolder + nameFile[j]);
       		if (!f.delete())
       			log.setError("Data_capture.daleteFiles", nameFile[j] + " удалить не удалось");
       	}
	}
}
