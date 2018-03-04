package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null; 
	
	public DBManager() 

			throws InstantiationException, 
			IllegalAccessException,  
			ClassNotFoundException {

		super();
		/** DB Driver hinzufügen */
		
		
		   
		Class.forName("com.mysql.jdbc.Driver").newInstance();


	} 

	public Connection getConnection() throws SQLException{
		/** neue Connection holen **/
		Connection conn = null;
		try{
			 
				conn = DriverManager.getConnection("jdbc:mysql://localhost/ballondb?user=root&password=");

		}catch(SQLException ex){
			System.out.println("SQLException :" + ex.getMessage());
			System.out.println("SQLStatus :" + ex.getSQLState());
			System.out.println("SQLException :"+ex.getErrorCode());
			throw ex; 	
		}
		return conn;

	}

	public void releaseConnection(Connection conn){
		try{
			conn.close();
		}catch(SQLException ex){
			System.out.println("Fehler beim Close() der Verbindung");
			System.out.println("Meldung: "+ex.getMessage());
			ex.printStackTrace();
		}
	}

	public List<TableBallon> readMyTable(Connection conn) throws SQLException{
		// TODO Auto-generated method stub
		ArrayList<TableBallon> result = new ArrayList<>();
		String query = "SELECT ballonfahrt.FlugID, ballonfahrt.MaxAnzPersonen, ort.NameOrt, pilot.Vorname, "
				+ "ballonfahrt.Zeitpunkt, ballonfahrt.Preis FROM ballonfahrt JOIN pilot USING(PilotID) "
				+ "JOIN ort USING(OrtID);";
		stmt = null; 
		rs = null;

		try{
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while(rs.next()){

				int FlugID= rs.getInt("flugid");
				String anzPers= rs.getString("maxanzpersonen");
				String ort = rs.getString("nameort");
				String pilotID = rs.getString("Vorname");
				int preis = rs.getInt("preis");
				int platz = rs.getInt("MaxAnzPersonen");
				String zeitpunkt = rs.getString("zeitpunkt");

				TableBallon item = new TableBallon(FlugID, anzPers, ort, pilotID, preis,platz, zeitpunkt);
				result.add(item);

			}
			rs.close(); rs= null;
			stmt.close(); stmt = null; 
			return result;

		}catch(SQLException ex){
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			throw ex; 
		}
	}
	
	public void insertPassagier(int pid, String vn, String nn, String e, Connection conn) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO passagier (PassagierID, Vorname, Nachname, email) VALUES (?,?,?,?);";
			stmt = conn.createStatement();
			pstmt.setInt(1, pid);
			pstmt.setString(2, vn);
			pstmt.setString(3, nn);
			pstmt.setString(4, e);
			stmt.executeUpdate(SQL);
			stmt.close();
	}
	
	public void insertOrt(int oid,int plz, String Ortsname, Connection conn) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO ort (OrtID,PLZ,NameOrt) VALUES (?,?,?);";
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, oid);
			pstmt.setInt(2,plz);
			pstmt.setString(3,Ortsname);
			pstmt.executeUpdate();
			pstmt.close();
	}
	
	public void insertBuchung(int idb, String datum, int pID, int rID, Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO buchung (BuchungsID,Datum,PassagierID, ReiseBuchungsID) VALUES (?,?,?,?);";
		pstmt = conn.prepareStatement(SQL);
		pstmt.setInt(1, idb);
		pstmt.setString(1, datum);
		pstmt.setInt(3,pID);
		pstmt.setInt(3,rID);
		pstmt.executeUpdate();
		pstmt.close();		
		
	}
	
	public void deletePassagier(int pid,Connection conn) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM passagier WHERE PassagierID = '"+pid+"'";
		stmt = conn.createStatement();
		stmt.executeUpdate(SQL);
		stmt.close();
	}
	
	public void deleteOrt(int oid,Connection conn) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM ort WHERE OrtID = '"+oid+"'";
		stmt = conn.createStatement();
		stmt.executeUpdate(SQL);
		stmt.close();
	}
	
	public void deleteBuchung(int bid,Connection conn) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM buchung WHERE BuchungsID = '"+bid+"'";
		stmt = conn.createStatement();
		stmt.executeUpdate(SQL);
		stmt.close();
	}
	
	public void deleteReise(int rid,Connection con) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM reise WHERE ReiseBuchungsID = '"+rid+"'";
		stmt = con.createStatement();
		stmt.executeUpdate(SQL);
		stmt.close();
	}
	
	public void deletBallonfahrt(int flid,Connection con) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM ballonfahrt WHERE FlugID = '"+flid+"'";
		stmt = con.createStatement();
		stmt.executeUpdate(SQL);
		stmt.close();
	}
	
	public String[][] getPassagiere(Connection con) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "Select * from passagier";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		System.out.println(99);
		String[][] daten = new String[99][2];
		int a = 0;
		while(rs.next()){
			System.out.println(rs.getString(1));
			daten[a][0] = rs.getString(1);
			daten[a][1] = rs.getString(2);
			daten[a][2] = rs.getString(3);
			daten[a][3] = rs.getString(4);
			System.out.println(daten[a][0]+" | "+daten[a][1]);
			a++;
		}
		return daten;
	}
	
	public String[] getPassagier(int pid,Connection con) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "Select * from passagier WHERE PassagierID = '"+pid+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		System.out.println(99);
		String[] daten = new String[99+1];
		while(rs.next()){
			System.out.println(rs.getString(1));
			daten[0] = rs.getString(1);
			daten[1] = rs.getString(2);
			daten[2] = rs.getString(3);
			daten[3] = rs.getString(4);
			System.out.println(daten[0]+" | "+daten[1]);
		}
		return daten;	
	}
	
	public String[] getBallonfahrt(int fid,Connection con) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "Select * from BallonFahrt WHERE FlugID = '"+fid+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		System.out.println(99);
		String[] daten = new String[99+1];
		while(rs.next()){
			System.out.println(rs.getString(1));
			daten[0] = rs.getString(1);
			daten[1] = rs.getString(2);
			daten[2] = rs.getString(3);
			daten[3] = rs.getString(4);
			System.out.println(daten[0]+" | "+daten[1]);
		}
		return daten;	
	}
	
	public String[][] getBuchungen(Connection con) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "Select * from buchung";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		System.out.println(99);
		String[][] daten = new String[99+1][2];
		int a = 0;
		while(rs.next()){
			System.out.println(rs.getString(1));
			daten[a][0] = rs.getString(1);
			daten[a][1] = rs.getString(2);
			daten[a][2] = rs.getString(3);
			daten[a][3] = rs.getString(4);
			System.out.println(daten[a][0]+" | "+daten[a][1]);
			a++;
		}
		return daten;
	}
	
	public String[] getBuchung(int bid, Connection con) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "Select * from buchung WHERE BuchungsID='"+bid+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		System.out.println(99);
		String[] daten = new String[99];
		while(rs.next()){
			System.out.println(rs.getString(1));
			daten[0] = rs.getString(1);
			daten[1] = rs.getString(2);
			daten[2] = rs.getString(3);
			daten[3] = rs.getString(4);
			System.out.println(daten[0]+" | "+daten[1]);
		}
		return daten;
	}
	
	public String[] getReise(int rid, Connection con) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "Select * from reise WHERE ReiseBuchungsID='"+rid+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		System.out.println(99);
		String[] daten = new String[99+1];
		while(rs.next()){
			System.out.println(rs.getString(1));
			daten[0] = rs.getString(1);
			daten[1] = rs.getString(2);

			System.out.println(daten[0]+" | "+daten[1]);
		}
		return daten;
	}
	
	
	public String[][] getOrte(Connection con) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "Select * from ort ";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		String[][] daten = new String[5][5];
		int a = 0;
		while(rs.next()){
			System.out.println(rs.getString(1));
			daten[a][0] = rs.getString(1);
			daten[a][1] = rs.getString(2);
			daten[a][2] = rs.getString(3);
			System.out.println(daten[a][0]+" | "+daten[a][1]+" | "+daten[a][2]);
			a++;
		}
		return daten;
	}

	public String[] getOrt(int oid,Connection con) throws SQLException{
		// TODO Auto-generated method stub
		String SQL = "Select * from ort WHERE OrtID = '"+oid+"'";
		stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(SQL);
		System.out.println(99);
		String[] daten = new String[5];
		while(rs.next()){
			System.out.println(rs.getString(1));
			daten[0] = rs.getString(1);
			daten[1] = rs.getString(2);
			daten[2] = rs.getString(3);
			System.out.println(daten[0]+" | "+daten[1]+" | "+daten[2]);
		}
		return daten;
	}
	
	public static void main(String[] argv) {

		try{
			DBManager db = new DBManager();
			Connection conn = db.getConnection();
			List<TableBallon> elemente = db.readMyTable(conn);
			for(TableBallon e : elemente){
				System.out.println(e);
			}
			db.releaseConnection(conn);
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public void insertReise(int rbID, int fID, Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO reise (ReiseBuchungsID, FlugIDs) VALUES (?,?);";
		try {
			stmt = conn.createStatement();
			pstmt.setInt(1, rbID);
			pstmt.setInt(1, fID);
			stmt.executeUpdate(SQL);
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
