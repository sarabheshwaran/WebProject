<%@page import="org.apache.jasper.tagplugins.jstl.core.Param"%>
<%@page import="uub.enums.AccountStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="uub.enums.EmployeeRole"%>
<%@ page import="uub.logicallayer.AdminHelper"%>
<%@ page import="uub.model.Branch"%>
<%@ page import="java.util.Map"%>
<%@ page import="uub.model.Account"%>
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
<% pageContext.setAttribute("page", "accounts");
    String type = (String)request.getAttribute("type"); %>


		<%if(type.equals("customer")){ %>
		    
				<%@include file="/WEB-INF/includes/userNav.jsp"%>
		    
		    <%}else{ %>
		    
		
				<%@include file="/WEB-INF/includes/employeeNav.jsp"%>
		
		   <%} %>
		   
<%@include file="/WEB-INF/includes/nameBar.jsp"%>


    <div class="body">
        <div class="body-1">

<%if(type.equals("customer")){ %>
		    
	<%@include file="/WEB-INF/includes/userVerticalNav.jsp"%>
		    
		    <%}else{ %>
		    
		
	<%@include file="/WEB-INF/includes/employeeVerticalNav.jsp"%>
		
		   <%} %>

        </div>

        <div class="body-2">
            <div class="body-rest">

                <h3>Account Portal</h3>
            </div>
            <div class="body-main">

            
            
            
<c:choose>
 <c:when test="${type eq 'customer'}">
 <c:choose>
 <c:when test="${action eq 'single' }">
 
  <% 
            
            Account account = (Account) request.getAttribute("account");
            
            %>
 
    <div class="body-header">
                    <h3>Account</h3>
                </div>

                <div class="create">
                    <div class="field">
                        <label>Account Number:</label>
                        <span>${account.accNo}</span>
                    </div>
                    <div class="field">
                        <label>User ID:</label>
                        <span>${account.userId}</span>
                    </div>
                    <div class="field">
                        <label>Branch:</label>
                        <span>${branch.name}</span>
                    </div>
                    <div class="field">
                        <label>Account Type:</label>
                        <span>${account.type}</span>
                    </div>
                    <div class="field">
                        <label>Balance:</label>
                        <span>${account.balance }</span>
                    </div>
                    <div class="field">
                        <label>Status:</label>
                         <%if(account.getStatus() == AccountStatus.ACTIVE){ %>
                        
                        <span>${account.status}</span>
                        
                        <%}else{ %>
                         <span style="color:red;">${account.status}</span>
                       
                        
                        <%} %>
                    </div>

                </div>
 
 </c:when>
<c:otherwise>
    			<div class="body-header">

                    <h3>Customer accounts</h3>
                </div>
                <div class="main-content">
                <div class="history-tab">

                    <table class="history-table">

                        <thead>
                            <td>Account No.</td>
                            <td>Account Type</td>
                            <td>Account Status</td>
                            <td>Balance</td>
                            <td>Branch ID:</td>
                            <td>Options</td>
                        </thead>
                    	 <c:forEach var="account" items="${accountMap.entrySet()}">
                            <tr>
	                            <td>${account.key}</td>
	                            <td>${account.value.type}</td>
	                            <td class="status ${account.value.status}">${account.value.status}</td>
	                            <td>${account.value.balance}</td>
	                            <td>${account.value.branchId}</td>
	                            <td>
	                                <div style="width: 100%;display: flex;justify-content: center;">
	
	                                    <form action="account" method="get">
	                                     <input type="hidden" name="accNo" value="${account.key}">
	                                        <input class="button" type="submit" value="See Account">
	                                    </form>
	                                    <form action="history" method="get">
	                                     <input type="hidden" name="accNo" value="${account.key}">
	                                        <input class="button" type="submit" value="See Transactions">
	                                    </form>
	                                </div>
	                            </td>
                        </tr>
                     
						</c:forEach>


                    </table>


                </div>

</div>
</c:otherwise>
</c:choose>
</c:when>
<c:otherwise>
<c:choose>
 <c:when test="${action eq 'search'}">
 		<div  class="container">
		 <h2>Search Account</h2>
				       
				<%@include file="/WEB-INF/includes/errorMsg.jsp"%>
				
			<form class="search-form" action="account" method="GET">
		       
	            <div class="form-group">
	                <input type="number" id="accNo" name="accNo" placeholder="Enter Account No.">
	            </div>
		        <input type="submit" value="Search">
			</form>
		</div>
 </c:when>
 
     <c:when test="${action eq 'create'}">
        
        <div class="body-header">
                    <h3>Creating account</h3>
                </div>
                
                
				<%@include file="/WEB-INF/includes/errorMsg.jsp"%>
                        <div class="create">
                    <form action="createAcc" method="POST" id="accountForm">
                       
                        <div class="form-group">
                            <label for="userId">User ID:</label>
                            <input type="number" id="userId" name="userId" placeholder="User ID" required>
                        </div>
                        <%int access = (int) request.getAttribute("access");
				
							if(access==1){%>
			 		 <div class="form-group">
                            <label for="branchId">Branch :</label>
                            <select id="branchId" name="branchId" required>
							
							<%
							
							AdminHelper adminHelper = new AdminHelper();
							Map<Integer,Branch> branches = adminHelper.getAllBranches();
							for (Map.Entry<Integer,Branch> branch : branches.entrySet()){%>
								
			
                                <option value="<%=branch.getKey()%>"><%=branch.getValue().getName() %></option>
							<%}%>
                            </select>
                        </div>
							<%}else{ 
							

								int branchId = (int) request.getSession().getAttribute("branchId");
							%>
							
							<input type="hidden" name="branchId" value="<%=branchId%>">
							 
                        <%} %>
                     
                        <div class="form-group">
                            <label for="type">Account Type:</label>
                            <select id="type" name="type" required>
                                <option value="0">Savings</option>
                                <option value="1">Current</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="balance">Balance:</label>
                            <input type="number" id="balance" name="balance" placeholder="Provide initial balance"  step="0.01" required>
                        </div>
                        <button type="submit">Create Account</button>
                    </form>
                </div>
                
                           <%if(request.getAttribute("success")!=null){ %>
            
            	   <div class="success-div">
            	   
                        <div class="content">
                            <i class="fa-regular fa-face-smile"></i>
                            <h3 style="color: #316952;">Account created Successfully !!</h3>
                        </div>

	             	</div>
            
            <%}	%>
        
    </c:when>
 
    <c:when test="${action eq 'view'}">
        <% 
            
            Account account = (Account) request.getAttribute("account");
            
            %>
            
                <div class="body-header">
                    <h3>Account</h3>
                </div>

                <div class="create">
                    <div class="field">
                        <label>Account Number:</label>
                        <span>${account.accNo}</span>
                    </div>
                    <div class="field">
                        <label>User ID:</label>
                        <span>${account.userId}</span>
                    </div>
                    <div class="field">
                        <label>Branch :</label>
                        <span>${branch.name}</span>
                    </div>
                    <div class="field">
                        <label>Account Type:</label>
                        <span>${account.type}</span>
                    </div>
                    <div class="field">
                        <label>Balance:</label>
                        <span>${account.balance}</span>
                    </div>
                    <div class="field">
                        <label>Status:</label>
                        <%if(account.getStatus() == AccountStatus.ACTIVE){ %>
                        
                        <span>${account.status}</span>
                        
                        <%}else{ %>
                         <span style="color:red;">${account.status}</span>
                       
                        
                        <%} %>
                    </div>
<div style="display: flex;">
                    <form style="display: flex; align-items: centre" " action="history" method="get">
                              <input type="hidden" name="accNo" value="${account.accNo}">
                                 <input style="margin: auto;" class="button" type="submit" value="See Transactions">
                             </form>
                    <form action="editAccount" method="POST">
                        <div class="submit-button">
                        	<input type="hidden" name="accNo" value="${account.accNo}">

                        <%if(account.getStatus() == AccountStatus.ACTIVE){ %>
                         <input class="red-button" type="submit" name="action" value="Deactivate">
                        
                        <%}else{ %>
                          <input class="green-button" type="submit" name="action" value="Activate">
                       
                        
                        <%} %>
                        </div>
                    </form>
                    </div>
                </div>
                   
    </c:when>
    <c:otherwise>
    			<div class="body-header">

                    <h3>Customer accounts</h3>
                </div>
                <div class="main-content">
                <div class="history-tab">

                    <table class="history-table">

                        <thead>
                            <td>Account No.</td>
                            <td>Account Type</td>
                            <td>Account Status</td>
                            <td>Branch ID:</td>
                            <td>Options</td>
                        </thead>
                    	 <c:forEach var="account" items="${accountMap.entrySet()}">
                            <tr>
	                            <td>${account.key}</td>
	                            <td>${account.value.type}</td>
	                            <td class="status ${account.value.status}">${account.value.status}</td>
	                            <td>${account.value.branchId}</td>
	                            <td>
	                                <div style="width: 100%;display: flex;justify-content: center;">
	
	                                    <form action="account" method="get">
	                                     <input type="hidden" name="accNo" value="${account.key}">
	                                        <input class="button" type="submit" value="See Account">
	                                    </form>
	                                </div>
	                            </td>
                        </tr>
                     
						</c:forEach>


                    </table>


                </div>

</div>
</c:otherwise>
    </c:choose>
</c:otherwise>
   
</c:choose>
  
            </div>
        </div>

    </div>



</body>

</html>