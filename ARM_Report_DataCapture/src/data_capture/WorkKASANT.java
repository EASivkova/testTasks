package data_capture;

import java.sql.*;
import ru.svrw.eivc.util.Properties;
import ru.svrw.eivc.util.StringUtils;

public class WorkKASANT {

	private StringUtils su = new StringUtils(); 
	private Properties propSQL = new Properties(su.replaceSystemParameter("${pathProject}/conf/query.properties"));
	private Properties propSettings = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));
	private static Connection con;
	private static ResultSet rs;
	private static PreparedStatement st;
	private Log log = new Log();
	
	/* загрузка драйвера для соединения с базой данных ПП */
	private static void loadDriver() throws ClassNotFoundException {
		String driver = "com.ibm.db2.jcc.DB2Driver";
		Class.forName(driver);
	}
	
	/* усановление соединения с базой данных ЕСПП*/
	private void connect() {
		try {
			loadDriver();
		} catch (ClassNotFoundException cnfe) {
			log.setError("WorkKASANT.loadDriver", cnfe + "\nProgram exits");
			log.setConsole("WorkKASANT.loadDriver", cnfe + "\nProgram exits");
			System.exit(0);
		}
		try {
			String username = propSettings.getString("bdKASANT.username");
			String password = propSettings.getString("bdKASANT.password");
			String protocol = propSettings.getString("bdKASANT.protocol");
			String host = propSettings.getString("bdKASANT.server");
			String file = propSettings.getString("bdKASANT.bd");
			String databaseUrl = protocol + host + file;
			con = DriverManager.getConnection(databaseUrl, username, password);
			con.setAutoCommit(true);
		} catch(SQLException sqle) { 
			log.setError("WorkKASANT.connect", sqle.toString());
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
	
	public int countVIOL(Timestamp begin, Timestamp end) {
		int count = 0;
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.KASANT_countVIOL");
				st = con.prepareStatement(sql);
				st.setTimestamp(1, begin);
				st.setTimestamp(2, end);
				st.setInt(3, 76);
				rs = st.executeQuery();
				if (rs == null) {
					System.out.println("rs = null");
				} else {
					if (rs.next()) {
						count = rs.getInt(1);
					}
				}
			} catch(SQLException sqle) { log.setError("WorkKASANT.countVIOL", sqle.toString()); }
		} else {
			log.setConsole("WorkKASANT.countVIOL", "Нет соединения с БД КАСАНТ.");
		}
        close();
        return count;
	}
	
	public int countTechBreak(Timestamp begin, Timestamp end) {
		int count = 0;
		connect();
		if (con != null) {
			try {
				String sql = propSQL.getString("sql.KASAT_countTECHbreak");
				st = con.prepareStatement(sql);
				st.setTimestamp(1, begin);
				st.setTimestamp(2, end);
				st.setInt(3, 76);
				rs = st.executeQuery();
				if (rs == null) {
					System.out.println("rs = null");
				} else {
					if (rs.next()) {
						count = rs.getInt(1);
					}
				}
			} catch(SQLException sqle) { log.setError("WorkKASANT.countTechBreak", sqle.toString()); }
		} else {
			log.setConsole("WorkKASANT.countTechBreak", "Нет соединения с БД КАСАНТ.");
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
			log.setError("WorkKASANT.close", sqle.toString() + "\nProgram exits"); 
			log.setConsole("WorkKASANT.close", sqle.toString() + "\nProgram exits");
			System.exit(0); 
		}
	}
	
}
