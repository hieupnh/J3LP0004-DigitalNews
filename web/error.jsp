<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" type="text/css" href="./public/css/style.css" />
        <title>Error</title>
    </head>
    <body>
        <div>
            <div class="font-sans text-blue">
                <!--Header-->
                <%@include file="/components/header.jsp" %>
                <!--Body-->
                <div class="flex container">
                    <div class="left pa-5">
                        <p class="error">${error}</p>
                    </div>
                </div>
                <!--Footer-->
                <footer class="footer"></footer>
            </div>
        </div>
    </body>
</html>
