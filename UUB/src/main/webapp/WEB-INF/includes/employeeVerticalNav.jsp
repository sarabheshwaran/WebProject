
<%

String option = (String)pageContext.getAttribute("page");


if(option.equals("accounts")){

%>


            <nav class="vertical-nav">
                <div class="quick">
                    <h3>Quick Links</h3><i class="fa-solid fa-chevron-right"></i>
                </div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/manageAccounts?action=search">Search Accounts</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/manageAccounts?action=create">Add Account</a></div>
          		<div class="links"><a href="<%=request.getContextPath()%>/app/employee/history">Transaction History</a></div>
            </nav>

<%
}
else if (option.equals("customers")){
%>

            <nav class="vertical-nav">
                <div class="quick">
                    <h3>Quick Links</h3><i class="fa-solid fa-chevron-right"></i>
                </div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/manageCustomers?action=search">Search Customers</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/manageCustomers?action=create">Create Customer</a></div>
                </nav>
        

<%}

else if(option.equals("employees")){
%>
 				<nav class="vertical-nav">
                <div class="quick">
                    <h3>Quick Links</h3><i class="fa-solid fa-chevron-right"></i>
                </div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/manageEmployees?action=search">Search Employee</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/manageEmployees?action=create">Create Employee</a></div>
                </nav>
        

<%}%>