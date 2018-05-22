package database;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

//import org.jasypt.util.password.StrongPasswordEncryptor;
import org.json.JSONArray;
import org.json.JSONObject;

//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;

public class BlackjackDatabase {
	
	public static void main(String[] args) throws Exception {
//		dropStatsTable();
//		createBlackjackStatsTable();
	}
	
//	private static void dropStatsTable() throws Exception {
//	Connection c = null;
//	Statement stmt = null;
//	try {
//		Class.forName("org.postgresql.Driver");
//		c = getConnection();
//		System.out.println("Opened database successfully");
//		
//		stmt = c.createStatement();
//		String sql = "DROP TABLE STATS";
//		stmt.executeQuery(sql);
//		stmt.close();
//		c.close();
//	} catch (Exception e) {
//		System.err.println( e.getClass().getName() + ": " + e.getMessage());
//	}
//	System.out.println("Table dropped successfully");
//}
	
	public static void createBlackjackStatsTable() throws Exception {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = getConnection();
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			String sql = "CREATE TABLE STATS " +
					"(ID INT PRIMARY KEY 		NOT NULL," +
					"HANDSWON		 INT	    NOT NULL," +
					"HANDSPLAYED 	 INT 		NOT NULL," +
					"BLACKJACKS    	 INT     	NOT NULL," +
					"MOSTMONEYWON    INT	    NOT NULL," +
					"TOTALMONEYWON   INT        NOT NULL," +
					"TIMECREATED    TIMESTAMP   NOT NULL," +
					"TIMEUPDATED    TIMESTAMP   NOT NULL)";
			stmt.executeQuery(sql);
			stmt.close();
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Stats table created successfully");
		c.close();
	}
	
	private static Connection getConnection() throws URISyntaxException, SQLException {
//	    String dbUrl = System.getenv("JDBC_DATABASE_URL");
//		return DriverManager.getConnection(dbUrl);
	    return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/blackjack", "postgres",
			"9074dewberry1136");
	}
	
	public static JSONArray getTopTen() throws Exception {
		Connection c = null;
		JSONArray array = new JSONArray();
		try {
			Class.forName("org.postgresql.Driver");
			c = getConnection();
			System.out.println("Opened database successfully");
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		try {
			Statement stmt = c.createStatement();
			Statement stmt2 = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM stats ORDER BY mostmoneywon DESC LIMIT 10");
			int i = 0;
			while (i < 10) {
				if (rs.next()) {
					int id = rs.getInt("id");
					int money = rs.getInt("mostmoneywon");
					int handsWon = rs.getInt("handswon");
					int handsPlayed = rs.getInt("handsplayed");
					int totalMoneyWon = rs.getInt("totalmoneywon");
					int blackjacks = rs.getInt("blackjacks"); 
					
					ResultSet rs2 = stmt2.executeQuery("SELECT name FROM users WHERE id = " + id + ";");
					String name = "";
					if(rs2.next()) {
						name = rs2.getString("name");
					}
					JSONObject obj = new JSONObject();
					
					obj.put("place", i + 1);
					obj.put("name", name);
					obj.put("money", money);
					obj.put("handswon", handsWon);
					obj.put("handsplayed", handsPlayed);
					obj.put("blackjacks", blackjacks);
					obj.put("totalmoney", totalMoneyWon);
					array.put(i, obj);
				}
				
				i++;
			}
			stmt2.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		c.close();
		return array;
	}
	
	public static JSONObject getStats(int id) throws Exception {
		Connection c = null;
		Statement stmt = null;
		JSONObject obj = new JSONObject();
		try {
			Class.forName("org.postgresql.Driver");
			c = getConnection();
			System.out.println("Opened database successfully");
			
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM stats WHERE id = '" + id + "';");
			
			if (rs.next()) {
				obj.put("handswon", rs.getInt("handswon"));
				obj.put("handsplayed", rs.getInt("handsplayed"));
				obj.put("blackjacks", rs.getInt("blackjacks"));
				obj.put("mostmoneywon", rs.getInt("mostmoneywon"));
				obj.put("totalmoneywon", rs.getInt("totalmoneywon"));
			}
			
		} catch (Exception e) {
			System.out.println(e);
			obj.put("error", "Unable to get stats: " + e.toString());
		}
		stmt.close();
		c.close();
		return obj;
	}
	
	public static JSONObject updateStatsTable(int id, int handsWon, int handsPlayed, int blackjacks, int highMoney, int totalMoney) throws Exception {
		Connection c = null;
		
		Date timeC = new Date();
		Date timeU = new Date();
		
		java.sql.Timestamp tC = new Timestamp(timeC.getTime());
		java.sql.Timestamp tU = new Timestamp(timeU.getTime());
		
		JSONObject obj = new JSONObject();
		try {
			Class.forName("org.postgresql.Driver");
			c = getConnection();
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			
			PreparedStatement pstmt = null;
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM stats WHERE id = '" + id + "';");
			
			if (rs.next()) {
				System.out.println("record exists. updating.");
				int oldHighMoney = rs.getInt("mostmoneywon");
				if (oldHighMoney >= highMoney) {
					highMoney = oldHighMoney;
				}
				
				pstmt = c.prepareStatement("UPDATE STATS SET HANDSWON = ?, HANDSPLAYED = ?, BLACKJACKS = ?, MOSTMONEYWON = ?, TOTALMONEYWON = ?, TIMEUPDATED = ? WHERE ID = ?");
					pstmt.setInt(1, rs.getInt("handswon") + handsWon);
					pstmt.setInt(2, rs.getInt("handsplayed") + handsPlayed);
					pstmt.setInt(3, rs.getInt("blackjacks") + blackjacks);
					pstmt.setInt(4, highMoney);
					pstmt.setInt(5, rs.getInt("totalmoneywon") + totalMoney);
					pstmt.setTimestamp(6, tU);
					pstmt.setInt(7, id);
			} else {
				pstmt = c.prepareStatement("INSERT INTO STATS (ID, HANDSWON, HANDSPLAYED, BLACKJACKS, MOSTMONEYWON, TOTALMONEYWON, TIMECREATED, TIMEUPDATED)"
		            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				pstmt.setInt(1, id);
				pstmt.setInt(2, handsWon);
				pstmt.setInt(3, handsPlayed);
				pstmt.setInt(4, blackjacks);
				pstmt.setInt(5, highMoney);
				pstmt.setInt(6, totalMoney);
				pstmt.setTimestamp(7, tC);
				pstmt.setTimestamp(8, tU);	
			}
			pstmt.executeUpdate();
			pstmt.close();
			
			stmt.close();
			c.commit();
			obj.put("message", "Stats Successfully Updated!");
		} catch (Exception e) {
			System.out.println(e);
			obj.put("error", "Stat Update Failed! " + e.toString());
		}
			
			c.close();
			return obj;
	}


}
