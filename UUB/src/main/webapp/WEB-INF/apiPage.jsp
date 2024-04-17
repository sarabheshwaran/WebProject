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

<% pageContext.setAttribute("page", "api"); %>
		    
		
<%@include file="/WEB-INF/includes/employeeNav.jsp"%>
<%@include file="/WEB-INF/includes/nameBar.jsp"%>
		



    <div class="body">

        <div class="body-2">

			
            
        </div>

</div>

</body>

</html>