<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE>
<html>
<head>
      <title>Student Details Tracker</title>
      
      <link type="text/css" rel="stylesheet" href="css/style.css" >
</head>

<body>

      <div id="wrapper">
          <div id="header">
              <h2>University Students</h2>
          </div>
      </div>
      
      <div id="container">
          <div id="content">
          
              <!-- put new button to add-student -->
              
              <input type="button" value="Add Student"
               onclick="window.location.href='add-student-form.jsp'; return false;"
               class="add-student-button" />
               
		      <table>
			      <tr>
			          <th>Student_id</th>
			          <th>First Name</th>
			          <th>Last Name</th>
			          <th>Email</th>
			          <th>Phone Number</th>
			          <th>Action</th>
			     </tr>
			 
			     <c:forEach var="tempStudent" items="${student_list}" >
	
	             <!--  set up a link for each student -->
	             
			     <c:url var="tempLink" value="StudentServlet">
			           <c:param name="command" value="LOAD" />
			           <c:param name="studentId" value="${tempStudent.id}" />
			     </c:url>
			     
			     <!-- set up a link to delete a student -->
			     <c:url var="deleteLink" value="StudentServlet">
			           <c:param name="command" value="DELETE" />
			           <c:param name="studentId" value="${tempStudent.id}" />
			     </c:url>
			     
				     <tr>
				         <td> ${tempStudent.id} </td>
				         <td> ${tempStudent.firstName} </td>
				         <td> ${tempStudent.lastName} </td>
				         <td> ${tempStudent.email} </td>
				         <td> ${tempStudent.phoneNumber} </td>
				         <td> <a href="${tempLink}" >Update</a>
				               |
				              <a href="${deleteLink}" 
				              onclick="if(!(confirm('Are you sure you want to delete this student?'))) return false">Delete</a>
				         </td> 
				     </tr>
			     </c:forEach>
		      </table>
          </div>
      </div>
</body>
</html>