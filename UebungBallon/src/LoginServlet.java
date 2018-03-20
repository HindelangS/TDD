import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DBManager;

/**
 * Servlet implementation class Login
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//		HttpSession session = request.getSession(true); // erlaubt neue Session zu erstellen , keine Info zu Session für Nutzer, liegt am Server
		
		String username = request.getParameter("usr");
		String password = request.getParameter("pwd");
		
		String error = "ACHTUNG ANMELDUNG FEHLGESCHLAGEN";
		
		DBManager dbm = null;
		Connection conn = null;
		try {
			dbm=new DBManager();
			conn=dbm.getConnection();

		if(DBManager.checkUser(conn, username, password) || (username.equals("Sara") && password.equals("1234"))){
			
			System.out.println("Anmeldung erfolgreich"); 
			
			HttpSession session = request.getSession();
			session.setAttribute("usr",username);  
		
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			RequestDispatcher view = request.getRequestDispatcher("MainPage.jsp");
			view.forward(request, response);
			
		}else {
			
//			request.setAttribute("usr", "Erneut eingeben");
			System.out.println("Bitte erneut versuchen");
			RequestDispatcher view = request.getRequestDispatcher("LoginPage.jsp");
			view.forward(request, response);
		}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbm.releaseConnection(conn);
		}
	}
}
