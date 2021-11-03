<%-- 
    Document   : checkout
    Created on : Jan 10, 2021, 2:11:13 PM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="container" style="margin-top: 140px;">
            <c:set var="paypalPayment" value="${false}"/>
            <c:if test="${requestScope.TRANSACTION != null}">
                <c:set var="paypalPayment" value="${true}"/>
            </c:if>
            <div class="row">
                <div class="col-md-4 order-md-2 mb-4">
                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <span class="text-muted">Your cart</span>
                        <span class="badge badge-secondary badge-pill">${sessionScope.CART.getCart().size()}</span>
                    </h4>
                    <ul class="list-group mb-3 sticky-top">
                        <c:set var="total" value="0"/>
                        <c:choose>
                            <c:when test="${paypalPayment}">
                                <c:forEach var="cart" items="${requestScope.TRANSACTION.itemList.items}" varStatus="counter">
                                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                                        <div>
                                            <h6 class="my-0">${cart.name}</h6>
                                            <small class="text-muted">${cart.description}</small>
                                        </div>
                                        <span class="text-muted"><fmt:formatNumber value = "${cart.price}" type = "currency"/></span>
                                    </li>
                                    <c:set var="total" value="${total + (cart.price * cart.quantity)}"/>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="cart" items="${sessionScope.CART.getCart().values()}" varStatus="counter">
                                    <li class="list-group-item d-flex justify-content-between lh-condensed">
                                        <div>
                                            <h6 class="my-0">${cart.name}</h6>
                                            <small class="text-muted">${cart.description}</small>
                                        </div>
                                        <span class="text-muted"><fmt:formatNumber value = "${cart.price}" type = "currency"/></span>
                                    </li>
                                    <c:set var="total" value="${total + (cart.price * cart.quantity)}"/>
                                </c:forEach>
                                <c:set var="CART" value="${null}"/>
                            </c:otherwise>
                        </c:choose>

                        <li class="list-group-item d-flex justify-content-between">
                            <span>Total</span>
                            <strong><fmt:formatNumber value = "${total}" type = "currency"/></strong>
                        </li>
                    </ul>
                </div>
                <div class="col-md-8 order-md-1">
                    <h4 class="mb-3">Billing address</h4>
                    <form class="needs-validation" novalidate="">
                        <div class="mb-3">
                            <label for="firstName">Your name</label>
                            <input type="text" class="form-control" id="firstName" placeholder="" value="${sessionScope.LOGIN_USERDTO.name}" readonly="true">
                        </div>
                        <div class="mb-3">
                            <label for="email">Phone number</label>
                            <input type="email" class="form-control" id="email" value="${sessionScope.LOGIN_USERDTO.phone}" readonly="true">
                        </div>
                        <div class="mb-3">
                            <label for="email">Email</label>
                            <input type="email" class="form-control" id="email" value="${sessionScope.LOGIN_USERDTO.email}" readonly="true">
                        </div>
                        <div class="mb-3">
                            <label for="address">Address</label>
                            <input type="text" class="form-control" id="address" value="${sessionScope.LOGIN_USERDTO.address}" readonly="true">
                        </div>

                        <hr class="mb-4">
                        <h4 class="mb-3">Payment</h4>
                        <div class="d-block my-3">
                            <c:choose>
                                <c:when test="${paypalPayment}">
                                    <label>Paypal</label>
                                </c:when>
                                <c:otherwise>
                                    <label>Cash payment upon delivery</label>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <hr class="mb-4">
                        <a href="MainController?btnAction=Shopping" class="btn btn-primary btn-lg btn-block">Continue to shopping</a>
                    </form>
                </div>
            </div>
            <!--            <footer class="my-5 pt-5 text-muted text-center text-small">
                            <p class="mb-1">© 2017-2019 Company Name</p>
                            <ul class="list-inline">
                                <li class="list-inline-item"><a href="#">Privacy</a></li>
                                <li class="list-inline-item"><a href="#">Terms</a></li>
                                <li class="list-inline-item"><a href="#">Support</a></li>
                            </ul>
                        </footer>-->
        </div>
    </body>
</html>
