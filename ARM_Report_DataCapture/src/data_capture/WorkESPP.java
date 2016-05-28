package data_capture;

import java.sql.*;

import ru.svrw.eivc.util.Properties;
import ru.svrw.eivc.util.StringUtils;

public class WorkESPP {

	private StringUtils su = new StringUtils(); 
	private Properties propSettings = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));
	private static Connection con;
	private static ResultSet rs;
	private static PreparedStatement st;
	private Log log = new Log();
	
	/* загрузка драйвера для соединения с базой данных ПП */
	private static void loadDriver() throws ClassNotFoundException {
		String driver = "net.sourceforge.jtds.jdbc.Driver";
		Class.forName(driver);
	}
	
	/* усановление соединения с базой данных ЕСПП*/
	private void connect() {
		try {
			loadDriver();
		} catch (ClassNotFoundException cnfe) {
			log.setError("WorkESPP.loadDriver", cnfe + "\nProgram exits");
			log.setConsole("WorkESPP.loadDriver", cnfe + "\nProgram exits");
			System.exit(0);
		}
		try {
			String username = propSettings.getString("bdESPP.username");
			String password = propSettings.getString("bdESPP.password");
			String protocol = propSettings.getString("bdESPP.protocol");
			String host = propSettings.getString("bdESPP.server");
			String file = propSettings.getString("bdESPP.bd");
			String databaseUrl = protocol + host + file;
			con = DriverManager.getConnection(databaseUrl, username, password);
			con.setAutoCommit(true);
		} catch(SQLException sqle) { 
			log.setError("WorkESPP.connect", sqle.toString()); 
		}
	}
	
	// тест возможности соединения с базой
	public boolean testingCon() {
		boolean test = false;
		connect();
		if (con != null) {
			test = true;
		} else {
    		test = false;
		}
        close();
        return test;
	}
	
	// загрузка содержимого таблицы ИНЦИДЕНТЫ
	public void readDataFromTableIncidets(Timestamp begin, Timestamp end) {
		connect();
		if (con != null) {
			try {
				String sql = "SELECT инциденты.ВРЕМЯ_ВОЗНИКНОВЕНИЯ, инциденты.ВРЕМЯ_УСТРАНЕНИЯ, инциденты.ДЛИТЕЛЬНОСТЬ_ВОССТАНОВЛЕНИЯ, инциденты.ИСПОЛНИТЕЛЬ, инциденты.КОД_ЗАКРЫТИЯ, инциденты.НОМЕР, инциденты.ООБЪЕКТ_СОПРОВОЖДЕНИЯ, инциденты.ОТЧЕТНЫЙ, инциденты.ПОДРАЗДЕЛЕНИЕ_ИНИЦИАТОРА, инциденты.ПОДРОБНОЕ_ОПИСАНИЕ, инциденты.ПОСЛЕДСТВИЯ_КОММЕНТАРИИ, инциденты.ПРИЧИНА_ВОЗНИКНОВЕНИЯ, инциденты.РАБОЧАЯ_ГРУППА, инциденты.РЕШЕНИЕ, инциденты.СТАТУС, инциденты.ЭК, инциденты.SYSMODTIME FROM инциденты WHERE инциденты.ЗОНА_ОТВЕТСТВЕННОСТИ=? AND инциденты.SYSMODTIME>=? AND инциденты.SYSMODTIME<?";
				st = con.prepareStatement(sql);
				st.setString(1, "76-СВРД");
				st.setTimestamp(2, begin);
				st.setTimestamp(3, end);
				rs = st.executeQuery();
				if (rs == null) {
					System.out.println("rs = null");
				} else {
					while (rs.next()) {
						Timestamp DOWNTIME_START = rs.getTimestamp(1);
						Timestamp DOWNTIME_END = rs.getTimestamp(2);
						Time DOWNTIME = rs.getTime(3);
						String HPC_ASSIGNEE	= rs.getString(4);
						String RESOLUTION_CODE = rs.getString(5);
						String NUMBER = rs.getString(6);
						String HPC_SUPPORT_OBJECT = rs.getString(7);
						String HPC_REPORT = rs.getString(8);
						String DEPT = rs.getString(9);
						Clob ACTION = rs.getClob(10);
						Clob EXPLANATION = rs.getClob(11);
						String CAUSE_CODE = rs.getString(12);
						String HPC_ASSIGNMENT = rs.getString(13);
						Clob RESOLUTION = rs.getClob(14);
						String HPC_STATUS = rs.getString(15);
						String LOGICAL_NAME = rs.getString(16);
						Timestamp sysmodtime = rs.getTimestamp(17);
						
						WorkReport bdReport = new WorkReport();
						if (bdReport.updateIncident(DOWNTIME_START, DOWNTIME_END, DOWNTIME, HPC_ASSIGNEE, RESOLUTION_CODE, NUMBER, HPC_SUPPORT_OBJECT, HPC_REPORT, DEPT, ACTION, EXPLANATION, CAUSE_CODE, HPC_ASSIGNMENT, RESOLUTION, HPC_STATUS, LOGICAL_NAME, sysmodtime))
							log.setConsole("WorkESPP.readDataFromTableIncidets", NUMBER + " - измеения данных из ЕСПП в Report записаны");
						else {
							if (bdReport.setIncidents(DOWNTIME_START, DOWNTIME_END, DOWNTIME, HPC_ASSIGNEE, RESOLUTION_CODE, NUMBER, HPC_SUPPORT_OBJECT, HPC_REPORT, DEPT, ACTION, EXPLANATION, CAUSE_CODE, HPC_ASSIGNMENT, RESOLUTION, HPC_STATUS, LOGICAL_NAME, sysmodtime))
								log.setConsole("WorkESPP.readDataFromTableIncidets", NUMBER + " - данные из ЕСПП в Report записаны");
						}
					}
				}
			} catch(SQLException sqle) { log.setError("WorkESPP.readDataFromTableIncidets", sqle.toString()); }
		} else {
			log.setConsole("WorkESPP.readDataFromTableIncidets", "Нет соединения с БД ЕСПП.");
		}
        close();
	}
	
	// кол-во записей в таблице ИНЦИДЕНТЫ за тек.месяц
	public int readDataFromTableIncidetsMonth(Timestamp begin, Timestamp end) {
		int count = 0;
		connect();
		if (con != null) {
			try {
				String sql = "SELECT count(*) FROM инциденты WHERE инциденты.ЗОНА_ОТВЕТСТВЕННОСТИ=? AND инциденты.SYSMODTIME>=? AND инциденты.SYSMODTIME<?";
				st = con.prepareStatement(sql);
				st.setString(1, "76-СВРД");
				st.setTimestamp(2, begin);
				st.setTimestamp(3, end);
				rs = st.executeQuery();
				if (rs == null) {
					System.out.println("rs = null");
				} else {
					if (rs.next()) {
						count = rs.getInt(1);
					}
				}
			} catch(SQLException sqle) { log.setError("WorkESPP.readDataFromTableIncidetsMonth", sqle.toString()); }
		} else {
			log.setConsole("WorkESPP.readDataFromTableIncidetsMonth", "Нет соединения с БД ЕСПП.");
		}
        close();
        return count;
	}
	
	// закрытие соединения
	private void close() {
		try {
			if (con != null)
				con.close();
		} catch(SQLException sqle) {
			log.setError("WorkESPP.closeESPP", sqle.toString() + "\nProgram exits");
			log.setConsole("WorkESPP.closeESPP", sqle.toString() + "\nProgram exits");
			System.exit(0); 
		}
	}
	
}
