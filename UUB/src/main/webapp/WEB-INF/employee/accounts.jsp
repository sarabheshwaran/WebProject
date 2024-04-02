<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>

<%@include file="/WEB-INF/includes/head.jsp"%>


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

            
            
            
            <%if( request.getParameter("add")!= null){%>
            	
            	
            	
            	<div class="body-header">
                    <h3>Creating account</h3>
                </div>

            
            <%if(request.getAttribute("success")!=null){ %>
            
            	   <div class="success-div">
            	   
                        <div class="content">
                            <i class="fa-regular fa-face-smile"></i>
                            <h3 style="color: #316952;">Account created Successfully !!</h3>
                        </div>
                        
                   		<form action="transaction" method="get">
	                        <div class="ok-button">
	                            <input class="button" type="submit" value="OK">
	                        </div>
	                	</form>
	             	</div>
            
            <%}	%>
                <div class="create">
                    <form action="createAcc" method="POST" id="accountForm">
                       
                        <div class="form-group">
                            <label for="userId">User ID:</label>
                            <input type="text" id="userId" name="userId" required>
                        </div>
                        <div class="form-group">
                            <label for="branchId">Branch ID:</label>
                            <input type="text" id="branchId" name="branchId" required>
                        </div>
                        <div class="form-group">
                            <label for="type">Account Type:</label>
                            <select id="type" name="type" required>
                                <option value="savings">Savings</option>
                                <option value="current">Current</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="balance">Balance:</label>
                            <input type="number" id="balance" name="balance" step="0.01" required>
                        </div>
                        <button type="submit">Create Account</button>
                    </form>
                </div>
            	
            <% } else{ %>
            
            <% if(request.getAttribute("account")!=null){%>
            
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
                        <label>Branch ID:</label>
                        <span>${account.branchId}</span>
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
                        <span>${account.status}</span>
                    </div>


                    <form action="accounts" method="get">
                        <div class="submit-button">
                        	<input type="hidden" name="deActivate" value="${account.accNo}">
                            <input class="red-button" type="submit" value="Deactivate">
                        </div>
                    </form>
                </div>
            
            
            <%} else if(request.getAttribute("accounts")!= null){%>
            
            
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
                    	 <c:forEach var="account" items="${accounts}">
                            <tr>
	                            <td>${account.accNo}</td>
	                            <td>${account.type}</td>
	                            <td class="status ${account.status}">${account.status}</td>
	                            <td>${account.balance}</td>
	                            <td>${account.branchId}</td>
	                            <td>
	                                <div style="width: 100%;display: flex;justify-content: center;">
	
	                                    <form action="accounts" method="POST">
	                                     <input type="hidden" name="accNo" value="${account.accNo}">
	                                        <input class="button" type="submit" value="See Account">
	                                    </form>
	                                     <form action="history" method="get">
	                                     <input type="hidden" name="accNo" value="${account.accNo}">
	                                        <input class="button" type="submit" value="See Transactions">
	                                    </form>
	                                    
	                                </div>
	                            </td>
                        </tr>
                     
						</c:forEach>


                    </table>


                </div>
            
            
            
            <%}
            
            
            
            else{%>
            
                <div class="body-header">

                    <h3>Branch accounts</h3>
                </div>
                <div class="main-content">
                
				<%@include file="/WEB-INF/includes/errorMsg.jsp"%>
				<%@include file="/WEB-INF/includes/employee-accounts/search.jsp"%>

</div>

<%}} %>
            </div>
        </div>

    </div>



</body>

</html>