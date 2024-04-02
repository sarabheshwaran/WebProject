    
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <div class="menu-bar">
        <div class="name-card">
        
        
        
        
            <h4>Welcome</h4>
            
            
            
            <h3> ${profile.gender eq 'male' ? 'Mr.' : 'Ms.'} ${fn:toUpperCase(profile.name)}</h3>
        </div>
    </div>