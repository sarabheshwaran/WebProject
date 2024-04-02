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
    
<%@include file="/WEB-INF/includes/userNav.jsp"%>

<%@include file="/WEB-INF/includes/nameBar.jsp"%>


    <div class="body">
        <div class="body-1">

	<%@include file="/WEB-INF/includes/userVerticalNav.jsp"%>
        </div>

        <div class="body-2">
            <div class="body-rest">

                <h3>Account Portal</h3>
            </div>
            <div class="body-main">
            
            
            <% if(request.getParameter("accNo")!=null){%>
            
                            <div class="body-header">
                    <h3>Account</h3>
                </div>

                <div class="create">
                    <div class="field">
                        <label>Account Number:</label>
                        <span>${account.key}</span>
                    </div>
                    <div class="field">
                        <label>User ID:</label>
                        <span>987654321</span>
                    </div>
                    <div class="field">
                        <label>Branch ID:</label>
                        <span>456</span>
                    </div>
                    <div class="field">
                        <label>Account Type:</label>
                        <span>${account.value.type}</span>
                    </div>
                    <div class="field">
                        <label>Balance:</label>
                        <span>$1000.00</span>
                    </div>
                    <div class="field">
                        <label>Status:</label>
                        <span>Active</span>
                    </div>


                    <form action="changePassword.html" method="get">
                        <div class="submit-button">
                            <input class="red-button" type="submit" value="Deactivate">
                        </div>
                    </form>
                </div>
            
            
            <%} %>
            
                <div class="body-header">

                    <h3>My accounts</h3>
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
	
	                                    <form action="account?accNo" method="get">
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
            </div>
        </div>

    </div>



</body>

</html>