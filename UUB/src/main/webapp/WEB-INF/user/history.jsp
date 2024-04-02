<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="uub.staticlayer.HelperUtils"%>
<%@ page import="uub.staticlayer.DateUtils"%>
<%@ page import="uub.model.Transaction"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
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
                <div class="body-header">

                    <h3>Transaction History</h3>
                </div>
                <div class="main-content">
                
                <%
             
                if(request.getParameter("accNo")==null){ %>
					<form class="radio" action="history" method="get">
					
					 	<label style="color:black" for="accNo">Select Account Number:</label>
                                   
						<select style="width: 30%;" id="accNo" name="accNo">
                                    
                           	<c:forEach var="accNo" items="${accNos}">
								<option value="${accNo}">${accNo}</option>
							</c:forEach>
                               
                                 
                         </select>
					 	 <button type="submit">Submit</button>
					 	 
					</form>
					
					

<%}else{ %>
                
                
                    <div class="task-bar">
                        <form action="history" method="post">
                            <label for="startDate">From:</label>
                            <input type="date" id="startDate" name="startDate" value="${param.startDate}" required>
                            <label for="endDate">To:</label>
                            <input type="date" id="endDate" name="endDate" value="${param.endDate}" required>
                            <input  type="hidden" name="pageNo" value="1">
                            
                            <input  type="hidden" name="accNo" value="<%=request.getParameter("accNo")%>">
                            <button type="submit">Submit</button>

                        </form>
                    </div>
                   
			<% }
				Object list = request.getAttribute("transactions");
				if(list!=null){
				
				List<Transaction> transactions = (ArrayList<Transaction>)list;
					%>
                  <div class="history-tab">
                        <table class="history-table">

                            <thead>
                                <td>Transaction ID</td>
                                <td>Time</td>
                                <td>Type</td>
                                <td>Transaction Account No.</td>
                                <td>Description</td>
                                <td>Amount</td>
                                <td>Closing Balance</td>
                            </thead>
                            
                           <%for(Transaction transaction : transactions) {%>
                            <tr>
	                            <td><%=transaction.getId() %></td>
	                            <td><%=DateUtils.formatTime(transaction.getTime()) %></td>
	                            <td><%=transaction.getType().toString() %></td>
	                            <td><%=transaction.getTransactionAcc() %></td>
	                            <td><%=transaction.getDesc() %></td>
	                            
	                            <%
	                            double amount = transaction.getAmount();
	                            if(amount <0 ) {%>
	                            
	                            <td class="status INACTIVE" ><%=amount %></td>
	                            
	                            <%}else{ %>
	                            
	                            <td class="status ACTIVE" ><%= "+"+amount %></td>
	                            
	                            <%} %>
	                            
	                            <td><%=transaction.getClosingBal()%></td>

                        </tr>
                     
						<%} %>

                        </table>
						
						
					  </div>	
                           
                         <form action="history" method="post">
                         
                          	<input type="hidden" id="startDate" name="startDate" value="${param.startDate}" >
                            <input type="hidden" id="endDate" name="endDate" value="${param.endDate}" >
                            <input  type="hidden" name="accNo" value="<%=request.getParameter("accNo")%>">
                            
                        <%
                        int pageNo = (int) request.getAttribute("pageNo");
                        %>
                            <div class="pagination">
                            <%if(pageNo>1){ %>
                            <div class="prev-nxt">
                            <button class="page-" name="pageNo" value = "<%=pageNo - 1 %>"><<</button>
                            </div>
                            <%} %>
                            
                        <div class="page-numbers">
                        
                       <% 
                       int pageCount = (int)request.getAttribute("pageCount");
                       for(int i=1; i<=pageCount; i++){ %>
                            
                            <input class="page-<%= i==pageNo?"selected":"" %>" type="submit" name="pageNo" value="<%=i%>">
                            
                          
                         <%}%>
                           </div>
                           
                           
                            <%if(pageNo<pageCount){ %>
                           <div class="prev-nxt">
                           <button class="page+" name="pageNo" value = "<%=pageNo + 1 %>">>></button>
                            </div>
                            <%} %>
                           </div>
                        </form>
                     
                         
<%} %>

                 
                </div>
            </div>
        </div>

    </div>



</body>

</html>


<%-- 
                        	<label for="accountNo">Select Account No. </label>
                        	<select id="accountNo" name="accountNo">
							<c:forEach var="accNo" items="${accNos}">
								<option value="${accNo}">${accNo}</option>
							</c:forEach>
							</select> --%>