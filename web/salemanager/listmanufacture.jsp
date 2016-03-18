<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_manager.jsp"/>

<section class="management_page">
    <h1>Categories List</h1>
    <section id="search_form" title="">
        <form action="productmanager" method="post">
            <input type="hidden" name="action" value="addmanufacture">
            <input type="text" name="manufacture" placeholder="Enter manufacture here" required>
            <input type="submit" value="Add Manufacture">
        </form>
    </section>
    <table>
        <tr>
            <th>Manufacture ID</th>
            <th>Manufacture Name</th>
            
        </tr>
        <c:forEach var="manu" items="${manufactureList}">
            <tr>
                <td>${manu.manufactureID}</td>
                <td>${manu.manufactureName}</td>
                
            </tr>
        </c:forEach>
    </table>
</section>

<c:import url="/footer.jsp"/>