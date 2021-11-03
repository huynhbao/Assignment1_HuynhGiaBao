<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="css/header.css" rel="stylesheet">
        <style>

        </style>
    </head>
    <body>
        <header class="header" id="header">
            <nav class="navbar navbar-expand-lg navbar-light">
                <h2><a exact="true" class="logo" href="home">HANA SHOP</a></h2>

                <c:if test="${sessionScope.LOGIN_USERDTO != null}">
                    <c:if test="${sessionScope.LOGIN_USERDTO.roleID == 'AD'}">
                        <a class="navbar-brand mr-3" href="MainController?btnAction=AdminManageProducts">Admin Management</a>
                    </c:if>
                </c:if>



                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                    </li>
                </ul>
                <c:choose>
                    <c:when test="${sessionScope.LOGIN_USERDTO == null}">
                        <a class="navbar-brand" href="MainController?btnAction=Login">Login</a>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${sessionScope.LOGIN_USERDTO.roleID == 'US'}">
                            <div class="cart">
                                <a href="MainController?btnAction=Cart">
                                    <c:set var="totalProduct" value="0"/>
                                    <c:if test="${sessionScope.CART != null}">
                                        <c:set var="totalProduct" value="${sessionScope.CART.getCart().values().size()}"/>
                                    </c:if>
                                    <span class="nb">${totalProduct}</span>
                                    <img src="images/cart.svg" alt="cart">
                                </a>
                            </div>
                        </c:if>
                        <ul class="navbar-nav">
                            <li class="nav-item dropdown">
                                <a class="nav-link" href="#" id="navbarDropdown" style=" color: #7d99ff; " data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Welcome, ${sessionScope.LOGIN_USERDTO.name}
                                </a>
                                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                                    <c:if test="${sessionScope.LOGIN_USERDTO.roleID == 'US'}">
                                        <a class="dropdown-item" href="MainController?btnAction=UserHistory">Order History</a>
                                    </c:if>
                                    <a class="dropdown-item" href="MainController?btnAction=Logout">Logout</a>
                                </div>
                            </li>
                        </ul>
                    </c:otherwise>
                </c:choose>

            </nav>
        </header>
        <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    </body>
</html>