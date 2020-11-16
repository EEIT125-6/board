package orange;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.naming.java.javaURLContextFactory;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DataSource ds = null;
    InitialContext ctxt = null;
    Connection conn = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	      
	      try {
	    	  
	    	  ctxt = new InitialContext();
	    	  ds = ( DataSource ) ctxt.lookup("java:comp/env/jdbc/EmployeeDB");
			conn = ds.getConnection();
			CommentDAO commentDAO = new CommentDAO(conn);		
					
			
			if(request.getParameter("submit") != null) {
			System.out.println("submit");
				packProcess(request, response);}
			
			if(request.getParameter("comfirm") != null)
			insertProcess(request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	private void packProcess (HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException, ServletException {
		response.setContentType("text/html; charset=UTF-8");
		
		String name = request.getParameter("name");
		Integer stars = Integer.parseInt(request.getParameter("stars"));//這個就叫轉型
		Date utilDate = new Date();
		Date date = utilDate;
		String comment = request.getParameter("comment");
		String photo = request.getParameter("photo");
	
		CommentBean commentBean = new CommentBean(name,stars,date,comment,photo);
		request.getSession(true).setAttribute("commentBean", commentBean);
		
		ServletContext servletContext = getServletContext();
	      RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/DisplayInsert.jsp");
	      requestDispatcher.forward(request, response);
		System.out.println(request.getSession(true).getAttribute("commentBean"));
	}

	
	
	private void insertProcess (HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException {
		
		
		
		CommentBean commentBean = (CommentBean)request.getSession(true).getAttribute("commentBean");
		CommentDAO commentDAO = new CommentDAO(conn);
		if(commentDAO.insertComment(commentBean))
		System.out.println("success");
			System.out.println(conn);
	}

}
