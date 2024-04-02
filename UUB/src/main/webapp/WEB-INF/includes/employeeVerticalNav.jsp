
<%

String option = (String)pageContext.getAttribute("page");


if(option.equals("accounts")){

%>


            <nav class="vertical-nav">
                <div class="quick">
                    <h3>Quick Links</h3><i class="fa-solid fa-chevron-right"></i>
                </div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/accounts">All Accounts</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/accounts?add=true">Add Account</a></div>
            </nav>

<%
}
else if (option.equals("customers")){
%>

            <nav class="vertical-nav">
                <div class="quick">
                    <h3>Quick Links</h3><i class="fa-solid fa-chevron-right"></i>
                </div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/customers">All Customers</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/customer?add=true">Add Customer</a></div>
                </nav>
        

<%}

else if(option.equals("branches")){
%>
 <nav class="vertical-nav">
                <div class="quick">
                    <h3>Quick Links</h3><i class="fa-solid fa-chevron-right"></i>
                </div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/branches">All Branches</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/employee/branches?add=true">Add Branch</a></div>
                </nav>
        

<%}%>