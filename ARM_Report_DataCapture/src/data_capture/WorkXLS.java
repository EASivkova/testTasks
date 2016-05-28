package data_capture;

import java.io.*;

import ru.svrw.eivc.util.Properties;
import ru.svrw.eivc.util.StringUtils;
import jxl.*;

public class WorkXLS {

	private StringUtils su = new StringUtils(); 
	private Properties propSettings = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));
	private Log log = new Log();

	public int setCountDO13(String ddmmyyyy) {
		String xlsDO13 = propSettings.getString("xls.do13");
        try {
           	Workbook workbook = Workbook.getWorkbook(new File(xlsDO13 + ddmmyyyy + ".xls")); 
           	Sheet sheet = workbook.getSheet("сводная");
           	int sum = 0;
           	if (!sheet.getCell("T7").getContents().equals(""))
           		sum += Integer.parseInt(sheet.getCell("T7").getContents());
           	if (!sheet.getCell("T12").getContents().equals(""))
           		sum += Integer.parseInt(sheet.getCell("T12").getContents());
           	if (!sheet.getCell("T17").getContents().equals(""))
           		sum += Integer.parseInt(sheet.getCell("T17").getContents());
          	workbook.close();
          	return sum;
        } catch (Exception e) {
			log.setError("WorkXLS.setCountDO13(" + ddmmyyyy + ")", e.toString());
            return -1;
        }
	}

	public int setCountDO14(String ddmmyyyy) {
		String xlsDO14a = propSettings.getString("xls.do14a");
		String xlsDO14b = propSettings.getString("xls.do14b");
        try {
           	Workbook workbook = Workbook.getWorkbook(new File(xlsDO14a + ddmmyyyy + ".xls")); 
           	Sheet sheet = workbook.getSheet("ДО-14ВЦ-1");
           	int sum = 0;
           	if (!sheet.getCell("S16").getContents().equals(""))
           		sum += Integer.parseInt(sheet.getCell("S16").getContents());
          	workbook.close();
           	workbook = Workbook.getWorkbook(new File(xlsDO14b + ddmmyyyy + ".xls")); 
           	sheet = workbook.getSheet("ДО-14ВЦ-1");
           	if (!sheet.getCell("S16").getContents().equals(""))
           		sum += Integer.parseInt(sheet.getCell("S16").getContents());
          	workbook.close();
          	return sum;
        } catch (Exception e) {
			log.setError("WorkXLS.setCountDO14(" + ddmmyyyy + ")", e.toString());
            return -1;
        }
	}

	public int setCountDO24(String ddmmyyyy) {
		String xlsDO24 = propSettings.getString("xls.do24");
        try {
           	Workbook workbook = Workbook.getWorkbook(new File(xlsDO24 + ddmmyyyy + ".xls")); 
           	Sheet sheet = workbook.getSheet("Раздел 2 Стр.5 (продолжение)");
           	int sum = 0;
           	if (!sheet.getCell("AC34").getContents().equals(""))
           		sum += Integer.parseInt(sheet.getCell("AC34").getContents());
          	workbook.close();
          	return sum;
        } catch (Exception e) {
			log.setError("WorkXLS.setCountDO24(" + ddmmyyyy + ")", e.toString());
            return -1;
        }
	}

}
