<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sample Form</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/indexStyle.css">
     <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
</head>


<body>
<%@include file="/WEB-INF/includes/indexheader.jsp"%>

    <div class="body-header">
        <h2>Login</h2>
    </div>


    <div class="body-rest">
    

<%@include file="/WEB-INF/includes/errorMsg.jsp"%>


        <div class="form-container">

            <form action="login" method="post">

                <div class="input">
                    <label for="userId">User ID:</label>
                    <input type="text" id="userId" name="userId"><br><br>
                </div>

                <div class="input">
                    <label for="password">Password :</label>
                    <input type="password" id="password" name="password"><br><br>
                </div>
                
                    <input class ="button" type="submit" value="Submit">
            </form>
        </div>
    </div>

</body>

</html>