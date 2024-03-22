
<%

String option = (String)pageContext.getAttribute("page");


if(option.equals("accounts")){

%>


            <nav class="vertical-nav">
                <div class="quick">
                    <h3>Quick Links</h3><i class="fa-solid fa-chevron-right"></i>
                </div>
                <div class="links"><a href="<%=request.getContextPath()%>>/app/user/accounts">All Accounts</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>>/app/userbalance">Balance enquiry</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>>/app/userhistory">See History</a></div>
            </nav>

<%
}
%>