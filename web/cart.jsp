<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="header.jsp"/>
<main>
    <section  id="cart">
        <h1>Your Cart : </h1><br>
        <h1>${ordermessage}</h1>
        <h1>${quantitymessage}</h1>
        <table>
            <tr>
                <th>Quantity</th>
                <th>Product Name</th>
                <th>Unit Price</th>
                <th>Discount</th>
                <th>Total</th>
                <th></th>
            </tr>
            <c:forEach var="item" items="${cart.items}">
                <tr>
                    <td>
                        <form action="shoppingcart" method="post">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="productID" 
                                   value="<c:out value='${item.product.productID}'/>">
                            <input type="text" name="quantity" 
                                   value="<c:out value='${item.quantity}'/>" id="quantity">
                            <input type="submit" value="Update">
                        </form>
                    </td>
                    <td><c:out value="${item.product.productName}" /></td>
                    <td>${item.product.formatProductPrice} đ</td>
                    <td>${item.promotionDiscount} %</td>
                    <td id="total">${item.formatLineItemAmount} đ</td>
                    <td>
                        <form action="shoppingcart" method="post">
                            <input type="hidden" name="action" value="remove">
                            <input type="hidden" name="productID" 
                                   value="<c:out value='${item.product.productID}'/>">
                            <input type="hidden" name="quantity" value="0">
                            <input type="submit" value="Remove">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table><br>

        <p><b>To change quantity</b> ,Enter quantity and click Update</p><br>
        <form action="shoppingcart" method="post">
            <input type="hidden" name="action" value="shop">
            <input type="submit" value="Continue Shopping">
        </form><br><br>
        <h3>Total Amount : ${orderTotal} VNĐ</h3><br>
        <c:if test="${isLogin == 'false'}">
            <p><b>Please register to checkout : </b></p><br>
            <form id="checkout" action="orderprocessing" method="post" onsubmit="return checkForm(this);">
                <label>Full Name: </label>
                <input type="text" name="customerName" required autofocus><span class="required">*</span><br>
                <label>Email: </label>
                <input type="email" name="customerEmail" required><span class="required">*</span><br>
                <label>Password: </label>
                <input type="password" name="customerPassword" required><span class="required">*</span><br>
                <label>Re-Type Password: </label>
                <input type="password" name="customerPassword2" required><span class="required">*</span><br>
                <label>Shipping Address: </label>
                <input type="text" name="customerAddress" required><span class="required">*</span><br>
                <label>Mobile Phone: </label>
                <input type="tel" name="customerPhone" required><span class="required">*</span><br>
                <label>Notes: </label><br>
                <textarea name="customerNote" rows="10" cols="30"></textarea><br>
                <input type="hidden" name="action" value="saveorder">
                <input type="submit" value="Register & Save your Cart">
            </form>
            <form id="checkout" action="orderprocessing" method="post" onsubmit="return checkForm(this);">
                <label>Full Name: </label>
                <input type="text" name="customerName" required autofocus><span class="required">*</span><br>
                <label>Email: </label>
                <input type="email" name="customerEmail" required><span class="required">*</span><br>
                <label>Password: </label>
                <input type="password" name="customerPassword" required><span class="required">*</span><br>
                <label>Re-Type Password: </label>
                <input type="password" name="customerPassword2" required><span class="required">*</span><br>
                <label>Shipping Address: </label>
                <input type="text" name="customerAddress" required><span class="required">*</span><br>
                <label>Mobile Phone: </label>
                <input type="tel" name="customerPhone" required><span class="required">*</span><br>
                <label>CreditCard Type: </label>
                <select name="cctype" id="creditcardtype">                               
                    <option value="visa">Visa</option>
                    <option value="master">Master Card</option>
                    <option value="americanexpress">American Express</option>
                </select><span class="required">*</span><br>
                <label>CreditCard Numbers: </label>
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
                <label>Notes: </label><br>
                <textarea name="customerNote" rows="10" cols="30"></textarea><br>
                <input type="hidden" name="action" value="pay">
                <input type="submit" value="Register & Pay Online">
            </form>
        </c:if>
        <c:if test="${isLogin == 'true'}">
            <form action="orderprocessing" method="post">               
                <input type="hidden" name="action" value="saveorder">
                <input type="submit" value="Add Order & Pay later">
            </form><br>
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

                <input type="hidden" name="action" value="pay">
                <input type="submit" value="Pay Online">
            </form>

        </c:if>
    </section>
    <script type="text/javascript">

        function checkForm(form)
        {
            

            if (form.customerPassword.value !== "" && form.customerPassword.value === form.customerPassword2.value) {
                if (form.customerPassword.value.length < 6) {
                    alert("Error: Password must contain at least six characters!");
                    form.customerPassword.focus();
                    return false;
                }
                if (form.customerPassword.value === form.customerName.value) {
                    alert("Error: Password must be different from Username!");
                    form.customerPassword.focus();
                    return false;
                }               
                
            } else {
                alert("Error: Please check that you've entered and confirmed your password!");
                form.customerPassword.focus();
                return false;
            }

            
            return true;
        }

    </script>
</main>
<c:import url="footer.jsp"/>