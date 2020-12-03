package orange.controller;

import java.io.IOException;
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

import orange.model.CommentBean;
import orange.service.CommentService;


/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DataSource ds = null;
    InitialContext ctxt = null;
    Connection conn = null;
	
 
    public CommentServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	      
	      try {
	    	  
	    	  ctxt = new InitialContext();  //載入context.xml系統預設
	    	  ds = ( DataSource ) ctxt.lookup("java:comp/env/jdbc/EmployeeDB");
			conn = ds.getConnection();
					
			
			if(request.getParameter("submit") != null) { //導向不同頁面
			System.out.println("submit");				//"xxxx"按鈕
				packProcess(request, response);}        //判斷使用的方法
			
			if(request.getParameter("comfirm") != null)
			insertProcess(request, response);
			
			if(request.getParameter("select") != null)
				selectProcess(request, response);
			
			if(request.getParameter("update") != null)
				updateProcess(request, response);
			
			if(request.getParameter("delete") != null)
				deleteProcess(request, response);
			
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
		
														//取值，做成Bean
		String name = request.getParameter("name");
		Integer stars = Integer.parseInt(request.getParameter("stars"));//這個就叫轉型		
		Date utilDate = new Date();
		Date date = utilDate;
		String comment = request.getParameter("comment");
		String photo = request.getParameter("photo");
		
		CommentBean commentBean = new CommentBean(name,stars,date,comment,photo);
		request.getSession(true).setAttribute("commentBean", commentBean);
		//呼叫session，把Bean放到session
		
		ServletContext servletContext = getServletContext();  //把context組態資訊帶進來。
	      RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/DisplayInsert.jsp");
	      requestDispatcher.forward(request, response);  //forward            //導向此JSP檔
	      
		System.out.println(request.getSession(true).getAttribute("commentBean"));
	}

	
	
	private void insertProcess (HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException, ServletException {
		
		
		
		CommentBean commentBean = (CommentBean)request.getSession(true).getAttribute("commentBean");
		CommentService commentService = new CommentService();
		if(commentService.insertComment(commentBean)>0)
		System.out.println("success");
		 else
			 System.out.println("fail"); 
	
		ServletContext servletContext = getServletContext();
	    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/Thanks.jsp");
	    requestDispatcher.forward(request, response);
		}
	
	private void selectProcess (HttpServletRequest request,HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		
		
		String name = request.getParameter("param");
		System.out.println("test"+name);
		
		CommentService commentService = new CommentService();
		CommentBean commentBean = commentService.selectComment(name);
		
		System.out.println("success");
			System.out.println("test2"+commentBean.getName());
			
			request.getSession(true).setAttribute("commentBean",commentBean);
			
			ServletContext servletContext = getServletContext();
		      RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/DisPlayComment.jsp");
		      requestDispatcher.forward(request, response);    //跳轉到其它後端 class
	}
	private void updateProcess (HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException, ServletException {
			
response.setContentType("text/html; charset=UTF-8");
		
		String name = request.getParameter("name");
		Integer stars = Integer.parseInt(request.getParameter("stars"));//這個就叫轉型
		Date utilDate = new Date();
		Date date = utilDate;
		String comment = request.getParameter("comment");
		String photo = request.getParameter("photo");
		
		CommentBean commentBean = new CommentBean(name,stars,date,comment,photo);
		CommentService commentService = new CommentService();
		if(commentService.updateComment(commentBean)>0)
			System.out.println("success");
		
		
		ServletContext servletContext = getServletContext();
	      RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/Thanks.jsp");
	      requestDispatcher.forward(request, response);
		
		
	}
	private void deleteProcess (HttpServletRequest request,
            HttpServletResponse response) throws SQLException, IOException, ServletException {
			
		
		String name = request.getParameter("name");
		
		CommentService commentService = new CommentService();
		if(commentService.deleteComment(name)>0)
			System.out.println("success");
		
		ServletContext servletContext = getServletContext();
	      RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/Thanks.jsp");
	      requestDispatcher.forward(request, response);
	      
	      
	  
		
	}
}

