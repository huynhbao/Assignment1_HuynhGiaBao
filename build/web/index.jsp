<%-- 
    Document   : index
    Created on : Jan 6, 2021, 7:34:06 PM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="css/filter.css" rel="stylesheet">
        <style>
            .hide {
                display:none;
            }
        </style>
    </head>
    <body>



        <jsp:include page="menu.jsp"/>

        <fmt:setLocale value = "vi_VN"/>

        <div class="container py-5">
            <div class="filter">
                <div class="filter-left">
                    <ul>
                        <li onclick="location.href = 'MainController?btnAction=Search';" class="filter-category ${param.txtCategory == null or empty param.txtCategory ? 'active' : ''}">All</li>
                            <c:forEach items="${requestScope.CATEGORY_LIST}" var="category">

                            <li onclick="location.href = 'MainController?btnAction=Search&txtCategory=${category.categoryID}';" class="filter-category ${param.txtCategory == category.categoryID ? 'active' : ''}">${category.name}</li>
                            </c:forEach>
                    </ul>
                </div>
                <div class="filter-right">
                    <button class="btn btn-outline-secondary" id="btn-search-panel" type="button">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </div>
            <c:set var="searching" value="${not empty param.txtSearchName or not empty param.txtSearchPriceStart or not empty param.txtSearchPriceEnd}" />
            <div class="search-container mb-5 ${searching == true ? '' : 'hide'}">
                <div class="bg-white p-3 rounded shadow">
                    <form action="MainController">
                        <div class="form-group mb-4">
                            <input class="form-control border-primary" type="text" placeholder="Search Name" name="txtSearchName" value="${param.txtSearchName}">
                        </div>
                        <div class="form-group mb-4">
                            <input class="form-control border-primary" type="text" placeholder="Price min" name="txtSearchPriceStart" value="${param.txtSearchPriceStart}">
                        </div>
                        <div class="form-group mb-4">
                            <input class="form-control border-primary" type="text" placeholder="Price max" name="txtSearchPriceEnd" value="${param.txtSearchPriceEnd}">
                        </div>
                        <div class="form-group mb-4 text-center">
                            <input type="hidden" name="txtCategory" value="${param.txtCategory}">
                            <p class="text-danger">${requestScope.ERROR_MISSING_SEARCH_PRICE}</p>
                            <p class="text-danger">${requestScope.ERROR_NUMBER_SEARCH_PRICE}</p>


                            <button type="submit" class="btn btn-primary btn-lg btn-radius" name="btnAction" value="Search">Search</button>
                        </div>

                    </form>
                    <!-- End -->

                </div>
            </div>
            <div class="row pb-5 mb-4">
                <c:forEach items="${requestScope.PRODUCT_LIST}" var="product">
                    <div class="col-4 mt-4">
                        <!-- Card-->
                        <div class="card rounded shadow-sm border-0">
                            <div class="card-body p-4"><img src="${product.imgLink}" alt="" class="card-img-top embed-responsive-item" style="width: 100%; height: 170px; object-fit: cover;">
                                <h5> <a href="#" class="text-dark">${product.name}</a></h5>
                                <h6> <fmt:formatNumber value = "${product.price}" type = "currency"/></h6>
                                <p class="small text-muted font-italic" style="height: 100px;">${product.description}</p>
                                <ul class="list-inline small">
                                    <fmt:formatDate var="date" pattern="dd/MM/yyyy" value="${product.date}" />
                                    <fmt:formatDate var="expiryDate" pattern="dd/MM/yyyy" value="${product.expiryDate}" />
                                    <li class="list-inline-item m-0">Create Date ${date}</li><br>
                                    <!--<li class="list-inline-item m-0">Expiry Date ${expiryDate}</li><br>-->
                                    <li class="list-inline-item m-0">Category: ${product.categoryName}</li>
                                </ul>
                                <c:url value = "MainController" var = "addLink">
                                    <c:param name="btnAction" value="AddProduct" />
                                    <c:param name="txtProductID" value="${product.productID}" />
                                </c:url>
                                <a class="btn btn-outline-primary w-100" id="btn-search-panel" href="${addLink}">
                                    <i class="fa fa-cart-plus"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
            <c:if test="${empty requestScope.PRODUCT_LIST}">
                <div class="row">
                    <div class="col-12 text-center">
                        <img class="center-block img-responsive" src="images/not-found.jpg" alt="product-empty"/>
                    </div>
                </div>
            </c:if>
            <div class="row">
                <div class="col-lg-6 offset-lg-3 py-5 d-flex">
                    <jsp:include page="paging.jsp" >
                        <jsp:param name="currentPage" value="${currentPage}" />
                        <jsp:param name="noOfPages" value="${noOfPages}" />
                        <jsp:param name="hrefLink" value="MainController?btnAction=Shopping&txtSearchName=${param.txtSearchName}&txtSearchPriceStart=${param.txtSearchPriceStart}&txtSearchPriceEnd=${param.txtSearchPriceEnd}&txtCategory=${param.txtCategory}" />
                    </jsp:include>
                </div>
            </div>



        </div>

        <script>
            $('#btn-search-panel').click(function () {
                $('.search-container').slideToggle('slow');
            });
        </script>

        <jsp:include page="footer.jsp"/>

    </body>
</html>
