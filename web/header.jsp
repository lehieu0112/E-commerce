<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Cart</title>
        
        <link rel="stylesheet" type="text/css" href="/Project1/styles/main.css">
        <link href='https://fonts.googleapis.com/css?family=Noto+Serif&subset=latin,vietnamese' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Patrick+Hand&subset=latin,vietnamese' rel='stylesheet' type='text/css'>
        <script src="/Project1/js/js-image-slider.js" type="text/javascript"></script>
        <style>
            table {
                border-collapse: collapse;
                width: 100%;
            }

            th, td {
                text-align: left;
                padding: 8px;
            }

            tr:nth-child(even){background-color: #f2f2f2}

            th {
                background-color: #666666;
                color: white;
            }
        </style>
        <script type="text/javascript">
            function switchAutoAdvance() {
                imageSlider.switchAuto();
                var auto = document.getElementById('auto');
                var isAutoPlay = imageSlider.getAuto();
            }
        </script>
    </head>
    <body>

        <header> 
            <a id="header_a" href="#"><img src="http://cliparts.co/cliparts/5iR/K9n/5iRK9n85T.png"/>
                <h1 style="font-family: 'Patrick Hand', cursive;color: #666666">Computer & Mobile Shop</h1>      
            </a>
            <nav id="top_menu">
                <ul>
                    <li><a href="homepage"><strong>Home</strong></a></li>
                    <li><a href="productsList?action=search&category=Laptop"><strong>Laptop</strong></a></li>
                    <li><a href="productsList?action=search&category=Tablet"><strong>Tablet</strong></a></li>
                    <li><a href="productsList?action=search&category=Mobile"><strong>Mobile</strong></a></li>
                    <li><a href="productsList?action=search&category=Desktop"><strong>Desktop</strong></a></li>
                    <li><a href="#"><strong>Network</strong></a></li>
                    <li><a href="#"><strong>Office</strong></a></li>  
                    <li id="searchform">
                        <form action="productsList" method="get">
                            <input type="hidden" name="action" value="search">
                            <input style="width: 15em;padding: 1px;border: 1px solid black;border-radius: 6px;font-size: medium;" type="text" name="name">
                            <input style="font-size: medium;padding: 1px;border: 1px solid black;border-radius: 8px;width: 7em;" type="submit" name="submit" value="Search">
                        </form>
                        
                    </li>
                </ul>
            </nav>
            <section id="header_information">
                <aside id="header_promotion">
                    <div id="sliderFrame">
                        <div id="slider">
                            <c:forEach var="promotion" items="${promotionsList}">
                                <c:if test="${promotion.pictureLink != ''}">
                                    <img src="<c:url value="${promotion.pictureLink}" />"/>
                                </c:if>
                                
                            </c:forEach>
<!--                            <img src="images/1.jpg" />
                            <img src="images/2.jpg" />
                            <img src="images/3.png" />
                            <img src="images/4.jpg" />
                            <img src="images/5.jpg" />
                            <img src="images/6.jpg" />
                            <img src="images/7.jpg" />
                            <img src="images/8.jpg" />-->
                        </div>
                        <div class="group1-Wrapper">
                            <a onclick="imageSlider.previous()" class="group1-Prev"></a>
                            <a onclick="imageSlider.next()" class="group1-Next"></a>
                        </div>
                    </div>

                </aside>
                <aside id="header_main">
                    <aside id="header_main_choose">
                        <form action="productsList" method="get">
                            <input type="hidden" name="action" value="search">
                            <label>Manufacture: </label>
                            <select name="manufacture" id="choose_manufacture">
                                <c:forEach var="manufacture" items="${manufactureList}">
                                    <option value="${manufacture.manufactureName}">${manufacture.manufactureName}</option>
                                </c:forEach>

                            </select><br>

                            <label>Category: </label>
                            <select name="category" id="choose_category">
                                <c:forEach var="category" items="${categoryList}">
                                    <option value="${category.categoryName}">${category.categoryName}</option>
                                </c:forEach>

                            </select><br>
                            <input type="submit" value="View" id="viewButton">
                        </form>
                    </aside>
                    <aside id="header_main_login">
                        <c:if test="${isLogin == 'false'}">
                            <form action="customer" method="post">
                                <input type="hidden" name="action" value="login">
                                <label>Email:</label>
                                <input type="email" name="email" required autofocus id="input_email"><br>
                                <label>Password: </label>
                                <input type="password" name="password" required id="input_password"><br>
                                <input type="checkbox" name="rememberme" value="rememberme"> Remember me 
                                <input type="submit" value="Login" id="loginButton">                       
                                <a href="customer?action=registerpage">Register</a><br>
                                <span id="loginmessage">
                                    <p>${loginmessage} </p>
                                    <a href="customer?action=forgotpassword">${forgotpassword}</a>
                                </span>
                            </form>
                        </c:if>
                        <c:if test="${isLogin == 'true'}">
                            <h3>Welcome ${customer.customerName}</h3>
                            <form class="login_form" action="customer" method="post">
                                <input type="hidden" name="action" value="editprofile">
                                <input type="hidden" name="customerID" 
                                       value="<c:out value='${customer.customerID}'/>">                                
                                <input type="submit" value="Edit Your Profile" id="editButton">
                            </form>
                            <form class="login_form" action="customer" method="post">
                                <input type="hidden" name="action" value="editorders">
                                <input type="hidden" name="customerID" 
                                       value="<c:out value='${customer.customerID}'/>">                                
                                <input type="submit" value="View Your Orders" id="editButton">
                            </form>
                            <form class="login_form" action="customer" method="post">
                                <input type="hidden" name="action" value="showcart">
                                <input type="submit" value="View Your Cart" id="showButton">
                            </form>
                            <form class="login_form" action="customer" method="post">
                                <input type="hidden" name="action" value="logout">
                                <input type="submit" value="Log Out" id="logoutButton">
                            </form>
                        </c:if>
                    </aside>
                </aside>
            </section>
        </header>
