<%-- 
    Document   : shopping-history
    Created on : Jan 10, 2021, 8:05:01 PM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping History Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <style>
            h2.title {
                font-weight: 700;
                font-size: 24px;
                color: #000;
                margin-bottom: 30px;
                text-transform: uppercase;
                letter-spacing: 3px;
            }
            
            .card-header {
                background-color: #7d9fffc7 !important;
            }

            .quantity {
                float: left;
                margin-right: 15px;
                background-color: #eee;
                position: relative;
                width: 80px;
                overflow: hidden
            }

            .quantity input {
                margin: 0;
                text-align: center;
                width: 15px;
                height: 15px;
                padding: 0;
                float: right;
                color: #000;
                font-size: 20px;
                border: 0;
                outline: 0;
                background-color: #F6F6F6
            }

            .quantity input.qty {
                position: relative;
                border: 0;
                width: 100%;
                height: 40px;
                padding: 10px 25px 10px 10px;
                text-align: center;
                font-weight: 400;
                font-size: 15px;
                border-radius: 0;
                background-clip: padding-box
            }

            .quantity .minus, .quantity .plus {
                line-height: 0;
                background-clip: padding-box;
                -webkit-border-radius: 0;
                -moz-border-radius: 0;
                border-radius: 0;
                -webkit-background-size: 6px 30px;
                -moz-background-size: 6px 30px;
                color: #bbb;
                font-size: 20px;
                position: absolute;
                height: 50%;
                border: 0;
                right: 0;
                padding: 0;
                width: 25px;
                z-index: 3
            }

            .quantity .minus:hover, .quantity .plus:hover {
                background-color: #dad8da
            }

            .quantity .minus {
                bottom: 0
            }
            .shopping-cart {
                margin-top: 20px;
            }

            .hide {
                display:none;
            }
        </style>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <script src="https://use.fontawesome.com/c560c025cf.js"></script>
        <div class="container" style="margin-top: 140px;">
            <div class="d-flex justify-content-between">
                <div>
                    <h2 class="title text-left">Shopping History</h2>
                </div>
                <div>
                    <button class="btn btn-outline-secondary" id="btn-search-panel" type="button">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </div>
            <c:set var="searching" value="${not empty param.txtSearchDateFrom or not empty param.txtSearchDateTo}" />
            <div class="search-container mb-5 ${searching == true ? '' : 'hide'}">
                <div class="bg-white p-3 rounded shadow">
                    <form action="MainController">
                        <div class="form-group mb-4">
                            <input class="form-control border-primary" type="date" name="txtSearchDateFrom" value="${param.txtSearchDateFrom}">
                        </div>
                        <div class="form-group mb-4">
                            <input class="form-control border-primary" type="date" name="txtSearchDateTo" value="${param.txtSearchDateTo}">
                        </div>
                        <div class="form-group mb-4 text-center">
                            <p class="text-danger">${requestScope.ERROR_SEARCH_DATE}</p>
                            <button type="submit" class="btn btn-primary btn-lg btn-radius" name="btnAction" value="Search History">Search</button>
                        </div>

                    </form>
                    <!-- End -->

                </div>
            </div>

            <c:if test="${requestScope.LIST != null}">
                <c:if test="${not empty requestScope.LIST}">


                    <c:forEach items="${requestScope.LIST}" var="order" varStatus="counter">
                        <div class="card shopping-cart">
                            <div class="card-header text-light">
                                <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                                Order No #${order.orderID}
                                <div class="clearfix"></div>
                            </div>
                            <div class="card-body">

                                <!-- PRODUCT -->
                                <c:forEach items="${order.orderDetail}" var="orderDetail" varStatus="counterOD">
                                    <div class="row">
                                        <div class="col-12 col-sm-12 col-md-2 text-center">
                                            <img class="img-responsive" src="${orderDetail.product.imgLink}" alt="prewiew" width="120" height="80">
                                        </div>
                                        <div class="col-12 text-sm-center col-sm-12 text-md-left col-md-6">
                                            <h4 class="product-name"><strong>${orderDetail.product.name}</strong></h4>
                                            <h4>
                                                <small>${orderDetail.product.description}</small>
                                            </h4>
                                        </div>
                                        <div class="col-12 col-sm-12 text-sm-center col-md-4 text-md-right row">

                                            <h6 class="mr-4"><strong>Price: $${orderDetail.product.price}</strong></h6>

                                            <h6 class="mr-4"><strong>Quantity: ${orderDetail.product.quantity}</strong></h6>

                                            <h6><strong>Total: $${orderDetail.product.quantity * orderDetail.product.price}</strong></h6>

                                        </div>
                                    </div>
                                    <c:if test="${counterOD.count != order.orderDetail.size()}">
                                        <hr>
                                    </c:if>
                                </c:forEach>
                                <!-- END PRODUCT -->

                            </div>
                            <div class="card-footer">
                                <div class="coupon col-md-5 col-sm-5 no-padding-left pull-left" style="margin: 10px">
                                    <fmt:formatDate var="date" pattern="dd-MM-yyyy H:m:s" value="${order.date}" />
                                    Order Date: <strong>${date}</strong>
                                </div>
                                <div class="pull-right" style="margin: 10px">
                                    Total price: <b>${order.total}</b>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <div class="row">
                        <div class="col-lg-6 offset-lg-3 py-5 d-flex">
                            <jsp:include page="paging.jsp" >
                                <jsp:param name="currentPage" value="${requestScope.currentPage}" />
                                <jsp:param name="noOfPages" value="${requestScope.noOfPages}" />
                                <jsp:param name="hrefLink" value="${requestScope.PAGING_LINK}" />
                            </jsp:include>
                        </div>
                    </div>
                </c:if>
            </c:if>
        </div>
        <script>
            $('#btn-search-panel').click(function () {
                $('.search-container').slideToggle('slow');
            });
        </script>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
