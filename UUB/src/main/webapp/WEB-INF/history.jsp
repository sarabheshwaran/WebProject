<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="uub.staticlayer.HelperUtils"%>
<%@ page import="uub.staticlayer.DateUtils"%>
<%@ page import="uub.model.Transaction"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                <div class="body-header">

                    <h3>Transaction History</h3>
                </div>
                <div class="main-content">
                
<%--                 <%
             
                if(type.equals("customer") && request.getParameter("accNo")==null){ %>
					<form class="radio" action="history" method="get">
					
					 	<label style="color:black" for="accNo">Select Account Number:</label>
                                   
						<select style="width: 30%;" id="accNo" name="accNo">
                                    
                           	<c:forEach var="accNo" items="${accNos}">
								<option value="${accNo}">${accNo}</option>
							</c:forEach>
                               
                                 
                         </select>
					 	 <button type="submit">Submit</button>
					 	 
					</form>
					
					

<% %> --%>
                
                
                    <div class="task-bar">
                        <form action="history" method="post">
                        
                        <%if(type.equals("employee")){ %>
                         <label for="accNo">Acc No:</label>
                            <input type="number" id="accNo" name="accNo" value="${param.accNo}" required>
                            
                        
                        <%} %>
                             <%if(type.equals("customer")){ %>
                            <%List<Integer> accNos = (List<Integer>)request.getAttribute("accNos");
                            
                            int accNo = 0;
                            if(request.getParameter("accNo")!=null){
                            
                            	accNo = Integer.parseInt(request.getParameter("accNo"));
                            }

                            %>
                         <label for="accNo">Acc No:</label>
		                    <select id="accNo" name="accNo" required>
                            <%for(int a : accNos)
                            {%>
                            <option value="<%= a %>" <%=a==accNo ? "selected" : "" %>><%=a%></option>
							<%} %>
							</select>
                            <%} %>
                        
                            <label for="startDate">From:</label>
                            <input type="date" id="startDate" name="startDate" value="${param.startDate}" required>
                            <label for="endDate">To:</label>
                            <input type="date" id="endDate" name="endDate" value="${param.endDate}" required>
                            <button type="submit">Submit</button>

                        </form>
                    </div>
                   
			<% 
				Object list = request.getAttribute("transactions");
				if(list!=null){
				
				List<Transaction> transactions = (ArrayList<Transaction>)list;
				
				if(transactions.isEmpty()){%>
					
					
					<p class="no-history">No Transactions to show...</p>
					
				<%}else{
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
	                            <td><%=transaction.getTransactionAcc() == 0 ? "-" : transaction.getTransactionAcc() %></td>
	                            <td><%=transaction.getDesc() == null ? "-" : transaction.getDesc()%></td>
	                            
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
                     
                         
<%}} %>

                 
                </div>
            </div>
        </div>

    </div>

  <script>
    let today = new Date().toISOString().split('T')[0];
    
    document.getElementById("startDate").value = today;
    document.getElementById("endDate").value = today;
  </script>

</body>

</html>


<%-- 
                        	<label for="accountNo">Select Account No. </label>
                        	<select id="accountNo" name="accountNo">
							<c:forEach var="accNo" items="${accNos}">
								<option value="${accNo}">${accNo}</option>
							</c:forEach>
							</select> --%>