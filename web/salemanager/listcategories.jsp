<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="/header_management.jsp"/>
<c:import url="left_aside_manager.jsp"/>

<section class="management_page">
    <h1>Categories List</h1>
    <section id="search_form" title="">
        <form action="productmanager" method="post">
            <input type="hidden" name="action" value="addcategory">
            <input type="text" name="category" placeholder="Enter category here" required>
            <input type="submit" value="Add Category">
        </form>
    </section>
    <table>
        <tr>
            <th>Category ID</th>
            <th>Category Name</th>
            
        </tr>
        <c:forEach var="cat" items="${categoryList}">
            <tr>
                <td>${cat.categoryID}</td>
                <td>${cat.categoryName}</td>
                
            </tr>
        </c:forEach>
    </table>
</section>

<c:import url="/footer.jsp"/>