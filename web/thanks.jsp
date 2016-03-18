<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<main style="background-color: white;height: 200px;margin-left: 10%;margin-right: 10%;padding: 20px;">
    <h1>${ordermessage}</h1>
    <h1>${paymessage}</h1>
    <h3>${registermessage}</h3>
    <h3>${emailmessage}</h3>
</main>
 <c:import url="footer.jsp"/>