<!DOCTYPE html>

<html>
<head>
     <title>Update Student</title>
     
     <link type="text/css" rel="stylesheet" href="css/style.css" />
     <link type="text/css" rel="stylesheet" href="css/add-student-style.css" />
</head>
<body>

      <div id="wrapper">
          <div id="header">
              <h2>University Details</h2>
          </div>
      </div>
      
      <div id="container">
          <h3>Update Student</h3>
          
          <form action="StudentServlet" method="GET" >
          
               <input type="hidden" name="command" value="UPDATE" />
               
               <input type="hidden" name="studentId" value="${the_student.id}" />
               
               <table>
                   <tbody>
                         <tr>
                             <td><label>First Name:</label></td>
                             <td><input type="text" name="firstName" 
                                        value="${the_student.firstName}" /></td>
                         </tr>
                             
                         <tr>
                             <td><label>Last Name:</label></td>
                             <td><input type="text" name="lastName" 
                                        value="${the_student.lastName}"/></td>
                         </tr>
                         
                         <tr>
                             <td><label>Email:</label></td>
                             <td><input type="text" name="email" 
                                        value="${the_student.email}"/></td>
                         </tr>
                         
                         <tr>
                             <td><label>Phone Number:</label></td>
                             <td><input type="text" name="phoneNumber" 
                                        value="${the_student.phoneNumber}"/></td>
                         </tr>
                         
                         <tr>
                             <td><label></label></td>
                             <td><input type="submit" value="Save" class="save" /></td>
                         </tr>
                   </tbody>
               </table>
          </form>
      </div>
      
      <div style="clear: both;"></div>
      
      <p>
         <a href="StudentServlet" >Back to List</a>
      </p>

</body>
</html>