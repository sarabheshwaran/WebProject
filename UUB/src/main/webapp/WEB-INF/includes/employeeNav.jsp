    <nav class="horizontal-nav">
        <div class="options">
            <div class="option ${page eq 'profile' ? 'selected' : ''}">
                <a href="profile">
                    <div class="icon">
                        <i class="fa-solid fa-user"></i>
                    </div>Profile

                </a>
            </div>
            
            <div class="option ${page eq 'accounts' ? 'selected' : ''}">

                <a href="manageAccounts?action=search">
                    <div class="icon">
                        <i class="fa-solid fa-list-check"></i>
                    </div>
                    Manage Accounts
                </a>
            </div>
            
            <div class="option ${page eq 'customers' ? 'selected' : ''}">

                <a href="manageCustomers?action=search">
                    <div class="icon">
                        <i class="fa-solid fa-user-pen"></i>
                    </div>Manage Customers
                </a>
            </div>
            
				<%int access = (int) request.getAttribute("access");
				
				if(access==1){%>

 					<div class="option ${page eq 'employees' ? 'selected' : ''}">

		                <a href="manageEmployees?action=search">
		                    <div class="icon">
		                       <i class="fa-solid fa-users"></i>
		                    </div>Manage Employees
		                </a>
            		</div>
				<%} %>
            
            

        </div>

    </nav>