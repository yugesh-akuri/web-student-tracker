package com.web.student.tracker;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.sql.DataSource;

/**
 * Servlet implementation class StudentServlet
 */
@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private StudentDB studentDB;
    
    @Resource(name="jdbc/web_student_tracker")
    private DataSource dataSource;

    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init();
    	
    	//create a studentdb and pass the connection pool
    	studentDB = new StudentDB(dataSource);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			 
			//read the "command" parameter
			String theCommand = request.getParameter("command");
			
			//if the command is missing, then default to listing students
			if(theCommand == null) {
				theCommand = "LIST";
			}
			//route to the appropriate method
			switch (theCommand) {
			case "LIST": 
			         listStudents(request, response);
			         break;
			case "ADD":
				     addStudent(request, response);
				     break;
			case "LOAD":
				     loadStudent(request, response);
				     break;
			case "UPDATE":
				     updateStudent(request, response);
				     break;
			case "DELETE":
				     deleteStudent(request, response);
				     break;
			default: 
				     listStudents(request, response);
			}
		}
		 catch(Exception e) {
			 throw new ServletException(e);
		 }
		
	}
	
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read the student info from form data
		String theStudentId = request.getParameter("studentId");
		
		//delete student from database
		studentDB.deleteStudent(theStudentId);
		
		//send them back to "listStudents" page
		listStudents(request, response);
	}

	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read the student info from form data
		int id = Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		
		//create a new student object
		Student theStudent = new Student(id, firstName, lastName, email, phoneNumber);
		
		//perform update on the database
		studentDB.updateStudents(theStudent);
		
		//send them back to "listStudents" page
		listStudents(request, response);
		
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read the studentId from the form data
		String theStudentId = request.getParameter("studentId");
		
		//get student from database (StudentDB)
		Student theStudent = studentDB.getStudent(theStudentId);
		
		//place student in request Attribute
		request.setAttribute("the_student", theStudent);
		
		//send to jsp page update-student-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}

	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read student info from form data
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		
		//create a new student object
		Student theStudent = new Student(firstName, lastName, email, phoneNumber);
		
		//add the student to the database
		studentDB.addStudent(theStudent);
		
		//send back to main page (the student list)
		listStudents(request, response);
	}

	public void listStudents(HttpServletRequest request, HttpServletResponse response)  throws Exception {
		try {
			//get the students list from studentDB
			List<Student> students = studentDB.getStudents();
			
			PrintWriter out = response.getWriter();
			out.println(students);
			//set attribute to students list
			request.setAttribute("student_list", students);
			
			
			//pass to JSP page
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
			dispatcher.forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
