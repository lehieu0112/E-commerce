<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<main>
    <section  id="cart">
        
            <p><b>Enter information to checkout : </b></p><br>
            <form id="checkout" action="orderprocessing" method="post">
                <label>Full Name: </label>
                <input type="text" name="ccName" required autofocus><span class="required">*</span><br>      
                <label>Shipping Address: </label>
                <input type="text" name="ccAddress" required><span class="required">*</span><br>
                <label>Mobile Phone: </label>
                <input type="tel" name="ccPhone" required><span class="required">*</span><br>
                <label>Credit Card Type: </label>
                <select name="cctype" id="creditcardtype">                               
                    <option value="visa">Visa</option>
                    <option value="master">Master Card</option>
                    <option value="americanexpress">American Express</option>
                </select><span class="required">*</span><br>
                <label>Credit Card Numbers: </label>
                <input type="text" name="ccnumbers" required autofocus><span class="required">*</span><br>
                <label>Expiration date: </label>
                <select name="expiremonth" id="creditcard">
                    <c:forEach var="i" begin="1" end="12">
                        <option value="${i}">${i}</option>
                    </c:forEach>                               
                </select><span class="required">*</span>
                <select name="expireyear" id="creditcard">                               
                    <c:forEach var="i" begin="2016" end="2020">
                        <option value="${i}">${i}</option>
                    </c:forEach>                
                </select><span class="required">*</span><br>
                <label>CVV numbers: </label>
                <input type="text" name="cvvnumbers" required><span class="required">*</span><br>

                <input type="hidden" name="action" value="paylater">
                <input type="hidden" name="invoiceID" value="<c:out value='${invoiceID}'/>">
                <input type="submit" value="Pay Online">
            </form>

    </section>
</main>
<c:import url="footer.jsp"/>