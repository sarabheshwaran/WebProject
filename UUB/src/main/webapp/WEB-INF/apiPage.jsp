<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="uub.model.Customer"%>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

<% pageContext.setAttribute("page", "api"); %>
		    
		
<%@include file="/WEB-INF/includes/employeeNav.jsp"%>
<%@include file="/WEB-INF/includes/nameBar.jsp"%>
		



    <div class="body">

        <div class="body-2">
<div class="main-content">
                <div class="history-tab">
					<form action="createApi" method="post">
                             <input class="button" type="submit" value="Generate Key">
                         </form>
                    <table class="history-table">

                        <thead>
                            <td>Api key</td>
                            <td>Created Time</td>
                            <td>Validity (Days)</td>
                            <td></td>
                        </thead>
                    	 <c:forEach var="api" items="${apiList}">
                            <tr>
	                            <td>${api.apiKey}</td>  
    
	                            <td>${ DateUtils.formatTime(api.createdTime) }</td>
	                            <td>${api.validity}</td>
	                            <td>
	                                <div style="width: 100%;display: flex;justify-content: center;">
	
	                                    <form action="deleteApi" method="post">
	                                     <input type="hidden" name="apiKey" value="${api.apiKey}">
	                                        <input class="red-button" type="submit" value="Delete Key">
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

</body>

</html>