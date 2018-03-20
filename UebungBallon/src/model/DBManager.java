package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
	
	public DBManager() 

			throws InstantiationException, 
			IllegalAccessException,  
			ClassNotFoundException {

		super();
		/** DB Driver hinzufügen */
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();

	} 

	public Connection getConnection(){
		/** neue Connection holen **/
		Connection conn = null;
		try{
			 
				conn = DriverManager.getConnection("jdbc:mysql://localhost/ballondb?user=root&password=");

		}catch(SQLException ex){
			System.out.println("SQLException :" + ex.getMessage());
			System.out.println("SQLStatus :" + ex.getSQLState());
			System.out.println("SQLException :"+ex.getErrorCode());
 	
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

	public List<TableFlug> readFluege(Connection conn) {
		// TODO Auto-generated method stub
		List<TableFlug> result = new ArrayList<>();
		String query = "SELECT ballonfahrt.FlugID, ballonfahrt.MaxAnzPersonen, ort.NameOrt, pilot.Vorname, "
				+ "ballonfahrt.Zeitpunkt, ballonfahrt.Preis FROM ballonfahrt JOIN pilot USING(PilotID) "
				+ "JOIN ort USING(OrtID);";
		PreparedStatement pstmt = null; 
		ResultSet rs = null;

		try{
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery(query);

			while(rs.next()){

				int FlugID= rs.getInt("flugid");
				String anzPers= rs.getString("maxanzpersonen");
				String ort = rs.getString("nameort");
				String pilotID = rs.getString("Vorname");
				int preis = rs.getInt("preis");
				int platz = rs.getInt("MaxAnzPersonen");
				String zeitpunkt = rs.getString("zeitpunkt");

				TableFlug item = new TableFlug(FlugID, anzPers, ort, pilotID, preis,platz, zeitpunkt);
				result.add(item);

			}
			rs.close(); 
			rs= null;
			pstmt.close(); 
			pstmt = null; 
			return result;

		}catch(SQLException ex){
			if(rs != null) rs = null;
			if(pstmt != null) pstmt = null;
 
		}
		return result;
	}
	
	
	public List<TableBuchung> readBuchungen(Connection conn) {
		// TODO Auto-generated method stub
		ArrayList<TableBuchung> result = new ArrayList<>();
		String query = "SELECT buchung.BuchungsID, passagier.PassagierID, passagier.VorName, passagier.NachName, "
				+ "buchung.Datum FROM buchung JOIN passagier USING(PassagierID)";
		PreparedStatement pstmt = null; 
		ResultSet rs = null;

		try{
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery(query);

			while(rs.next()){

				int bID= rs.getInt("BuchungsID");
				int pID= rs.getInt("PassagierID");
				String pVN = rs.getString("Vorname");
				String pNN = rs.getString("Nachname");
				String datum = rs.getString("Datum");

				TableBuchung item = new TableBuchung(bID, pID, pVN, pNN, datum);
				result.add(item);

			}
			rs.close(); 
			rs= null;
			pstmt.close(); 
			pstmt = null; 
			return result;

		}catch(SQLException ex){
			if(rs != null) rs = null;
			if(pstmt != null) pstmt = null;
 
		}
		return result;
	}
	
	public void insertPassagier( String vn, String nn, String e, Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO passagier (PassagierID, Vorname, Nachname, email) VALUES (?,?,?,?);";

		try {
//			stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, vn);
		pstmt.setString(2, nn);
		pstmt.setString(3, e);
		pstmt.executeUpdate();
		pstmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void insertOrt(int oid,int plz, String Ortsname, Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO ort (OrtID,PLZ,NameOrt) VALUES (?,?,?);";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, oid);
			pstmt.setInt(2,plz);
			pstmt.setString(3,Ortsname);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	public void insertBuchung(String datum, int pID, int fID, Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO buchung (Datum,PassagierID,FlugID) VALUES (?,?,?);";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, datum);
			pstmt.setInt(2,pID);
			pstmt.setInt(3,fID);
			pstmt.executeUpdate();
			pstmt.close();	pstmt = null; 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
	public void insertBallonFahrt(String zp, int p, int maxp, int pID, int oid, Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "INSERT INTO ballonfahrt (Zeitpunkt,Preis, MaxAnzPersonen, PilotID, OrtID) VALUES (?,?,?,?,?);";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, zp);
			pstmt.setInt(2,p);
			pstmt.setInt(3,maxp);
			pstmt.setInt(4,pID);
			pstmt.setInt(5,oid);
			pstmt.executeUpdate();
			pstmt.close();	pstmt = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[][] getPassagiere(Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "Select * from passagier";
		PreparedStatement stmt;
		String[][] daten = null;
		try {
			stmt = conn.prepareStatement(SQL);
			ResultSet rs = stmt.executeQuery(SQL);
			System.out.println(99);
			daten = new String[99][5];
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return daten;
	}

	public String[] getPassagier(int pid,Connection conn){
		// TODO Auto-generated method stub
		String SQL = "Select * from passagier WHERE PassagierID = '"+pid+"'  ";
		ResultSet rs;
		String[] daten = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery(SQL);
			daten = new String[3];
			while(rs.next()){
				System.out.println(rs.getString(1));
				daten[0] = rs.getString(1);
				daten[1] = rs.getString(2);
				daten[2] = rs.getString(3);
				daten[3] = rs.getString(4);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return daten;	
	}
	
	public String[] getBallonfahrt(int fid,Connection conn){
		// TODO Auto-generated method stub
		String SQL = "Select * from BallonFahrt WHERE FlugID = '"+fid+"'";
		String[] daten = null;
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery(SQL);
			daten = new String[99];
			while(rs.next()){
//				System.out.println(rs.getString(1));
				daten[0] = rs.getString(1);
				daten[1] = rs.getString(2);
				daten[2] = rs.getString(3);
				daten[3] = rs.getString(4);
				System.out.println(daten[0]+" | "+daten[1]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return daten;	
	}
	
	public String[][] getBuchungen(Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "Select * from buchung";
		PreparedStatement pstmt;
		String[][] daten = null;
		try {
			pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery(SQL);

			daten = new String[99][7];
			int a = 0;
			while(rs.next()){
				System.out.println(rs.getString(1));
				daten[a][0] = rs.getString(1);
				daten[a][1] = rs.getString(2);
				daten[a][2] = rs.getString(3);
				daten[a][3] = rs.getString(4);
				daten[a][4] = rs.getString(5);
				a++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return daten;
	}
	
	public Buchung getBuchung(int bid, Connection conn){
		// TODO Auto-generated method stub
		String SQL = "Select * from buchung WHERE BuchungsID='"+bid+"'";
//		String[] daten = null;
		Buchung buchung = null;
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery(SQL);
//			daten = new String[99];
			while(rs.next()){
//				daten[0] = rs.getString(1);
//				daten[1] = rs.getString(2);
//				daten[2] = rs.getString(3);
//				daten[3] = rs.getString(4);
				
				int bID = rs.getInt(1);
				String d = rs.getString(2);
				int pID = rs.getInt(3);
				int rbID = rs.getInt(4);

				buchung= new Buchung(bID,d,pID,rbID); 
				return buchung;			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buchung;
	}
	
	public String[][] getOrte(Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "Select * from ort ";
		String[][] daten = null; 
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery(SQL);
			daten = new String[5][5];
			int a = 0;
			while(rs.next()){
				System.out.println(rs.getString(1));
				daten[a][0] = rs.getString(1);
				daten[a][1] = rs.getString(2);
				daten[a][2] = rs.getString(3);
				System.out.println(daten[a][0]+" | "+daten[a][1]+" | "+daten[a][2]);
				a++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return daten;
	}

	public String[] getOrt(int oid,Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "Select * from ort WHERE OrtID = '"+oid+"'";
		String[] daten = null; 
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery(SQL);
			daten = new String[5];
			while(rs.next()){
				System.out.println(rs.getString(1));
				daten[0] = rs.getString(1);
				daten[1] = rs.getString(2);
				daten[2] = rs.getString(3);
				System.out.println(daten[0]+" | "+daten[1]+" | "+daten[2]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return daten;
	}
	
	public void deletePassagier(int pid,Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM passagier WHERE PassagierID = '"+pid+"'";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);	
			pstmt.executeUpdate(SQL);
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void deleteOrt(int oid,Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM ort WHERE OrtID = '"+oid+"'";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate(SQL);
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void deleteBuchung(int bid,Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM buchung WHERE BuchungsID = '"+bid+"'";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate(SQL);
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void deleteBallonfahrt(int flid,Connection conn) {
		// TODO Auto-generated method stub
		String SQL = "DELETE FROM ballonfahrt WHERE FlugID = '"+flid+"'";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.executeUpdate(SQL);
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
//	public static void main(String[] argv) {
//
//		try{
//			DBManager db = new DBManager();
//			Connection conn = db.getConnection();
//			List<TableFlug> elemente = db.readFluege(conn);
//			for(TableFlug e : elemente){
//				System.out.println(e);
//			}
//			db.releaseConnection(conn);
//		}catch(Exception e){
//			System.err.println(e.getMessage());
//			e.printStackTrace();
//		}
//
//	}

}
