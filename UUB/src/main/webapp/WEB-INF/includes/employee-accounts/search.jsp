					
					
					<a href="<%=request.getContextPath()%>/app/employee/accounts?search=customer">Search by Customer</a>
					
					<a href="<%=request.getContextPath()%>/app/employee/accounts?search=account">Search by Account No.</a>
					
					
					<% 
					String search = request.getParameter("search");
					%>
					
					
					<%
					if(search!=null){ 
					
					
					if(search.equals("customer")){
					
					%>
					
					<div class="container">
				        <h2>Search by Customer ID</h2>
				        <form action="accountSearch" method="POST">
				            <div class="form-group">
				            	<input type="hidden" name="branchId" value="${profile.branchId}">
				                <label for="customerId">Customer ID:</label>
				                <input type="number" id="customerId" name="customerId" placeholder="Enter Customer ID">
				            </div>
				            <input type="submit" value="Search">
				        </form>
				    </div>
				    <%}else if(search.equals("account")){ %>
				    <div class="container">
				        <h2>Search by Account No.</h2>
				        <form action="accountSearch" method="POST">
				            <div class="form-group">
				            	<input type="hidden" name="branchId" value="${profile.branchId}">
				                <label for="accNo">Account No:</label>
				                <input type="number" id="accNo" name="accNo" placeholder="Enter the Account No.">
				            </div>
				            <input type="submit" value="Search">
				        </form>
				    </div>
<%}}%>