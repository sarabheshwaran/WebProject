<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="../mainStyle.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">


</head>


<body>
    <header>
        <div class="main-header"><img class="logo" src="../logo.png">
            <h1 class="title-logo">UUB </h1>
        </div>
        <div class="logout">

            <a href="../index.html">
                <div class="icon">
                    <i class="fa-solid fa-power-off"></i>
                </div>LOGOUT
            </a>
        </div>
    </header>
    <nav class="horizontal-nav">
        <div class="options">
            <div class="option">
                <a href="profile.html">
                    <div class="icon">
                        <i class="fa-solid fa-user"></i>
                    </div>Profile

                </a>
            </div>
            <div class="option selected">

                <a href="accounts.html">
                    <div class="icon">
                        <i class="fa-solid fa-list-check"></i>
                    </div>
                    Manage Accounts
                </a>
            </div>
            <div class="option">

                <a href="addCustomers.html">
                    <div class="icon">
                        <i class="fa-solid fa-user-pen"></i>
                    </div>Manage Customers
                </a>
            </div>

        </div>

    </nav>

    <div class="menu-bar">
        <div class="name-card">
            <h4>Welcome</h4>
            <h3>Mr JHON DOE</h3>
        </div>
    </div>
    <div class="body">

        <div class="body-1">
            <nav class="vertical-nav">
                <div class="quick">
                    <h3>Quick Links</h3><i class="fa-solid fa-chevron-right"></i>
                </div>
                <div class="links"><a href="accounts.html">See All Accounts</a></div>
                <div class="links"><a href="createAccount.html">Create Account</a></div>
            </nav>
        </div>
        <div class="body-2">

            <div class="body-main">
                <div class="body-header">

                    <h3>Branch accounts</h3>
                </div>
                <div class="main-content">
                
                <div class="task-bar">
                    <form action="history.html">
                        <label for="branchId">Branch ID:</label>
                        <input type="number" id="branchId" name="branchId">
                        <button type="submit">Search</button>

                    </form>
                </div>
                <div class="history-tab">
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="../mainStyle.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">


</head>


<body>

<%@include file="/WEB-INF/includes/mainheader.jsp"%>
<% pageContext.setAttribute("page", "accounts"); %>
    
<%@include file="/WEB-INF/includes/employeeNav.jsp"%>

<%@include file="/WEB-INF/includes/nameBar.jsp"%>


    <div class="body">

        </div>
        <div class="body-2">

            <div class="body-main">
                <div class="body-header">

                    <h3>Branch accounts</h3>
                </div>
                <div class="main-content">
                
                <div class="task-bar">
                    <form action="history.html">
                        <label for="branchId">Branch ID:</label>
                        <input type="number" id="branchId" name="branchId">
                        <button type="submit">Search</button>

                    </form>
                </div>
                <div class="history-tab">

                    <table class="history-table">

                        <thead>
                            <td>Account No.</td>
                            <td>Account Type</td>
                            <td>Account Status</td>
                            <td>Balance</td>
                            <td>Branch ID:</td>
                            <td>Options</td>
                        </thead>
                    	 <c:forEach var="account" items="${accountMap.entrySet()}">
                            <tr>
	                            <td>${account.key}</td>
	                            <td>${account.value.type}</td>
	                            <td class="status ${account.value.status}">${account.value.status}</td>
	                            <td>${account.value.balance}</td>
	                            <td>${account.value.branchId}</td>
	                            <td>
	                                <div style="width: 100%;display: flex;justify-content: center;">
	
	                                    <form action="account?=${account.key}" method="get">
	                                        <input class="button" type="submit" value="See Account">
	                                    </form>
	                                    
	                                </div>
	                            </td>
                        </tr>
                     
						</c:forEach>


                    </table>



                    <div class="page-numbers">
                        <a href="">prev</a>
                        <a href="">1</a>
                        <a href="">2</a>
                        <a href="">3</a>
                        <a href="">next</a>
                    </div>

                </div>
                </div>
            </div>
        </div>
    </div>



</body>

</html>


                    <div class="page-numbers">
                        <a href="">prev</a>
                        <a href="">1</a>
                        <a href="">2</a>
                        <a href="">3</a>
                        <a href="">next</a>
                    </div>

                </div>
                </div>
            </div>
        </div>
    </div>



</body>

</html>