package data_capture;

import java.io.*;

import ru.svrw.eivc.date.Calendar;
import ru.svrw.eivc.util.Properties;
import ru.svrw.eivc.util.StringUtils;

public class Log {
	
	private Calendar today = new Calendar();
	private StringUtils su = new StringUtils(); 
	private Properties propSettings = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));
	private String sAdres = propSettings.getString("log.adres");
	
	public void setError(String place, String text) {
		today = new Calendar();
		String clas = place.substring(0, place.indexOf("."));
		String function = place.substring(place.indexOf("."));
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(sAdres + "Error_" + clas + today.getDBDate() + "_" + today.getHour() + ".log", true));
			bw.newLine();
			bw.write(function);
			bw.newLine();
			String[] str = text.split("\n");
			for (int i = 0; i < str.length; i++) {
				bw.write(str[i]);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) { System.out.println(e); }
	}
	
	public void setConsole(String place, String text) {
		today = new Calendar();
		String clas = place.substring(0, place.indexOf("."));
		String function = place.substring(place.indexOf("."));
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(sAdres + "Console_" + clas + today.getDBDate() + "_" + today.getHour() + ".log",true));
			bw.newLine();
			bw.write(function);
			bw.newLine();
			String[] str = text.split("\n");
			for (int i = 0; i < str.length; i++) {
				bw.write(str[i]);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) { System.out.println(e); }
	}
}
