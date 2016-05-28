package data_capture;

import java.sql.*;

import ru.svrw.eivc.util.*;

public class WorkReport {

	private StringUtils su = new StringUtils(); 
	private Properties propSQL = new Properties(su.replaceSystemParameter("${pathProject}/conf/query.properties"));
	private Properties propSettings = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));
	private static Connection con;
	private static ResultSet rs;
	private static PreparedStatement st;
	private Log log = new Log();
	
	/* загрузка драйвера для соединения с базой данных Report */
	private static void loadDriver() throws ClassNotFoundException {
		String driver = "com.ibm.db2.jcc.DB2Driver";
		Class.forName(driver);
	}
	
	/* усановление соединения с базой данных Report*/
	private void connect() {
		try {
			loadDriver();
		} catch (ClassNotFoundException cnfe) {
			log.setError("WorkReport.loadDriver", cnfe + "\nProgram exits");
			log.setConsole("WorkReport.loadDriver", cnfe + "\nProgram exits");
			System.exit(0);
		}
		try {
			String username = propSettings.getString("bdReport.username");
			String password = propSettings.getString("bdReport.password");
			String protocol = propSettings.getString("bdReport.protocol");
			String host = propSettings.getString("bdReport.server");
			String file = propSettings.getString("bdReport.bd");
			String databaseUrl = protocol + host + file;
			con = DriverManager.getConnection(databaseUrl, username, password);
			con.setAutoCommit(true);
		} catch(SQLException sqle) { 
			log.setError("WorkReport.connect", sqle.toString());
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
	
	public boolean setIncidents(Timestamp DOWNTIME_START, Timestamp DOWNTIME_END, Time DOWNTIME, String HPC_ASSIGNEE, String RESOLUTION_CODE, String NUMBER, String HPC_SUPPORT_OBJECT, String HPC_REPORT, String DEPT, Clob ACTION, Clob EXPLANATION, String CAUSE_CODE, String HPC_ASSIGNMENT, Clob RESOLUTION, String HPC_STATUS, String LOGICAL_NAME, Timestamp sysmodtime) {
		boolean compare = false;
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.Report_insertIncidents");
				st = con.prepareStatement(sql);
				st.setTimestamp(1, DOWNTIME_START);
				st.setTimestamp(2, DOWNTIME_END);
				st.setTime(3, DOWNTIME); 
				st.setString(4, HPC_ASSIGNEE);
				st.setString(5, RESOLUTION_CODE);
				st.setString(6, NUMBER);
				st.setString(7, HPC_SUPPORT_OBJECT);
				st.setString(8, HPC_REPORT);
				st.setString(9, DEPT);
				st.setClob(10, ACTION);
				st.setClob(11, EXPLANATION);
				st.setString(12, CAUSE_CODE);
				st.setString(13, HPC_ASSIGNMENT);
				st.setClob(14, RESOLUTION);
				st.setString(15, HPC_STATUS);
				st.setString(16, LOGICAL_NAME);
				st.setTimestamp(17, sysmodtime);
				st.executeUpdate();
				compare = true;
			} catch(SQLException sqle) {
				compare = false;
				log.setError("WorkReport.setIncidents(" + NUMBER + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.setIncidents(" + NUMBER + ")", "Нет соединения с БД REPORT.");
		}
		close();
		return compare;
	}
	
	private void setDay(Timestamp day) {
		try {
			String sql = propSQL.getString("sql.Report_ifISday");
			st = con.prepareStatement(sql);
			st.setTimestamp(1, day);
			rs = st.executeQuery();
			if (rs.next())
				if (rs.getInt(1) == 0) {
					sql = propSQL.getString("sql.Report_insertDay");
					st = con.prepareStatement(sql);
					st.setTimestamp(1, day);
					st.executeUpdate();
				}
		} catch(SQLException sqle) {
			log.setError("WorkReport.setDay(" + day + ")", sqle.toString());
		}
	}
	
	public boolean setEVENT(Timestamp day, int count, String system) {
		boolean compare = false;
		connect();
		if (con != null) {
			int id_system = getIDsystem(system);
			setDay(day);
			try {
				String sql = propSQL.getString("sql.Report_insertEVENT");
				st = con.prepareStatement(sql);
				st.setInt(1, count);
				st.setInt(2, id_system);
				st.setTimestamp(3, day);
				st.executeUpdate();
				compare = true;
			} catch(SQLException sqle) {
				compare = false;
				log.setError("WorkReport.setEVENT(" + day + ", " + system + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.setEVENT(" + day + ", " + system + ")", "Нет соединения с БД REPORT.");
		}
		close();
		return compare;
	}
	
	public boolean updEVENT(Timestamp day, int count, String system) {
		boolean compare = false;
		connect();
		if (con != null) {
			int id_system = getIDsystem(system);
			setDay(day);
			try {
				String sql = propSQL.getString("sql.Report_ifISevent");
				st = con.prepareStatement(sql);
				st.setInt(1, id_system);
				st.setTimestamp(2, day);
				rs = st.executeQuery();
				if (rs.next())
					if (rs.getInt(1) == 0) {
						sql = propSQL.getString("sql.Report_insertEVENT");
						st = con.prepareStatement(sql);
						st.setInt(1, count);
						st.setInt(2, id_system);
						st.setTimestamp(3, day);
						st.executeUpdate();
					} else {
						sql = propSQL.getString("sql.Report_updateEVENT");
						st = con.prepareStatement(sql);
						st.setInt(1, count);
						st.setInt(2, id_system);
						st.setTimestamp(3, day);
						st.executeUpdate();
					}
				compare = true;
			} catch(SQLException sqle) {
				compare = false;
				log.setError("WorkReport.updEVENT(" + day + ", " + system + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.updEVENT(" + day + ", " + system + ")", "Нет соединения с БД REPORT.");
		}
		close();
		return compare;
	}
	
	public void deleteEVENT(Timestamp day, String system) {
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.Report_deleteEvent");
				st = con.prepareStatement(sql);
				st.setTimestamp(1, day);
				st.setString(2, system);
				st.executeUpdate();
				sql = propSQL.getString("sql.Report_ifISeventOnDay");
				st = con.prepareStatement(sql);
				st.setTimestamp(1, day);
				rs = st.executeQuery();
				if (rs.next())
					if (rs.getInt(1) == 0) {
						sql = propSQL.getString("sql.Report_deleteDay");
						st = con.prepareStatement(sql);
						st.setTimestamp(1, day);
						st.executeUpdate();
					}
			} catch(SQLException sqle) {
				log.setError("WorkReport.deleteEVENT(" + day + ", " + system + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.deleteEVENT(" + day + ", " + system + ")", "Нет соединения с БД REPORT.");
		}
		close();
	}
	
	private int getIDsystem(String system) {
		int id = 0;
		String sql = propSQL.getString("sql.Report_IDsystem");
		try {
			st = con.prepareStatement(sql);
			st.setString(1, system);
			rs = st.executeQuery();
			if (rs.next())
				id = rs.getInt(1);
		} catch(SQLException sqle) {
			log.setError("WorkReport.getIDsystem(" + system + ")", sqle.toString());
		}
		return id;
	}
	
	public boolean updateIncident(Timestamp DOWNTIME_START, Timestamp DOWNTIME_END, Time DOWNTIME, String HPC_ASSIGNEE, String RESOLUTION_CODE, String number, String HPC_SUPPORT_OBJECT, String HPC_REPORT, String DEPT, Clob ACTION, Clob EXPLANATION, String CAUSE_CODE, String HPC_ASSIGNMENT, Clob RESOLUTION, String HPC_STATUS, String LOGICAL_NAME, Timestamp sysmodtime) {
		boolean compare = false;
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.Report_ifIsNumber");
				st = con.prepareStatement(sql);
				st.setString(1, number);
				rs = st.executeQuery();
				if (rs.next())
					if (rs.getInt(1) > 0) {
						sql = propSQL.getString("sql.Report_updateIncident");
						st = con.prepareStatement(sql);
						st.setTimestamp(1, DOWNTIME_START);
						st.setTimestamp(2, DOWNTIME_END);
						st.setTime(3, DOWNTIME); 
						st.setString(4, HPC_ASSIGNEE);
						st.setString(5, RESOLUTION_CODE);
						st.setString(6, HPC_SUPPORT_OBJECT);
						st.setString(7, HPC_REPORT);
						st.setString(8, DEPT);
						st.setClob(9, ACTION);
						st.setClob(10, EXPLANATION);
						st.setString(11, CAUSE_CODE);
						st.setString(12, HPC_ASSIGNMENT);
						st.setClob(13, RESOLUTION);
						st.setString(14, HPC_STATUS);
						st.setString(15, LOGICAL_NAME);
						st.setTimestamp(16, sysmodtime);
						st.setString(17, number);
						st.executeUpdate();
						compare = true;
					}
			} catch(SQLException sqle) {
				compare = false;
				log.setError("WorkReport.updateIncident(" + number + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.updateIncident(" + number + ")", "Нет соединения с БД REPORT.");
		}
		close();
		return compare;
	}
	
	public int countDay(Timestamp begin, Timestamp end) {
		int count = 0;
//		String sql = propSQL.getString("sql.time");
		connect();
		if (con != null) {
			try {
				st = con.prepareStatement("SELECT (days('" + end + "')-days('" + begin + "')) FROM sysibm.sysdummy1");
//				st.setTimestamp(1, end);
//				st.setTimestamp(2, begin);
				rs = st.executeQuery();
				if (rs.next())
					count = rs.getInt(1);
			} catch(SQLException sqle) {
				log.setError("WorkReport.countDay(" + begin + ", " + end + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.countDay(" + begin + ", " + end + ")", "Нет соединения с БД REPORT.");
		}
		close();
		return count;
	}
	
	// кол-во записей в таблице ИНЦИДЕНТЫ за тек.месяц
	public int countIncidents(Timestamp begin, Timestamp end) {
		int count = 0;
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.Report_countIncidents");
				st = con.prepareStatement(sql);
				st.setTimestamp(1, begin);
				st.setTimestamp(2, end);
				rs = st.executeQuery();
				if (rs == null) {
					System.out.println("rs = null");
				} else {
					if (rs.next()) {
						count = rs.getInt(1);
					}
				}
			} catch(SQLException sqle) {
				log.setError("WorkReport.countIncidents(" + begin + ", " + end + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.countIncidents(" + begin + ", " + end + ")", "Нет соединения с БД REPORT.");
		}
		close();
        return count;
	}
	
	// удаление записей в таблице ИНЦИДЕНТЫ за тек.месяц
	public void deleteIncidents(Timestamp begin, Timestamp end) {
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.Report_deleteIncidents");
				st = con.prepareStatement(sql);
				st.setTimestamp(1, begin);
				st.setTimestamp(2, end);
				st.executeUpdate();
			} catch(SQLException sqle) {
				log.setError("WorkReport.deleteIncidents(" + begin + ", " + end + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.deleteIncidents(" + begin + ", " + end + ")", "Нет соединения с БД REPORT.");
		}
		close();
	}
	
	public void setTableSynchr(Timestamp day, int status, String system) {
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.Report_insSynchr");
				st = con.prepareStatement(sql);
				st.setTimestamp(1, day);
				st.setInt(2, status);
				st.setString(3, system);
				st.executeUpdate();
			} catch(SQLException sqle) {
				log.setError("WorkReport.setTableSynchr(" + day + ", " + system + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.setTableSynchr(" + day + ", " + system + ")", "Нет соединения с БД REPORT.");
		}
		close();
	}
	
	public void synchronization(Timestamp day, int status, String system) {
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.Report_updSynchr");
				st = con.prepareStatement(sql);
				st.setInt(1, status);
				st.setTimestamp(2, day);
				st.setInt(3, 1);
				st.setString(4, system);
				st.executeUpdate();
			} catch(SQLException sqle) {
				log.setError("WorkReport.synchronization(" + day + ", " + system + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.synchronization(" + day + ", " + system + ")", "Нет соединения с БД REPORT.");
		}
		close();
	}
	
	public int countEvent(Timestamp begin, Timestamp end, String system) {
		int count = 0;
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.Report_countEventOnSystem");
				st = con.prepareStatement(sql);
				st.setTimestamp(1, begin);
				st.setTimestamp(2, end);
				st.setString(3, system);
				rs = st.executeQuery();
				if (rs == null) {
					System.out.println("rs = null");
				} else {
					if (rs.next()) {
						count = rs.getInt(1);
					}
				}
			} catch(SQLException sqle) {
				log.setError("WorkReport.countEvent(" + begin + ", " + end + ", " + system + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.countEvent(" + begin + ", " + end + ", " + system + ")", "Нет соединения с БД REPORT.");
		}
		close();
        return count;
	}
	
	public int summEvent(Timestamp begin, Timestamp end, String system) {
		int summ = 0;
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.Report_summEventOnSystem");
				st = con.prepareStatement(sql);
				st.setTimestamp(1, begin);
				st.setTimestamp(2, end);
				st.setString(3, system);
				rs = st.executeQuery();
				if (rs == null) {
					System.out.println("rs = null");
				} else {
					if (rs.next()) {
						summ = rs.getInt(1);
					}
				}
			} catch(SQLException sqle) {
				log.setError("WorkReport.summEvent(" + begin + ", " + end + ", " + system + ")", sqle.toString());
			}
		} else {
			log.setConsole("WorkReport.summEvent(" + begin + ", " + end + ", " + system + ")", "Нет соединения с БД REPORT.");
		}
		close();
        return summ;
	}
	
	// закрытие соединения
	private void close() {
		try {
			if (con != null)
				con.close();
		} catch(SQLException sqle) {
			log.setError("WorkReport.close", sqle.toString() + "\nProgram exits");
			log.setConsole("WorkReport.close", sqle.toString() + "\nProgram exits");
			System.exit(0); 
		}
	}
	
}
