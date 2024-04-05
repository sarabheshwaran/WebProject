
<%

String option = (String)pageContext.getAttribute("page");


if(option.equals("accounts")){

%>


            <nav class="vertical-nav">
                <div class="quick">
                    <h3>Quick Links</h3><i class="fa-solid fa-chevron-right"></i>
                </div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/user/account">All Accounts</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/user/history">See History</a></div>
            </nav>

<%
}
else if (option.equals("transaction")){
%>

            <nav class="vertical-nav">
                <div class="quick">
                    <h3>Quick Links</h3><i class="fa-solid fa-chevron-right"></i>
                </div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/user/transaction?type=interbank">Inter Bank Transfer</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/user/transaction?type=intrabank">Intra Bank Transfer</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/user/transaction?type=withdraw">Withdraw</a></div>
                <div class="links"><a href="<%=request.getContextPath()%>/app/user/transaction?type=deposit">Deposit</a></div>
            </nav>
        

<%}%>