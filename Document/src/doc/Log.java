package doc;

import java.io.*;

import ru.svrw.eivc.date.Calendar;

public class Log {
	
	private Calendar today = new Calendar();
	
	/* создание лог-файла при возникновении ошибок в работе программы */
	public void setFile(String workCatalog, String user, String sError) {
		today = new Calendar();
		try {
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(workCatalog + "Log" + File.separator + user + today.getDBDate() + "_" + today.getDBTime() + ".txt",true));
			String[] str = sError.split("\n");
			for (int i = 0; i < str.length; i++) {
				bw.write(str[i]);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) { System.out.println(e); }
	}
}
