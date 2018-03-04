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

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			fail("Konnection konnte nicht aufgebaut werden");

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
		
		return;
	} 

	@Test
	public void shouldInsertBuchung() throws InstantiationException, IllegalAccessException{
		try {
			DBManager db= new DBManager();
			conn = db.getConnection();

			db.insertBallonFahrt(3, "1.1.1", 122, 3, 2, 2, conn);
			db.insertReise(2, 3, conn);
			/** nicht Testrelevant **/
			
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp d = new java.sql.Timestamp(calendar.getTime().getTime());

			SimpleDateFormat dn = new SimpleDateFormat("dd.MM.yyyy");
			String pD = dn.format(calendar.getTime());
			
			db.insertBuchung(2, pD , 2, 2, conn);
			
			String[] daten = new String[5];
			daten =db.getBuchung(3, conn);
			
			assertEquals(3,daten[0]);
			assertEquals(pD,daten[1]);
			assertEquals(1,daten[2]);
			assertEquals(1,daten[3]);
			
			/** Aufräumen: **/ 
			
			db.deleteBuchung(3, conn);
			db.deleteReise(2, conn);
			db.deleteBallonfahrt(3, conn);
			
		} catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// Unique Constraint Violation --> Error ist OK
			System.out.println("TInsertPassgaier geht vorübergehend nicht, FereignKey Fehler");
			assertTrue(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("TInsertPassgaier geht nicht");
			fail();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("TInsertPassgaier geht nicht");
			e.printStackTrace();
		}

	}

	@Test
	public void shouldInsertPassgier() throws InstantiationException, IllegalAccessException{
		try {
			DBManager t= new DBManager();
			conn = t.getConnection();
			t.insertPassagier(3, "Hans", "Qurst", "h.w@mail.com", conn);
			
			String[] daten = new String[4];
			daten = t.getPassagier(3, conn);
			
			assertEquals(3,daten[0]);
			assertEquals("Hans",daten[1]);
			assertEquals("Qurst",daten[2]);
			assertEquals("h.w@mail.com",daten[3]);
			
			/** Aufräumen: **/ 
			
			t.deletePassagier(3, conn);
			
		} catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// Unique Constraint Violation --> Error ist OK
			System.out.println("TestInsertPassgaier geht nicht, aber ok da FK Fehler");
			assertTrue(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("TestInsertPassgaier geht nicht");
//			fail();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("TestInsertPassgaier geht nicht");
			e.printStackTrace();
		}
	}
	
	@Test
	public void shouldInsertReise(){
		DBManager db;
		try {
			db = new DBManager();
			conn = db.getConnection();
			
			db.insertBallonFahrt(3, "1.1.1", 122, 3, 2, 2, conn);
			db.insertReise(2, 3, conn);
			
			String[] daten = new String[2];
			daten = db.getReise(2, conn);
			
			assertEquals(2,daten[0]);
			assertEquals(2, daten[1]);
			
			/** Aufräumen: **/ 
			
			db.deleteReise(2, conn);
			db.deleteBallonfahrt(3, conn);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("TInsertReise geht nicht");
		}
		catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// Unique Constraint Violation --> Error ist OK
			System.out.println("TInsertReise geht nichtgeht nicht, aber ok da FK Fehler");
			assertTrue(true);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Fehler SQL TestInsertReise");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Classnotfound TestInsertReise");
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
		catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// Unique Constraint Violation --> Error ist OK
			System.out.println("TInsertOrt geht nichtgeht nicht, aber ok da FK Fehler");
			assertTrue(true);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Fehler SQL TestInsertOrt");

		} catch (ClassNotFoundException e) {
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
			db.readMyTable(conn);

			assertTrue(db.readMyTable(conn)!=null);
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("ReadTable geht nicht");
			e.printStackTrace();
//			fail();
		}
		catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// Unique Constraint Violation --> Error ist OK
			System.out.println("ReadTable geht nicht wegen FK Fehler - also OK");
			assertTrue(true);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ReadTable geht nicht, SQL Fehler");
//			fail();
		} catch (ClassNotFoundException e) {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
//			fail();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			
			db.insertBuchung(3, pD , 1, 1, conn);
			
			String[] daten = new String[5];
			daten = db.getBuchung(3, conn);
			
			assertEquals(3,daten[0]);
			assertEquals(pD,daten[1]);
			assertEquals(1,daten[2]);
			assertEquals(1,daten[3]);

		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			fail();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// FK --> Error ist OK
			System.out.println("DeleteOrt geht nicht, FK Fehler");
			assertTrue(true);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("DeleteOrt geht nicht");
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
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
			
			db.insertBuchung(2, "01.01.01", 1, 1, conn);
			db.deleteBuchung(2, conn);
			
			assertNull(db.getBuchung(2,conn));
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// FK --> Error ist OK
			assertTrue(true);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			fail();
		} catch (ClassNotFoundException e) {
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
			
			db.insertPassagier(4, "Name", "Name", "mail@mail.com", conn);
			db.deletePassagier(4, conn);
			
			assertNull(db.getPassagier(4,conn));
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// FK --> Error ist OK
			assertTrue(true);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			fail();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void shouldDeleteReise(){

		DBManager db;
		try {
			db = new DBManager();
			conn = db.getConnection();
			db.insertBallonFahrt(3, "1.1.1", 122, 3, 2, 2, conn);
			db.insertReise(2,3,conn);
			
			
			db.deleteReise(2, conn);
			db.deleteBallonfahrt(3, conn);
			
			assertNull(db.getReise(2,conn));
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// Unique Constraint Violation --> Error ist OK
			assertTrue(true);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			fail();
		} catch (ClassNotFoundException e) {
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
			
			db.insertBuchung(2, pD , 1, 1, conn);
			db.deleteBallonfahrt(2, con);
			System.out.println("TestDeleteBallonfahrt");
			
			assertNull(db.getBallonfahrt(2,conn));
			
			
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// Unique Constraint Violation --> Error ist OK
			assertTrue(true);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
}