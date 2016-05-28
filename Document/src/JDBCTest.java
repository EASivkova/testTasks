import java.sql.*;
public class JDBCTest {
	//Connection to database
	private static Connection connection;
	//ResultSet used to store results in various methods
	private static ResultSet rs;
	//Statement used to store SQL-statement in various methods
	private static Statement statement;
	private static void loadDriver() throws ClassNotFoundException {
		String driver = "org.firebirdsql.jdbc.FBDriver";
		// load driver
		Class.forName(driver);
	}//loadDriver()
	private static Connection getConnection() throws SQLException {
		String username = "sysdba";
		String password = "masterkey";
		String protocol = "jdbc:firebirdsql:";
		String host = "//localhost/";
		String file = "c:\\PartPost111\\data\\PARTPOST.IB";
		String databaseUrl = protocol + host + file;
		//get and return connection
		return DriverManager.getConnection(databaseUrl, username, password);
	}//getConnection()
	private static void insertGuest() {
		try {
			//insert a guest - note: use \" to get a " in a String
			int noOfRows = statement.executeUpdate("INSERT INTO Z_ADRESAT (ID_ADRESAT, INDEXTO, REGION, AREA, CITY, STREET, HOUS, KORP, FLAT, FAMILY, NAME, SNAME, COMMENT_ADR, INN, ADR_FLAG, ADRES) " +
               "VALUES (140, '105064', NULL, NULL, 'ул.Братьев Быковых, 16, Москва', NULL, NULL, NULL, NULL, 'ЖД предприятие', NULL, NULL, NULL, NULL, 1, NULL)");
			System.out.println("Inserted rows (should be 1): " + noOfRows + "\n");
		} catch(SQLException sqle) {
			System.out.println(sqle + "\nProgram exits");
			System.exit(0);
		}//try-catch
	}//insertGuest()
	private static void simpleSelect() {
		//get inf. about guests with GuestNo larger than 8:
		try {
			rs = statement.executeQuery("SELECT * FROM Z_ADRESAT WHERE ID_ADRESAT > 137");
			//show inf.
			while (rs.next()) {
				int guestNo = rs.getInt("ID_ADRESAT");
				String name = rs.getString("FAMILY");
				String address = rs.getString("CITY");
				System.out.println(guestNo + "\t" + name + "\t" + address);
			}//while rows in resultset
			System.out.println();
		} catch(SQLException sqle) {
			System.out.println(sqle + "\nProgram exits");
			System.exit(0);
		}//try-catch
	}//simpleSelect()
	public static void main(String[] args) {
		//load driver
		try {
			loadDriver();
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe + "\nProgram exits");
			System.exit(0);
		}//try-catch
		//get connection and create statement
		try {
			connection = getConnection();
			//commits should be done explicitly:
			connection.setAutoCommit(true);
			//create a statement
			statement = connection.createStatement();
		} catch(SQLException sqle) {
			System.out.println(sqle + "\nProgram exits");
			System.exit(0);
		}//try-catch
		//perform basic SQL statements on database
		insertGuest();
		simpleSelect();
		//close connection
		try {
			connection.close();
		} catch(SQLException sqle) {
			System.out.println(sqle + "\nProgram exits");
			System.exit(0);
		}//try-catch
	}//main
}//class JDBCTest