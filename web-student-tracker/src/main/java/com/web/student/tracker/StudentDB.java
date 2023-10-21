package com.web.student.tracker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDB {

	private DataSource dataSource;
	
	
	public StudentDB(DataSource theDataSource) {
	     dataSource = theDataSource;
	}

	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<>();
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
	try {
		  
//		Class.forName("com.mysql.cj.jdbc.Driver");
		con = dataSource.getConnection();
		String sql = "SELECT * FROM student";
		st = con.createStatement();
		rs = st.executeQuery(sql);
	
		while(rs.next()) {
			int id = rs.getInt("id");
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String email = rs.getString("email");
			String phoneNumber = rs.getString("phoneNumber");
			
			Student tempStudent = new Student(id, firstName, lastName, email, phoneNumber);
		    
			students.add(tempStudent);
		}
//		close(con, st, rs);
		}
	catch(SQLException e) {		
         e.printStackTrace();
		//		System.out.println("Please check your connection");
	}
	finally {
		close(con, st, rs);
	}
		
		return students;
	}

	private void close(Connection con, Statement st, ResultSet rs) {
		
		try {
		if(con != null) {
			con.close();
		}
		if(st != null) {
			st.close();
		}
		if(rs != null) {
		   rs.close();
		}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void addStudent(Student theStudent) throws Exception {
		 
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			
			//get db connection
			con = dataSource.getConnection();
			
			// create sql for insert
			String sql = "insert into student"
			           + "(firstName, lastName, email, phoneNumber)"
			           + "values (?, ?, ?, ?)";
			pst = con.prepareStatement(sql);
			
			//set param values for the student 
			pst.setString(1, theStudent.getFirstName());
			pst.setString(2, theStudent.getLastName());
			pst.setString(3, theStudent.getEmail());
			pst.setString(4, theStudent.getPhoneNumber());
			
			//execute sql insert
			pst.execute();
		}
        finally {		
        	//clean up JDBC objects
        	close(con, pst, null);
       }
	}

	public Student getStudent(String theStudentId) throws Exception {
 
		Student theStudent = null;
        
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int studentId;
		
		try {
			//convert studentId to int
			studentId = Integer.parseInt(theStudentId);
			
			//get connection database
			con = dataSource.getConnection();
			
			//create sql statement to get selected student
			String sql = "select * from student where id=?";
			
			//create Prepared statement
			pst = con.prepareStatement(sql);
			
			//get params
			pst.setInt(1, studentId);
				
			//execute statement
			rs = pst.executeQuery();
			
			//get result using Result set 
			if(rs.next()) {
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phoneNumber");
				
				//use the studentId during construction
				theStudent = new Student(studentId, firstName, lastName, email, phoneNumber);
			}
			else {
				throw new Exception("could not find id: " + studentId);
			}
			return theStudent;
		}
		finally {
			close(con, pst, rs);
		}
		
	}

	public void updateStudents(Student theStudent) throws Exception {
		
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			//get db connection
			con = dataSource.getConnection();
			
			//create sql update statement
			String sql = "update student "
					   + "set firstName=?, lastName=?, email=?, phoneNumber=? "
					   + "where id=?";
			
			//create prepared statement
			pst = con.prepareStatement(sql);
			
			//get params
			pst.setString(1, theStudent.getFirstName());
			pst.setString(2, theStudent.getLastName());
			pst.setString(3, theStudent.getEmail());
			pst.setString(4, theStudent.getPhoneNumber());
			pst.setInt(5, theStudent.getId());
			
			//execute query
			pst.execute();
		}
		finally {
			close(con, pst, null);
		}
	}

	public void deleteStudent(String theStudentId) throws Exception {
		
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			//convert studentId to int
			int studentId = Integer.parseInt(theStudentId);
			
			//get connection to database
			con = dataSource.getConnection();
			
			//create sql to delete statement
			String sql = "delete from student where id=?";
			
			//create prepared statement
			pst = con.prepareStatement(sql);
			
			//set params
			pst.setInt(1, studentId);
			
			//execute sql statement
			pst.execute();
		}
		finally {
			close(con, pst, null);
		}
	}
}
