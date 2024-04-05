<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
   response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
   response.setDateHeader("Expires", 0); // Proxies.
%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>


<%@include file="/WEB-INF/includes/head.jsp"%>


<body>

	<%@include file="/WEB-INF/includes/mainheader.jsp"%>
<% pageContext.setAttribute("page", "transaction"); %>
    
<%@include file="/WEB-INF/includes/userNav.jsp"%>

<%@include file="/WEB-INF/includes/nameBar.jsp"%>

    <div class="body">
        <div class="body-1">



	<%@include file="/WEB-INF/includes/userVerticalNav.jsp"%>
        </div>

        <div class="body-2">
            <div class="body-rest">

                <h3>Transaction Portal</h3>
            </div>
            <div class="body-main">
                <div class="body-header">

                    <h3>Inter Bank Transfer</h3>
                </div>
                <div class="main-content">
                
				<%@include file="/WEB-INF/includes/errorMsg.jsp"%>
					
					<%if(request.getAttribute("done")!=null) { %>
	                    <div class="success-div">
	                        <div class="content">
	                            <i class="fa-regular fa-face-smile"></i>
	                            <h3 style="color: #316952;">Your Transaction is Successful !!</h3>
	                        </div>
	                        
                      <form action="transaction" method="get">
	                        <div class="ok-button">
	                            <input class="button" type="submit" value="OK">
	                        </div>
	                        </form>
	                    </div>
	                    
	                <%} else{
	                	
	                	List<Integer> accNos = (List<Integer>)request.getAttribute("accNos");
	                	
					if(accNos.isEmpty()){%>
					
						<p class="no-history">You Can't do transaction ! <br> Create an UUB account from the nearby branch !</p>
					<%}else{
	                	
					String type = (String)request.getAttribute("type");
					
					switch(type){
					case "interbank":{
						
						%>
						
						<%@include file="/WEB-INF/includes/transaction/interBank.jsp"%>
						
						<%
						
						break;
					}					
					case "intrabank":{
						
						%>
						
						<%@include file="/WEB-INF/includes/transaction/intraBank.jsp"%>
						
						<%
						
						break;
					}					
					case "deposit":{
						
						%>
						
						<%@include file="/WEB-INF/includes/transaction/deposit.jsp"%>
						
						<%
						
						break;
					}								
					case "withdraw":{
						
						%>
						
						<%@include file="/WEB-INF/includes/transaction/withdraw.jsp"%>
						
						<%
						
						break;
					
					}
					
					} }}%>
					
					
                </div>
            </div>
        </div>

    </div>



</body>

</html>