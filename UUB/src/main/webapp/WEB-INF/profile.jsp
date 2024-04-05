<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="uub.model.Customer"%>
    
<%@ page import="uub.enums.EmployeeRole"%>
<%@ page import="uub.model.User"%>
<%@ page import="uub.model.Employee"%>
<%@ page import="uub.staticlayer.DateUtils"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
   response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
   response.setDateHeader("Expires", 0); // Proxies.
%>
<html>

<%@include file="/WEB-INF/includes/head.jsp"%>


<body>

<%@include file="/WEB-INF/includes/mainheader.jsp"%>

<% pageContext.setAttribute("page", "profile"); 
                   
String type = (String)request.getAttribute("type"); %>


		<%if(type.equals("customer")){ %>
		    
				<%@include file="/WEB-INF/includes/userNav.jsp"%>
		    
		    <%}else{ %>
		    
		
				<%@include file="/WEB-INF/includes/employeeNav.jsp"%>
		
		   <%} %>



    <div class="body">


        <div class="body-2">
         <%
         
         if(request.getAttribute("edit")!=null){ %>
                <div class="profile-div">
                <div class="profile-header">

                    <h3>Change Password</h3>
                </div>
                              
                <%
                
                
                if(request.getAttribute("done")!=null){ %>
                	
                	
                	  <div class="success-div">
                    <div class="content">
                        <i class="fa-regular fa-face-smile"></i>
                        <h3 style="color: #316952;">Password change Successful !!</h3>
                    </div>
                      <form action="profile" method="get">
                    <div class="ok-button">
                        <input class="button" type="submit" value="OK">
                    </div>
                    </form>
                </div>
                </div>
                
                	
               <%} else{%>
                
				<%@include file="/WEB-INF/includes/errorMsg.jsp"%>


                <div class="transfer-form">
                    <form action="changePassword" method="POST">

                        <div class="input-div">
                            <div class="label-div">
                             <div class="values">
                                <label for="old-password">Old Password:</label>
                                 <input type="password" id="oldPassword" name="oldPassword" required>
                                 </div>
                                 <div class="warning-block">
                                     <ul>
								        <li>Password should contain at least 8 characters</li>
								        <li>Password should contain at least one uppercase letter</li>
								        <li>Password should contain at least one lowercase letter</li>
								        <li>Password should contain at least one number</li>
								        <li>Password should contain at least one special character (e.g., !, @, #, $, %)</li>
								        <li>Password should not contain spaces</li>
								        <li>Password should not be a commonly used password</li>
								    </ul>
								    </div>
                                  <div class="values">
                                <label for="new-password">New Password:</label>
                                 <input type="password" id="newPassword" name="newPassword" required>
                                 </div>
                                  <div class="values">
                                <label for="repeat-password">Repeat Password:</label>
                                 <input type="password" id="repeatPassword" name="repeatPassword" required>
                                 </div>
                                

                            </div>
              

                        </div>
                        <div class="submit-button">
                            <input class="button" type="submit" value="Submit">
                         <a onclick="history.back()" class="red-button">Cancel</a>
                        </div>
                    </form>
                    

                </div>
                
                
              
             <%}}
         
         else{
        	 
        
         
        	User user = (User)request.getAttribute("profile");
         
        	String dateOfBirth = DateUtils.formatDate(user.getDOB());
        	String userType = user.getUserType().toString();
        	
         %>
            
            <div class="profile-div">
                <div class="profile-header">

                    <h3>Profile</h3>
                </div>

                <div class="profile">
                    <div class="field">
                        <label>ID:</label>
                        <span>${profile.id }</span>
                    </div>
                    <div class="field">
                        <label>Name:</label>
                        <span>${profile.name}</span>
                    </div>
                    <div class="field">
                        <label>Email:</label>
                        <span>${profile.email}</span>
                    </div>
                    
                    <div class="field">
                        <label>Phone:</label>
                        <span>${profile.phone }</span>
                    </div>
                    
                    <div class="field">
                        <label>Date of Birth:</label>
                        <span><%=dateOfBirth%></span>
                    </div>
                    
                    <div class="field">
                        <label>Gender:</label>
                        <span>${profile.gender }</span>
                    </div>
                   
                   
                   <%
                   if(type.equals("employee")){ 
                   
                	   Employee employee = (Employee) user;
                	   EmployeeRole role = employee.getRole();
                   %>
                    <div class="field">
                        <label>Branch ID:</label>
                        <span>${profile.branchId}</span>
                    </div>
                    <div class="field">
                        <label>Role:</label>
                        <span><%=role %></span>
                    </div>
                    
                    <%}else{ 
                    

                        Customer customer = (Customer) user;
                        String panNumber = customer.getPAN();
                    %>
                    
                      <div class="field">
                        <label>Aadhar:</label>
                        <span>${profile.aadhar}</span>
                    </div>
                    <div class="field">
                        <label>PAN:</label>
                        <span><%=panNumber %></span>
                    </div>
                    <div class="field">
                        <label>Address:</label>
                        <span>${profile.address}</span>
                    </div>
                    
                    <%} %>
                    
                    <form action="profile" method="get">
                    <div class="submit-button">
                        <input class="button" type="submit" value="Change Password">
                        <input type="hidden" name="edit" value="true">
                    </div>
                    </form>
                </div>
                </div>
                
               <%} %>
            
        </div>



</body>

</html>