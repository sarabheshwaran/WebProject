<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    
<%@ page import="uub.model.Customer"%>
<!DOCTYPE html>
<html>

<%@include file="/WEB-INF/includes/head.jsp"%>
<%@ page import="uub.staticlayer.DateUtils"%>


<body>

<%@include file="/WEB-INF/includes/mainheader.jsp"%>
<% pageContext.setAttribute("page", "accounts"); %>
    
<%@include file="/WEB-INF/includes/employeeNav.jsp"%>

<%@include file="/WEB-INF/includes/nameBar.jsp"%>


    <div class="body">
        <div class="body-1">

	<%@include file="/WEB-INF/includes/employeeVerticalNav.jsp"%>
        </div>

        <div class="body-2">
            <div class="body-rest">

                <h3>Account Portal</h3>
            </div>
            <div class="body-main">
            
            
            <% if(request.getAttribute("customer")!=null){
            
            	Customer customer = (Customer)request.getAttribute("customer");
                
            	String dateOfBirth = DateUtils.formatDate(customer.getDOB());
            	String userType = customer.getUserType().toString();
            	/* 
            	String status = customer.getStatus().toString(); */
            	
            	String panNumber = customer.getPAN();
            %>
            
                            <div class="body-header">
                    <h3>Account</h3>
                </div>

                <div class="customer">
                    <div class="field">
                        <label>ID:</label>
                        <span>${customer.id }</span>
                    </div>
                    <div class="field">
                        <label>Name:</label>
                        <span>${customer.name}</span>
                    </div>
                    <div class="field">
                        <label>Email:</label>
                        <span>${customer.email}</span>
                    </div>
                    <div class="field">
                        <label>Phone:</label>
                        <span>${customer.phone }</span>
                    </div>
                    <div class="field">
                        <label>Date of Birth:</label>
                        <span><%=dateOfBirth%></span>
                    </div>
                    <div class="field">
                        <label>Gender:</label>
                        <span>${customer.gender }</span>
                    </div>

                    <div class="field">
                        <label>User Type:</label>
                        <span><%= userType %></span>
                    </div>

                    <div class="field">
                        <label>Aadhar:</label>
                        <span>${customer.aadhar}</span>
                    </div>
                    <div class="field">
                        <label>PAN:</label>
                        <span><%=panNumber %></span>
                    </div>
                    <div class="field">
                        <label>Address:</label>
                        <span>${customer.address}</span>
                    </div>
                    <form action="customer" method="get">
                    <div class="submit-button">
                        <input class="button" type="submit" value="Change Password">
                        <input type="hidden" name="edit" value="true">
                    </div>
                    </form>
                </div>
            
            
            <%} else{%>
            
                <div class="body-header">

                    <h3>Branch accounts</h3>
                </div>
                <div class="main-content">

					
				
				</div>
				
				<%} %>
            </div>
        </div>

    </div>



</body>

</html>