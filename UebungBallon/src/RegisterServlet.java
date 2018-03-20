import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DBManager;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static int minPW = 8; 
	static int maxPW =  20;
	
	static int digit;
	static int code = -1;

	static int specialUN;

	public RegisterServlet() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("usr");
		String pwd = request.getParameter("pwd");		
		
		response.setContentType("text/html");  
		boolean registerok = false;
		
		DBManager m = null;
		Connection conn = null;

		if(pwdIsValid(pwd) && usernIsValid(username)) {
			if((userDB(username) == false)) {
				try {
					m = new DBManager();
					conn = m.getConnection();				
					m.RegisterBenutzer(conn,username, pwd);
					code = 0;
				} catch (InstantiationException e) {
					e.printStackTrace();
					code = 1;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					code = 1;
					e.printStackTrace();
				} catch (SQLException e) {
					code = 3;
					e.printStackTrace();
				} finally {
					m.releaseConnection(conn);
				}
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("LoginPage.jsp");

		switch(code) {
		case 0: 
			System.out.println("Anmeldung erfolgreich");
			rd.include(request, response);	break;
		case 1: 
			System.out.println("Fehlercode von Servlet: "+ code);
			rd.include(request, response); 
			break;
		case 2:
			System.out.println("Registrieren fehlgeschlagen, Passwort inkorrekt" );
			rd.include(request, response); 
			break;
		case 3: 
			System.out.println("Registrieren fehlgeschlagen, Email oder Username bereits registriert" );
			rd.include(request, response); 
			break;
		case 4:
			System.out.println("Registrieren fehlgeschlagen, Username inkorrekt" );
			rd.include(request, response); 
		default:
			System.out.println("Ein unbekannter Fehler ist aufgetreten");
			rd.include(request, response);
			break;
		}
	}

	

	public static boolean userDB(String username) {
		
		DBManager db = null;
		Connection conn = null;
		
		boolean userDB = false; 
		try {
			db = new DBManager();
			conn = db.getConnection();

			if( db.getUser(conn, username) != null) {
				System.out.println("Username "+ username +" darf nicht verwendet werden, er existiert bereits!");
				userDB = true; 
				return userDB;
			}
			else{
				System.out.println("Username "+ username +" darf verwendet werden, er existiert noch nicht!");
				userDB = false; 
				code = 3;
				return userDB;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.releaseConnection(conn);
		}
		return userDB; 
	}

	public static boolean usernIsValid(String username) {
		boolean unok; 

		for(int i = 0; i < username.length(); i++){

			char c = username.charAt(i);
			if(c >= 33 && c <= 46 ||c == 64){

				specialUN++;
				System.out.println("Achtung, bitte keine Sonderzeichen im Benutzername verwenden");
				unok = false; 
				code = 4;
				break;
			}
		}

		if(username.length() >= 3 && username.length() <= 15 && specialUN == 0) //null ok? laut DBManager zuerst null 
		{
			System.out.println("Gültigkeit: Username "+ username +" darf verwendet werden!");
			unok = true;
		}else {
			System.out.println("Gültigkeit: Username ungültig, bitte erneut eingeben");
			unok = false;
		}
		return unok; 
	}

	public static boolean pwdIsValid(String password) {

		boolean pwvalid = false; 
		
		if(password.length() >= minPW && password.length() <= maxPW){
			for(int i = 0; i < password.length(); i++){
				char c = password.charAt(i);

				if(Character.isDigit(c)){
					digit++;
				}
			}

			if( digit >= 1){
				pwvalid = true; 
				return pwvalid; 
			}
		}
		else {

			System.out.println("Passwortlänge falsch!");
			pwvalid = false; 
			return pwvalid; 
		}
		return pwvalid; 
	}
}
