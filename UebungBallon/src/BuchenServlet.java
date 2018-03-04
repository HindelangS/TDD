import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.DBManager;
import model.TableBallon;

/**
 * Servlet implementation class BuchenServlet
 */
@WebServlet("/BuchenServlet")
public class BuchenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBManager db;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		Connection conn = null;
		System.out.println("Get BUCHEN - Servlet");
		
		
		try{
			db = new DBManager();
			conn = db.getConnection();
			List <TableBallon> items = db.readMyTable(conn);  
			for(TableBallon myT : items) {
				System.out.println(myT);
			}

			Gson gson = new GsonBuilder().create();
			String result = gson.toJson(items);
			
			response.getWriter().append(result);

		}catch(SQLException e){
			response.getWriter().append(e.getMessage()); 
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {  

			if(conn != null) db.releaseConnection(conn);
		}
		
	}

}
