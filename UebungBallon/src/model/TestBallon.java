package model;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TestBallon {

	static final String DB_URL = "jdbc:mysql://localhost/ballondb?user=root&password=";


	static Connection conn;
	static  Statement stmt;
	static DBManager dbm;

	@BeforeClass 
	public void TestDB(){
		try {
			dbm = new DBManager();

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			fail("Driver konnte nicht gestartet werden");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			fail("JDBC Driver konnte nicht ausgeführt werden");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@BeforeClass
	public void shouldMakeDBConnection(){

		try {  
			dbm = new DBManager();
			conn = dbm.getConnection();

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			assertTrue(conn!= null);
		}
	}

	@AfterClass
	public static void shouldCloseConnection() throws SQLException {
		DBManager t;
		try {
			t = new DBManager();
			System.out.println("Verbindung zu DB lösen");
			t.releaseConnection(conn);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(conn== null);

	
		return;
	} 

	@Test
	public void shouldInsertBuchung() throws InstantiationException, IllegalAccessException{
		try {
			DBManager db= new DBManager();
			conn = db.getConnection();

			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp d = new java.sql.Timestamp(calendar.getTime().getTime());

			SimpleDateFormat dn = new SimpleDateFormat("dd.MM.yyyy");
			String pD = dn.format(calendar.getTime());
			
			db.insertBallonFahrt( "hi" , 122, 5, 2, 2, conn);
			/** nicht Testrelevant **/
			
			db.insertBuchung( "hi" , 1, 2, conn);
			
			Buchung buchung; 
			buchung = db.getBuchung(2, conn);
			
//			assertEquals("hi",buchung.getD()); //getuserBymail oder so was mit mehrere ect. mit mehr where weil mail uniqce war
//			assertEquals(2,buchung.getpID());
//			assertEquals(2,buchung.getRbID());
			
			/** Aufräumen: **/ 
			
			db.deleteBuchung(2, conn);
			db.deleteBallonfahrt(2, conn);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("TInsertBuchungr geht nicht");
			e.printStackTrace();
		}

	}

	@Test
	public void shouldInsertPassgier() throws InstantiationException, IllegalAccessException{
		try {
			DBManager t= new DBManager();
			conn = t.getConnection();
			t.insertPassagier( "Hans", "Qurst", "h.w@mail.com", conn);
			
			String[] daten = new String[4];
			daten = t.getPassagier(3, conn);
			
			assertEquals("3",daten[0]);
			assertEquals("Hans",daten[1]);
			assertEquals("Qurst",daten[2]);
			assertEquals("h.w@mail.com",daten[3]);
			
			/** Aufräumen: **/ 
			t.deletePassagier(3, conn);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("TestInsertPassgaier geht nicht");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void shouldInsertOrt(){
		DBManager t;
		try {
			t = new DBManager();
			conn = t.getConnection();
			t.insertOrt(6, 6606, "Ort1", conn);
			
			String[] daten = new String[3];
			daten = t.getOrt(6, conn);
			
			assertEquals(6,daten[0]);
			assertEquals(6606, daten[1]);
			assertEquals("Ort1",daten[2]);
			
			/** Aufräumen: **/ 
			
			t.deleteOrt(6, conn);
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("TInsertOrt geht nicht");
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Classnotfound TestInsertOrt");
		}
	}


	@Test
	public void shouldReadTabelle(){
		DBManager db;
		try {
			db = new DBManager();
			conn = db.getConnection();
			db.readFluege(conn);

			assertTrue(db.readFluege(conn)!=null);
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("ReadTable geht nicht");
			e.printStackTrace();
//			fail();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("ReadTable geht nicht");
			e.printStackTrace();
		}	
	}

	@Test
	public void shouldGetPassagiere(){
		DBManager db;
		try {
			db = new DBManager();
			conn = db.getConnection();
			assertTrue(db.getPassagier(2,conn)!=null);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			fail();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void shouldGetBuchungen(){
		DBManager db;
		try {
			db = new DBManager();
			conn = db.getConnection();

			assertTrue(db.getBuchungen(conn)!=null);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldGetBuchung(){
		DBManager db;
		try {
			db = new DBManager();
			conn = db.getConnection();

			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp d = new java.sql.Timestamp(calendar.getTime().getTime());

			SimpleDateFormat dn = new SimpleDateFormat("dd.MM.yyyy");
			String pD = dn.format(calendar.getTime());
			
			db.insertBuchung( pD , 1, 1,conn);
			
			String[] daten = new String[5];
//			daten = db.getBuchung(3, conn);
			
			assertEquals(3,daten[0]);
			assertEquals(pD,daten[1]);
			assertEquals(1,daten[2]);
			assertEquals(1,daten[3]);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			fail();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void shouldGetOrte(){
		DBManager t;
		try {
			t = new DBManager();
			conn = t.getConnection();

			assertTrue(t.getOrte(conn)!=null);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			fail();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void shouldDeleteOrt()  {

		DBManager db;
		try {
			db = new DBManager();
			conn = db.getConnection();
			
			db.insertOrt(4, 6666, "ORT", conn);
			db.deleteOrt(4, conn);
			
			assertNull(db.getOrt(4,conn));
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("DeleteOrt geht nicht");
			e.printStackTrace();
		}
	}

	@Test
	public void shouldDeleteBuchung(){

		DBManager db;

		try {
			db = new DBManager();
			conn = db.getConnection();
			
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp d = new java.sql.Timestamp(calendar.getTime().getTime());

			SimpleDateFormat dn = new SimpleDateFormat("dd.MM.yyyy");
			String pD = dn.format(calendar.getTime());
			
			db.insertBuchung( pD , 1, 1, conn);
			db.deleteBuchung(2, conn);
			
			assertNull(db.getBuchung(2,conn));
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void shouldDeletePassagier(){

		DBManager db;
		try {
			db = new DBManager();
			conn = db.getConnection();
			
			db.insertPassagier("Name", "Name", "mail@mail.com", conn);
			db.deletePassagier(4, conn);
			
			assertNull(db.getPassagier(4,conn));
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Test
	public void shouldDeleteBallonfahrt(){

		DBManager db;
		try {
			db = new DBManager();
			Connection con = db.getConnection();
			
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp d = new java.sql.Timestamp(calendar.getTime().getTime());

			SimpleDateFormat dn = new SimpleDateFormat("dd.MM.yyyy");
			String pD = dn.format(calendar.getTime());
			
			db.insertBuchung(pD , 1, 1, conn);
			db.deleteBallonfahrt(2, con);
			System.out.println("TestDeleteBallonfahrt");
			
			assertNull(db.getBallonfahrt(2,conn));
			
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}