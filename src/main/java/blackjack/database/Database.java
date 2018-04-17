package blackjack.database;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class Database {

	public static void main(String[] args) throws Exception {
//		createUserTable();
		//insertIntoUserTable("Test", "x@x.com", "aofengen", "xxxxxxxx");
		checkUserTable("a", "b");
	}
	
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

	public static JSONArray insertIntoUserTable(String name, String email, String username, String password) {
		Connection c = null;
		int id = 0;
		
		Date timeC = new Date();
		Date timeU = new Date();
		
		java.sql.Timestamp tC = new Timestamp(timeC.getTime());
		java.sql.Timestamp tU = new Timestamp(timeU.getTime());
		try {
			Class.forName("org.postgresql.Driver");
			c = getConnection();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");


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
			pstmt.setString(4, username);
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
		
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		
		obj.put("id", id);
		obj.put("name", name);
		obj.put("email", email);
		obj.put("username", username);
		obj.put("password", "<hidden>");
		obj.put("Record Created", tC);
		obj.put("Record Updated", tU);
		
		array.put(obj);	
		return array;
	}

	public static JSONArray checkUserTable(String email, String password) {
		Connection c = null;
		Statement stmt = null;
		int id = 0;
		String name = "";
		try {
			Class.forName("org.postgresql.Driver");
			c = getConnection();
			System.out.println("Opened database successfully");	
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USERS WHERE email = '" + email + "' AND password = '"
					+ password + "';");
			
			if (rs.next()) {
				System.out.println("Record Found! ");
				id = rs.getInt(1);
				name = rs.getString(2);
			} else {
				System.out.println("Record not found!");
			}

			stmt.close();
			c.close();
	} catch (Exception e) {
		System.out.println(e);
	}
    	JSONObject message = new JSONObject();
    	JSONArray array = new JSONArray();
    	
    	message.put("id", id);
    	message.put("email", email);
    	message.put("name", name);
    	message.put("password", "<hidden>");
    	message.put("token", "token");
    	array.put(message);
    	
		return array;
}
	
	private static Connection getConnection() throws URISyntaxException, SQLException {
//	    String dbUrl = System.getenv("JDBC_DATABASE_URL");
//		return DriverManager.getConnection(dbUrl);
	    return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/blackjack", "postgres",
			"9074dewberry1136");
	}

}
