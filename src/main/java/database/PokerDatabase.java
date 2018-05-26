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

public class PokerDatabase {

public static void main(String[] args) throws Exception {
//		createVideoPokerStatsTable();
	}

private static Connection getConnection() throws URISyntaxException, SQLException {
    String dbUrl = System.getenv("JDBC_DATABASE_URL");
	return DriverManager.getConnection(dbUrl);
//    return DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/blackjack", "postgres",
//		"9074dewberry1136");
}

//private static void dropVideoPokerStatsTable() throws Exception {
//Connection c = null;
//Statement stmt = null;
//try {
//	Class.forName("org.postgresql.Driver");
//	c = getConnection();
//	System.out.println("Opened database successfully");
//	
//	stmt = c.createStatement();
//	String sql = "DROP TABLE VIDEOPOKERSTATS";
//	stmt.executeQuery(sql);
//	stmt.close();
//	c.close();
//} catch (Exception e) {
//	System.err.println( e.getClass().getName() + ": " + e.getMessage());
//}
//System.out.println("Video Poker stats table dropped successfully");
//}


public static void createVideoPokerStatsTable() throws Exception {
	Connection c = null;
	Statement stmt = null;
	try {
		Class.forName("org.postgresql.Driver");
		c = getConnection();
		System.out.println("Opened database successfully");
		
		stmt = c.createStatement();
		String sql = "CREATE TABLE VIDEOPOKERSTATS " +
				"(ID INT PRIMARY KEY 		NOT NULL," +
				"HIGHMONEY		 INT	    NOT NULL," +
				"TOTALMONEY		 INT	    NOT NULL," +
				"HANDSPLAYED 	 INT 		NOT NULL," +
				"HANDSWON		 INT	    NOT NULL," +
				"ROYALFLUSH    	 INT     	NOT NULL," +
				"STRAIGHTFLUSH   INT	    NOT NULL," +
				"FOURKIND	     INT        NOT NULL," +
				"FULLHOUSE		 INT		NOT NULL," +
				"FLUSH			 INT	    NOT NULL," +
				"STRAIGHT		 INT	    NOT NULL," +
				"THREEKIND		 INT	    NOT NULL," +
				"TIMECREATED    TIMESTAMP   NOT NULL," +
				"TIMEUPDATED    TIMESTAMP   NOT NULL)";
		stmt.executeQuery(sql);
		stmt.close();
		System.out.println("Video Poker stats table created successfully");
	} catch (Exception e) {
		System.err.println( e.getClass().getName() + ": " + e.getMessage());
	}
	c.close();
}

public static JSONArray getTopTen() throws Exception {
	
	Connection c = null;
	JSONArray array = new JSONArray();
	try {
		Class.forName("org.postgresql.Driver");
		c = getConnection();
		System.out.println("Opened database successfully");
		
		Statement stmt = c.createStatement();
		Statement stmt2 = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM videopokerstats ORDER BY highmoney DESC LIMIT 10");
		int i = 0;
		while (i < 10) {
			if (rs.next()) {
				int id = rs.getInt("id");
				int highMoney = rs.getInt("highmoney");
				int handsWon = rs.getInt("handswon");
				int handsPlayed = rs.getInt("handsplayed");
				int totalMoney = rs.getInt("totalmoney");
				int royalFlush = rs.getInt("royalflush"); 
				int straightFlush = rs.getInt("straightflush"); 
				int fourKind = rs.getInt("fourkind"); 
				int fullHouse = rs.getInt("fullhouse");
				int flush = rs.getInt("flush"); 
				int straight = rs.getInt("straight"); 
				int threeKind = rs.getInt("threekind"); 

				ResultSet rs2 = stmt2.executeQuery("SELECT name FROM users WHERE id = " + id + ";");
				String name = "";
				if(rs2.next()) {
					name = rs2.getString("name");
				}
				JSONObject obj = new JSONObject();
				
				obj.put("place", i + 1);
				obj.put("name", name);
				obj.put("money", highMoney);
				obj.put("handswon", handsWon);
				obj.put("handsplayed", handsPlayed);
				obj.put("totalmoney", totalMoney);
				obj.put("royalflush", royalFlush);
				obj.put("straightflush", straightFlush);
				obj.put("fourkind", fourKind);
				obj.put("fullhouse", fullHouse);
				obj.put("flush", flush);
				obj.put("straight", straight);
				obj.put("threekind", threeKind);

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
//
public static JSONObject getStats(int id) throws Exception {
	Connection c = null;
	Statement stmt = null;
	JSONObject obj = new JSONObject();
	try {
		Class.forName("org.postgresql.Driver");
		c = getConnection();
		System.out.println("Opened database successfully");
		
		stmt = c.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM videopokerstats WHERE id = '" + id + "';");
		
		if (rs.next()) {
			obj.put("handswon", rs.getInt("handswon"));
			obj.put("handsplayed", rs.getInt("handsplayed"));
			obj.put("highmoney", rs.getInt("highmoney"));
			obj.put("totalmoney", rs.getInt("totalmoney"));
			obj.put("royalflush", rs.getInt("royalflush"));
			obj.put("straightflush", rs.getInt("straightflush"));
			obj.put("fourkind", rs.getInt("fourkind"));
			obj.put("fullhouse", rs.getInt("fullhouse"));
			obj.put("flush", rs.getInt("flush"));
			obj.put("straight", rs.getInt("straight"));
			obj.put("threekind", rs.getInt("threekind"));
		}
		
	} catch (Exception e) {
		System.out.println(e);
		obj.put("error", "Unable to get stats: " + e.toString());
	}
	stmt.close();
	c.close();
	return obj;
}

public static JSONObject updateStatsTable(int id, int handsWon, int handsPlayed, int highMoney, int totalMoney,
							int royalFlush, int straightFlush, int fourKind, int fullHouse, int flush, int straight, int threeKind) throws Exception
	{
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
		ResultSet rs = stmt.executeQuery("SELECT * FROM videopokerstats WHERE id = '" + id + "';");
		
		if (rs.next()) {
			System.out.println("record exists. updating.");
			int oldHighMoney = rs.getInt("highmoney");
			if (oldHighMoney >= highMoney) {
				highMoney = oldHighMoney;
			}
			
			pstmt = c.prepareStatement("UPDATE VIDEOPOKERSTATS SET HANDSWON = ?, HANDSPLAYED = ?, HIGHMONEY = ?, TOTALMONEY = ?, ROYALFLUSH = ?, STRAIGHTFLUSH = ?,"
								     + "FOURKIND = ?, FULLHOUSE = ?, FLUSH = ?, STRAIGHT = ?, THREEKIND = ?, TIMEUPDATED = ? WHERE ID = ?");
				pstmt.setInt(1, rs.getInt("handswon") + handsWon);
				pstmt.setInt(2, rs.getInt("handsplayed") + handsPlayed);
				pstmt.setInt(3, highMoney);
				pstmt.setInt(4, rs.getInt("totalmoney") + totalMoney);
				pstmt.setInt(5, rs.getInt("royalflush") + royalFlush);
				pstmt.setInt(6, rs.getInt("straightflush") + straightFlush);
				pstmt.setInt(7, rs.getInt("fourkind") + fourKind);
				pstmt.setInt(8, rs.getInt("fullhouse") + fullHouse);
				pstmt.setInt(9, rs.getInt("flush") + flush);
				pstmt.setInt(10, rs.getInt("straight") + straight);
				pstmt.setInt(11, rs.getInt("threekind") + threeKind);
				pstmt.setTimestamp(12, tU);
				pstmt.setInt(13, id);
		} else {
			pstmt = c.prepareStatement("INSERT INTO VIDEOPOKERSTATS (ID, HANDSWON, HANDSPLAYED, HIGHMONEY, TOTALMONEY, ROYALFLUSH, STRAIGHTFLUSH,"
					+ "FOURKIND, FULLHOUSE, FLUSH, STRAIGHT, THREEKIND, TIMECREATED, TIMEUPDATED)"
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, id);
			pstmt.setInt(2, handsWon);
			pstmt.setInt(3, handsPlayed);
			pstmt.setInt(4, highMoney);
			pstmt.setInt(5, totalMoney);
			pstmt.setInt(6, royalFlush);
			pstmt.setInt(7, straightFlush);
			pstmt.setInt(8, fourKind);
			pstmt.setInt(9, fullHouse);
			pstmt.setInt(10, flush);
			pstmt.setInt(11, straight);
			pstmt.setInt(12, threeKind);
			pstmt.setTimestamp(13, tC);
			pstmt.setTimestamp(14, tU);	
		}
		pstmt.executeUpdate();
		pstmt.close();
		
		stmt.close();
		c.commit();
		obj.put("message", "Video Poker Stats Successfully Updated!");
	} catch (Exception e) {
		System.out.println(e);
		obj.put("error", "Stat Update Failed! " + e.toString());
	}
		
		c.close();
		return obj;
}
	
}
