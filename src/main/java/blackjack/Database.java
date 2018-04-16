package blackjack;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

public class Database {

//	public static void main(String[] args) throws Exception {
////		createUserTable();
//		insertIntoUserTable("Test", "x@x.com", "aofengen", "xxxxxxxx");
//	}
	
	public static void createUserTable() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = getConnection();
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			String sql = "CREATE TABLE USERS " +
					"(ID INT PRIMARY KEY 		NOT NULL," +
					"NAME		 	TEXT		NOT NULL," +
					"EMAIL		 	TEXT		NOT NULL," +
					"USERNAME    	TEXT    	NOT NULL," +
					"PASSWORD   	TEXT	    NOT NULL," + 
					"TIMECREATED    TIMESTAMP   NOT NULL," +
					"TIMEUPDATED    TIMESTAMP   NOT NULL)";
			stmt.executeQuery(sql);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Table created successfully");
	}
//	
	public static void insertIntoUserTable(String name, String email, String username, String password) {
		Connection c = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = getConnection();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			Date timeC = new Date();
			Date timeU = new Date();
			
			java.sql.Timestamp tC = new Timestamp(timeC.getTime());
			java.sql.Timestamp tU = new Timestamp(timeU.getTime());
			
			int id = 0;
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = ( SELECT MAX (id) FROM users );");
			
			if (rs.next()) {
				id = rs.getInt(1) + 1;
			} else {
				id = 1;
			}
			stmt.close();
			
			PreparedStatement pstmt = c.prepareStatement("INSERT INTO USERS (ID, NAME, EMAIL, USERNAME, PASSWORD, TIMECREATED, TIMEUPDATED)"
	            + "VALUES (?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setString(4,  username);
			pstmt.setString(5, password);
			pstmt.setTimestamp(6, tC);
			pstmt.setTimestamp(7, tU);
			
			pstmt.executeUpdate();
			pstmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Record added successfully");
		
	}

	private static Connection getConnection() throws URISyntaxException, SQLException {
	    String dbUrl = System.getenv("JDBC_DATABASE_URL");
	    return DriverManager.getConnection(dbUrl);
	}

	public static void checkUserTable(String email, String password) {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = getConnection();
			System.out.println("Opened database successfully");	
			
			ResultSet rs = stmt.executeQuery("SELECT FROM USERS WHERE email = '" + email + "' AND password = '" +
							password + "';");
			
			if (rs.next()) {
				System.out.println("Record Found!");
			} else {
				System.out.println("Record not found!");
			}
	} catch (Exception e) {
		System.out.println(e);
	}
}

}
