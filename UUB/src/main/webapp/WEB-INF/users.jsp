<%@page import="org.apache.jasper.tagplugins.jstl.core.Param"%>
<%@page import="uub.enums.AccountStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="uub.enums.EmployeeRole"%>
<%@ page import="uub.enums.UserStatus"%>

<%@ page import="uub.model.User"%>
<%@ page import="uub.model.Customer"%>
<%@ page import="uub.model.Employee"%>
<%@ page import="uub.model.Account"%>
<%@ page import="uub.staticlayer.DateUtils"%>
<%@ page import="java.util.List"%>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
   response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
   response.setDateHeader("Expires", 0); // Proxies.
%>
<!DOCTYPE html>
<html>

<%@include file="/WEB-INF/includes/head.jsp"%>


<body>

<%@include file="/WEB-INF/includes/mainheader.jsp"%>
<% pageContext.setAttribute("page", request.getAttribute("type")+"s"); %>
    
<%@include file="/WEB-INF/includes/employeeNav.jsp"%>

<%@include file="/WEB-INF/includes/nameBar.jsp"%>


    <div class="body">
        <div class="body-1">

	<%@include file="/WEB-INF/includes/employeeVerticalNav.jsp"%>
        </div>

        <div class="body-2">
            <div class="body-rest">

                <h3>${type eq 'customer' ? 'Customer Management' : 'Employee Management'} Portal</h3>
            </div>
            <div class="body-main">

            
            
            
<c:choose>

 <c:when test="${action eq 'search'}">
 		<div  class="container">
				       <h2>Search ${type eq 'customer' ? 'Customer' : 'Employee'}</h2>
				<%@include file="/WEB-INF/includes/errorMsg.jsp"%>
			<form class="search-form" action="${type}" method="GET">
	            <div class="form-group">
	                <input type="number" id="${type}Id" name="${type}Id" placeholder="Enter ${type} ID">
	            </div>
		        <input type="submit" value="Search">
			</form>
		</div>
				   
 </c:when>
 
     <c:when test="${action eq 'create'}">
     
             	<div class="body-header">
                    <h3>Creating ${type eq 'customer' ? 'Customer' : 'Employee'}</h3>
                </div>
                
    
                <div class="main-content">
                
                 
      <%if(request.getAttribute("success")!=null){ %>
            
            	   <div class="success-div">
            	   
                        <div class="content">
                            <i class="fa-regular fa-face-smile"></i>
                            <h3 style="color: #316952;">Customer created Successfully !!</h3>
                        </div>

	             	</div>

            <%}	%>
                
                
				<%@include file="/WEB-INF/includes/errorMsg.jsp"%>
                        <div class="create-user">
                    <form action="${type eq 'customer' ? 'createCustomer' : 'createEmployee'}" method="POST" >
                       
                        
					<div class="form-group">
                        <label>Name:</label>
                        <input type="text" id="name" name="name" value="${param.name}" required>
                    </div>
                    <div class="form-group">
                        <label>Email:</label>
                        <input type="email" id="email" name="email"  value="${param.email}"required>
                    </div>
                    <div class="form-group">
                        <label>Phone:</label>
                        <input type="tel" id="phone" name="phone"   oninput="validatePhoneNumber()" value="${param.phone}" required>
                    </div>
                    <div class="form-group">
                        <label>Date of Birth:</label>
                        <input type="date" id="dob" name="dob"  value="${param.dob}" required>
                    </div>
                    <div class="form-group">
                        <label>Gender:</label>
                        <select id="gender" name="gender"  value="${param.gender}" required>
				            <option value="male">Male</option>
				            <option value="female">Female</option>
				            <option value="other">Other</option>
				        </select><br>
                    </div>

                      <div class="form-group">
                        <label>Password:</label>
                        <input type="password" id="password" name="password" required>
                    </div>

					<c:choose>
					    <c:when test="${type eq 'customer'}">
					        <div class="form-group">
		                        <label>Aadhar:</label>
		                        <input type="text" id="aadhar" name="aadhar" value="${param.aadhar}" required>
		                    </div>
		                    <div class="form-group">
		                        <label>PAN:</label>
		                        <input type="text" id="pan" name="pan" value="${param.pan}" required>
		                    </div>
		                    <div class="form-group">
		                        <label>Address:</label>
		                        <input type="text" id="address" name="address" value="${param.address}" required>
		                    </div>
					    </c:when>
					    <c:otherwise>
					    	
							<div class="form-group">
		                        <label>Role:</label>
				                <select id="role" name="role"  required>
						            <option value="0">Staff</option>
						            <option value="1">Admin</option>
						        </select><br>
		                    </div>
		                    <div class="form-group">
		                        <label>Branch ID:</label>
                            <select id="branchId" name="branchId" required>
                                <option value="1">Main Branch</option>
                                <option value="2">Down Town Branch</option>
                            </select>
  		                    </div>
		                    
					    </c:otherwise>
					</c:choose>

                    
                    
                        <button type="submit">Create ${type}</button>
                    </form>
                </div>
                
            </div>
        
    </c:when>
 
    <c:when test="${action eq 'view'}">
            <% 
            	User user = (User)request.getAttribute("profile");
                
            	String dateOfBirth = DateUtils.formatDate(user.getDOB());
            	String profileType = user.getUserType().toString();
            	/* 
            	String status = profile.getStatus().toString(); */
            	
            %>
            
           		<div class="body-header">
                    <h3>${type eq 'customer' ? 'Customer' : 'Employee'}</h3>
                </div>
  <div class="main-content">
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
                        <label>profile Type:</label>
                        <span><%= profileType %></span>
                    </div>
 						<div class="field">
                        <label>Status:</label>
                        <%if(user.getStatus() == UserStatus.ACTIVE){ %>
                        
                        <span>${profile.status}</span>
                        
                        <%}else{ %>
                         <span style="color:red;">${profile.status}</span>
                       
                        
                        <%} %>
                    </div>
                    
                    <c:choose>
    					<c:when test="${type eq 'customer'}">
    					
                           <div class="field">
		                        <label>Aadhar:</label>
		                        <span>${profile.aadhar}</span>
		                    </div>
		                    
		                     <% Customer customer = (Customer) user;
		                    	String pan = customer.getPAN(); %>
		                    
		                    <div class="field">
		                        <label>PAN:</label>
		                        <span><%=pan %></span>
		                    </div>
		                    <div class="field">
		                        <label>Address:</label>
		                        <span>${profile.address}</span>
		                    </div>
		                    
		            <form action="customerAccounts" method="GET">
	                    <input type="hidden" name="${type}Id" value="${profile.id }">
	                    <div class="submit-button">
	                        <input class="button" type="submit" value="See Customer Accounts">
	                    </div>
                    </form>
		                   
					    </c:when>
					    <c:otherwise>
					    
					       <div class="field">
		                        <label>Role:</label>
		                        <span>${profile.role}</span>
		                    </div>
		                    <div class="field">
		                        <label>Branch ID:</label>
		                        <span>${profile.branchId}</span>
		                    </div>


					    </c:otherwise>
					</c:choose>
                    

                    <div style="display: flex;">
                    <form action="${type}" method="GET">
	                   	<input type="hidden" name="edit" value="true">
	                    <input type="hidden" name="${type}Id" value="${profile.id }">
	                    <div class="submit-button">
	                        <input class="button" type="submit" value="edit">
	                    </div>
                    </form>
                  	<form action="${type eq 'customer' ? 'editCustomer' : 'editEmployee'}" method="POST">
	                    <input type="hidden" name="${type}Id" value="${profile.id }">
	                    <div class="submit-button">
                        <%if(user.getStatus() == UserStatus.ACTIVE){ %>
                         <input class="red-button" type="submit" name="status" value="Deactivate">
                        
                        <%}else{ %>
                          <input class="green-button" type="submit" name="status" value="Activate">
                       
                        
                        <%} %>
	                    </div>
                    </form>
                    </div>
                </div>
               </div>    
    </c:when>
    <c:when test="${action eq 'edit'}">
            <% 
            	User user = (User)request.getAttribute("profile");
                
            	String dateOfBirth = DateUtils.formatDate(user.getDOB());
            	String profileType = user.getUserType().toString();
            	

            	/* 
            	String status = profile.getStatus().toString(); */
            	
            %>
            
           		<div class="body-header">
                    <h3>Account</h3>
                </div>
				<%@include file="/WEB-INF/includes/errorMsg.jsp"%>
                <div class="profile">
                <form action="${type eq 'customer' ? 'editCustomer' : 'editEmployee'}" method="POST">
                    
                
                
                    <div class="field">
                        <label>ID:</label>
                       
                        <span>${profile.id}</span>
       
                    </div>
                    <div class="field">
                        <label>Name:</label>
                        <input type="text" id="name" name="name" value="${profile.name}">
                    </div>
                    <div class="field">
                        <label>Email:</label>
                        <input type="text" id="email" name="email" value="${profile.email}">
                    </div>
                    <div class="field">
                        <label>Phone:</label>
                        <input type="text" id="phone" name="phone" value="${profile.phone}"  oninput="validatePhoneNumber()" required>
                    </div>
                    <div class="field">
                        <label>Date of Birth:</label>
                        <input type="date" id="dob" name="dob" value="<%= dateOfBirth %>">
                    </div>
                     <div class="field">
                        <label>Gender:</label>
                        <select id="gender" name="gender" required>
				            <option value="male"  <c:if test="${profile.gender eq 'male'}">selected</c:if>>Male</option>
				            <option value="female" <c:if test="${profile.gender eq 'female'}">selected</c:if>>Female</option>
				            <option value="other"  <c:if test="${profile.gender eq 'other'}">selected</c:if>>Other</option>
				        </select><br>
                    </div>
                    
                    <c:choose>
    					<c:when test="${type eq 'customer'}">
    						
		                    <div class="field">
		                        <label>Aadhar:</label>
		                        <input type="text" id="aadhar" name="aadhar" value="${profile.aadhar}">
		                        
		                    </div>
		                    
		                    <%  Customer customer = (Customer) user;
		                   		String pan = customer.getPAN(); %>
		                    
		                    
		                    <div class="field">
		                        <label>PAN:</label>
		                        <input type="text" id="pan" name="pan" value="<%=pan%>">
		                    </div>
		                    <div class="field">
		                        <label>Address:</label>
		                        
		                        <input type="text" id="address" name="address" value="${profile.address}">
		                    </div>
                    
					    </c:when>
					    <c:otherwise>
							
							 <%  Employee employee = (Employee) user;%>
							<div class="field">
		                        <label>Role:</label>
		                        <select id="role" name="role">
						            <% 
						                for (EmployeeRole role : EmployeeRole.values()) {
						            %>
						                <option value="<%= role.getRole() %>" <%=employee.getRole()==role ? "selected" : "" %>><%= role.toString() %></option>
						            <% 
						                }
						            %>
						        </select>
		                    </div>
		                    <div class="field">
		                        <label>Branch ID:</label>
		                        
		                        <input type="number" id="branchId" name="branchId" value="${profile.branchId}">
		                    </div>
		                    
					    </c:otherwise>
					</c:choose>
                    
                    <div style="display: flex;" class="submit-button">
                     <input type="hidden" name="${type}Id" value="${profile.id}">
                        <input class="green-button" type="submit" value="Save">
                         <a href="${type}?${type}Id=${profile.id}" class="red-button">Cancel</a>
                    </div>
                   
           </form>
                </div>
                   
    </c:when>

</c:choose>

            </div>
        </div>

    </div>


    <script>
        function validatePhoneNumber() {
            var phoneNumberInput = document.getElementById("phone");

            var phoneNumber = phoneNumberInput.value;

            var phonePattern = /^[6789]\d{9}$/;

            if (phonePattern.test(phoneNumber)) {
                phoneNumberInput.setCustomValidity("");
            } else {
                phoneNumberInput.setCustomValidity("Please enter a valid phone number ");
            }
        }
    </script>
</body>

</html>
            <%-- 
${type eq 'customer' ? 'editCustomer' : 'editEmployee'} --%>
            
            
