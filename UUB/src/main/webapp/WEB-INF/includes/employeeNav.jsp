    <nav class="horizontal-nav">
        <div class="options">
            <div class="option ${page eq 'profile' ? 'selected' : ''}">
                <a href="profile.html">
                    <div class="icon">
                        <i class="fa-solid fa-user"></i>
                    </div>Profile

                </a>
            </div>
            
            <div class="option ${page eq 'accounts' ? 'selected' : ''}">

                <a href="accounts">
                    <div class="icon">
                        <i class="fa-solid fa-list-check"></i>
                    </div>
                    Manage Accounts
                </a>
            </div>
            
            <div class="option ${page eq 'customers' ? 'selected' : ''}">

                <a href="customers">
                    <div class="icon">
                        <i class="fa-solid fa-user-pen"></i>
                    </div>Manage Customers
                </a>
            </div>

        </div>

    </nav>