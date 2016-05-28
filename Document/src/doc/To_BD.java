package doc;

import java.sql.*;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import ru.svrw.eivc.date.Calendar;
import ru.svrw.eivc.util.*;

public class To_BD {

	private StringUtils su = new StringUtils(); 
	private Properties propSQL = new Properties(su.replaceSystemParameter("${pathProject}/conf/query.properties"));
	private Properties propSettings = new Properties(su.replaceSystemParameter("${pathProject}/conf/settings.properties"));
	private static Connection connection;
	private static ResultSet rs;
	private static PreparedStatement st;
	public String report = "";
	
	/* загрузка драйвера для соединения с базой данных ПП */
	private static void loadDriver() throws ClassNotFoundException {
		String driver = "org.firebirdsql.jdbc.FBDriver";
		Class.forName(driver);
	}
	
	/* усановление соединения с базой данных Партионной Почты */
	private void connectPP() {
		try {
			loadDriver();
		} catch (ClassNotFoundException cnfe) { System.out.println(cnfe + "\nProgram exits"); System.exit(0); }
		try {
			String username = propSettings.getString("bdPP.username");
			String password = propSettings.getString("bdPP.password");
			String protocol = propSettings.getString("bdPP.protocol");
			String host = propSettings.getString("adres.server");
			String file = propSettings.getString("adres.bd");
			String databaseUrl = protocol + host + file;
			connection = DriverManager.getConnection(databaseUrl, username, password);
			connection.setAutoCommit(true);
		} catch(SQLException sqle) { 
			System.out.println(sqle); 
		}
	}
	
	// тест возможности соединения с базой
	public boolean testingCon() {
		boolean test = false;
		connectPP();
		if (connection != null) {
			test = true;
		} else {
    		test = false;
		}
        closePP();
        return test;
	}
	
	// загрузка содержимого таблицы на панели поиска ID конвертов по нажатию на кнопку
	public void getDataTable_LastID(DefaultTableModel dtm) {
		report = "";
		Calendar today = new Calendar();
		today.addMonth(-6);
		System.out.println(today.getYear() + "-" + today.getMonth() + "-01 00:00:00.0");
		connectPP();
		if (connection != null) {
			try {
				String sql = propSQL.getString("sql.sel_BARCODEnaMAXDAPO");       
				st = connection.prepareStatement(sql);
				st.setTimestamp(1, Timestamp.valueOf(today.getYear() + "-" + (today.getMonth()<10?"0":"") + today.getMonth() + "-01 00:00:00.0"));
				rs = st.executeQuery();
				while (rs.next()) {
					Vector row = new Vector();
					row.add("<html>" + rs.getString(2) + "<br><font size=2>" + rs.getString(1) + "</font></html>");
					row.add(rs.getString(3));
					row.add("");
					dtm.addRow(row);
				}
			} catch(SQLException sqle) { System.out.println(sqle); }
		} else {
    		report = "Нет соединения с БД Партионной почты. Удостовертесь, что VIPNet установлен в режим Внутренняя конфигурация. ";
		}
        closePP();
	}
	
	/* загрузка содержимого таблицы всех ID конвертов по выбранному предприяию при нажатии на кнопку */
	public void getDataAllID(String nameF, String adrF, DefaultTableModel dtm) {
		report = "";
		connectPP();
		if (connection != null) {
			try {
				String sql = propSQL.getString("sql.sel_BARCODEforFIRM");       
				st = connection.prepareStatement(sql);
				st.setString(1, adrF);
				st.setString(2, nameF);
				rs = st.executeQuery();
				while (rs.next()) {
					Vector row = new Vector();
					row.add(rs.getString(1).substring(0, 10));
					row.add(rs.getString(2));
					row.add("");
					dtm.addRow(row);
				}
			} catch(SQLException sqle) { System.out.println(sqle); }
		} else {
			report = "Нет соединения с БД Партионной почты. Удостовертесь, что VIPNet установлен в режим Внутренняя конфигурация. ";
		}
        closePP();
	}
	
	public Vector createListIdLetter(String startDay, String endDay) {
		Vector list = new Vector();
		connectPP();
		try {
			String sql = propSQL.getString("sql.sel_BARCODEoverPERIOD");       
			st = connection.prepareStatement(sql);
			st.setTimestamp(1, Timestamp.valueOf(startDay));
			st.setTimestamp(2, Timestamp.valueOf(endDay));
			rs = st.executeQuery();
			while (rs.next()) {
				Vector row = new Vector();
				row.add(rs.getString(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getString(4));
				list.add(row);
			}
		} catch(SQLException sqle) { System.out.println(sqle); }
        closePP();
		return list;
	}
	
	// закрыие соединения с ПП
	private void closePP() {
		try {
			if (connection != null)
				connection.close();
		} catch(SQLException sqle) {
			System.out.println(sqle + "\nProgram exits"); 
			System.exit(0); 
		}
	}
	
}
