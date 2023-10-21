package com.web.student.tracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	//Define datasourse/connection pool for Resource Injection
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//step 1: set up printWriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		//step 2: Get a connection to the database
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
		     con = dataSource.getConnection();
		     //step 3: Create a SQL statements
		     String sql = "select * from student";
		     st = con.createStatement();
		
		     //step 4: Execute SQL query
		     rs = st.executeQuery(sql);
		
		     //step 5: Process the result set
		     while(rs.next()) {
		    	 int id = rs.getInt(1);
		    	 String firstName = rs.getString("first_name");
		    	 String lastName = rs.getString("last_name");
		    	 String email = rs.getString("email");
		    	 String phoneNumber = rs.getString("phone_number");
		    	 
		    	 out.println(id + firstName + lastName + email + phoneNumber);
		     }
		}
		 catch(Exception e) {
			 e.printStackTrace();
		 }
	}

}
