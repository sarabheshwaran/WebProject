    <%Object errorMessage = request.getAttribute("error");
    	
    if(errorMessage != null){
    %>
    
            <div class="error-block">
            <i class="fa-solid fa-triangle-exclamation"></i>
            <p><%=errorMessage%></p>
        </div>
        <%}%>