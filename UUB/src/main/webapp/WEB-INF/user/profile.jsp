<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="uub.model.Customer"%>
<%@ page import="uub.staticlayer.DateUtils"%>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/mainStyle.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">


</head>


<body>

<%@include file="/WEB-INF/includes/mainheader.jsp"%>

<% pageContext.setAttribute("page", "profile"); %>

<%@include file="/WEB-INF/includes/userNav.jsp"%>


    <div class="body">


        <div class="body-2">
         <%
         
         if(request.getParameter("edit")!=null){ %>
                <div class="profile-div">
                <div class="profile-header">

                    <h3>Change Password</h3>
                </div>
                
                <%
                
                
                if(request.getParameter("done")!=null){ %>
                	
                	
                	  <div class="success-div">
                    <div class="content">
                        <i class="fa-regular fa-face-smile"></i>
                        <h3 style="color: #316952;">Password change Successful !!</h3>
                    </div>
                    <div class="ok-button">
                        <input class="button" type="submit" value="OK">
                    </div>
                </div>
                </div>
                
                	
               <%} else{%>
                
				<%@include file="/WEB-INF/includes/errorMsg.jsp"%>


                <div class="transfer-form">
                    <form action="changePassword" method="POST">

                        <div class="input-div">
                            <div class="label-div">
                            
                                <label for="old-password">Old Password:</label>
                                <label for="new-password">New Password:</label>
                                <label for="repeat-password">Repeat Password:</label>

                            </div>
                            <div class="space-div">

                                <input type="password" id="old-password" name="old-password">
                                <input type="text" id="new-password" name="new-password">
                                <input type="text" id="repeat-password" name="repeat-password">

                            </div>

                        </div>
                        <div class="submit-button">
                            <input class="button" type="submit" value="Submit">
                        </div>
                    </form>
                    

                </div>
                
                
              
                
             <%}}
         
         else{
         
        	Customer customer = (Customer)request.getAttribute("profile");
         
        	String dateOfBirth = DateUtils.formatDate(customer.getDOB());
        	String userType = customer.getUserType().toString();
        	/* 
        	String status = customer.getStatus().toString(); */
        	
        	String panNumber = customer.getPAN();
        	
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

                    <div class="field">
                        <label>User Type:</label>
                        <span><%= userType %></span>
                    </div>

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