<%-- 
    Document   : manage-food
    Created on : Jan 12, 2021, 8:22:29 AM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Products Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <style>
            @media (min-width: 1200px) {
                .container{
                    max-width: 1470px !important;
                }
            }

            .hide {
                display:none;
            }

            h2.title {
                font-weight: 700;
                font-size: 24px;
                color: #000;
                margin-bottom: 30px;
                text-transform: uppercase;
                letter-spacing: 3px;
            }
        </style>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div class="container py-5" style="margin-top: 140px;">

            <!-- Modal MsgBox -->
            <div class="modal fade" id="msgModal" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            Message
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p id="modal-msg">Successful!</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Modal Confirm -->
            <div class="modal hide fade" id="confirm" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Do you want to delete?</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                            <button type="button" class="btn btn-primary" data-dismiss="modal" id="delete">Yes</button>
                        </div>
                    </div>
                </div>
            </div>


            <!-- Modal Form -->
            <div class="modal fade" id="productForm">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">

                        <!-- Modal Header -->
                        <div class="modal-header">
                            <h4 class="modal-title">Manage Product</h4>
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                        </div>

                        <form action="AdminCreateProductController" method="POST" enctype="multipart/form-data" id="action-form">
                            <!-- Modal body -->
                            <div class="modal-body">
                                <input type="hidden" name="page" value="${param.page}"/>
                                <div class="form-group">
                                    <label for="txtProductID">Product ID</label>
                                    <input type="text" class="form-control" id="txtProductID" name="txtProductID" placeholder="Enter Product ID" value="${param.txtProductID}">
                                    <small class="text-danger">
                                        ${requestScope.ERROR_PRODUCTID}
                                    </small>
                                </div>
                                <div class="form-group">
                                    <label for="txtProductName">Product Name</label>
                                    <input type="text" class="form-control" id="txtProductName" name="txtProductName" placeholder="Enter Product Name" value="${param.txtProductName}">
                                    <small class="text-danger">
                                        ${requestScope.ERROR_NAME}
                                    </small>
                                </div>
                                <div class="form-group">
                                    <label for="txtDescription">Description</label>
                                    <input type="text" class="form-control" id="txtDescription" name="txtDescription" placeholder="Enter Description" value="${param.txtDescription}">
                                    <small class="text-danger">
                                        ${requestScope.ERROR_DESCRIPTION}
                                    </small>
                                </div>
                                <div class="form-group">
                                    <label for="txtQuantity">Quantity</label>
                                    <input type="text" class="form-control" id="txtQuantity" name="txtQuantity" placeholder="Enter Quantity" value="${param.txtQuantity}">
                                    <small class="text-danger">
                                        ${requestScope.ERROR_QUANTITY}
                                    </small>
                                </div>
                                <div class="form-group">
                                    <label for="txtPrice">Price</label>
                                    <input type="text" class="form-control" id="txtPrice" name="txtPrice" placeholder="Enter Price" value="${param.txtPrice}">
                                    <small class="text-danger">
                                        ${requestScope.ERROR_PRICE}
                                    </small>
                                </div>
                                <div class="form-group">
                                    <label for="txtExpiryDate">Expiry Date</label>
                                    <input type="date" class="form-control" id="txtExpiryDate" name="txtExpiryDate" value="${param.txtExpiryDate}">
                                    <small class="text-danger">
                                        ${requestScope.ERROR_DATE}
                                    </small>
                                </div>
                                <div class="form-group">
                                    <label for="cbCategory">Category</label>
                                    <select class="form-control" id="cbCategory" name="cbCategory">
                                        <c:forEach items="${requestScope.CATEGORY_LIST}" var="category">
                                            <option ${param.cbCategory == category.categoryID ? 'selected' : ''} value="${category.categoryID}">${category.name}</option>
                                        </c:forEach>
                                    </select>
                                    <small class="text-danger">
                                        ${requestScope.ERROR_CATEGORY}
                                    </small>
                                </div>
                                <div class="form-check radio-form">
                                    <label class="form-check-label">
                                        <input type="radio" class="form-check-input radioImg" id="radioCurImg" name="radioImg" value="currentImg" checked="checked">Using current image
                                    </label>
                                    <br>
                                    <label class="form-check-label">
                                        <input type="radio" class="form-check-input radioImg" id="radioNewImg" name="radioImg" value="newImg">Upload new image
                                    </label>
                                    <br>
                                    <label class="form-check-label">
                                        <input type="radio" class="form-check-input radioImg" id="radioDefaultImg" name="radioImg" value="defaultImg">Using default image
                                    </label>
                                </div>


                                <div class="form-group text-center">
                                    <img id="imgPreview" src="#"  alt="" class="card-img-top embed-responsive-item" style="width: 300px; height: 200px; object-fit: cover;"/>

                                </div>
                                <div class="form-group upload-form text-center hide">
                                    <input type="file" onchange="readURL(this)" class="form-control-file" id="imgUpload" name="imgUpload">
                                </div>

                            </div>

                            <!-- Modal footer -->
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                <input type="submit" class="btn btn-primary" id="btnAction" name="btnAction" value="Create Product"/>
                            </div>
                        </form>

                    </div>
                </div>
            </div>

            <div class="d-flex justify-content-between">
                <div>
                    <h2 class="title text-left">Shopping History</h2>
                </div>
                <div>
                    <button type="button" id="btn-add" class="btn btn-primary" data-toggle="modal" data-target="#productForm">
                        <i class="fa fa-plus"></i>
                        Add Product
                    </button>
                    <button type="button" id="btn-add" class="btn btn-danger" data-toggle="modal" data-target="#confirm" onclick="deleteProduct(); return;">
                        <i class="fa fa-trash"></i>
                        Delete Product
                    </button>
                </div>
            </div>


            <div class="row pb-5 mb-4">
                <c:if test="${requestScope.LIST != null}">
                    <c:if test="${not empty requestScope.LIST}">
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead class="thead-light">
                                    <tr>
                                        <th scope="col">No</th>
                                        <th scope="col">Image</th>
                                        <th scope="col">Product ID</th>
                                        <th scope="col">Name</th>
                                        <th scope="col">Description</th>
                                        <th scope="col">Quantity</th>
                                        <th scope="col">Price</th>
                                        <th scope="col">Category</th>
                                        <th scope="col">Date Create</th>
                                        <th scope="col">Date Expired</th>
                                        <th scope="col">Status</th>
                                        <th scope="col">Action</th>
                                        <th scope="col">Select</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <form action="AdminDeleteProductController" method="POST" id="delete-form">

                                        <c:forEach items="${requestScope.LIST}" var="product" varStatus="counter">
                                            <tr>
                                                <th class="align-middle" scope="row">${counter.count}</th>
                                                <td class="align-middle column-data product-imgLink"><img src="${product.imgLink}" alt="" class="card-img-top embed-responsive-item" style="width: 100px; height: 60px; object-fit: cover;"></td>
                                                <td class="align-middle column-data product-id">${product.productID}</td>
                                                <td class="align-middle column-data product-name">${product.name}</td>
                                                <td class="align-middle column-data product-description">${product.description}</td>
                                                <td class="align-middle column-data product-quantity">${product.quantity}</td>
                                                <td class="align-middle column-data product-price"><fmt:formatNumber value = "${product.price}" type = "currency"/></td>
                                                <td class="align-middle column-data product-categoryID">${product.categoryID}</td>
                                                <fmt:formatDate var="date" pattern="dd-MM-yyyy H:m:s" value="${product.date}" />
                                                <fmt:formatDate var="expiryDate" pattern="dd-MM-yyyy" value="${product.expiryDate}" />
                                                <td class="align-middle">${date}</td>
                                                <td class="align-middle column-data expiry-date">${expiryDate}</td>
                                                <td class="align-middle">
                                                    <c:choose>
                                                        <c:when test="${product.status}" >
                                                            <i class="fa fa-toggle-on btn btn-success btn-xs"></i>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <i class="fa fa-toggle-off btn btn-danger btn-xs"></i>
                                                            </c:otherwise>
                                                        </c:choose>
                                                </td>
                                                <td class="align-middle">
                                                    <!--<button onclick="toggleUpdateProduct()">Update</button>-->
                                                    <!--<input class="editBtn" type="button" value="Edit">-->
                                                    <button class="btn btn-info btn-xs editBtn" type="button"><i class="fa fa-pencil-square-o"></i></button>
                                                </td>
                                                <td class="align-middle text-center">
                                                    <input type="checkbox" name="txtSelectedProduct" value="${product.productID}">
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </form>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </c:if>


            </div>
            <div class="row">
                <div class="col-lg-6 offset-lg-3 py-5 d-flex">
                    <jsp:include page="paging.jsp" >
                        <jsp:param name="currentPage" value="${requestScope.currentPage}" />
                        <jsp:param name="noOfPages" value="${requestScope.noOfPages}" />
                        <jsp:param name="hrefLink" value="MainController?btnAction=AdminManageProducts" />
                    </jsp:include>
                </div>
            </div>

        </div>
        <script>

            var currentImg = '';

            var defaultImg = 'images/default_thumbnail.jpg';

            $(document).ready(function () {
                var errorCreate = ${requestScope.CREATE_ERROR == null ? false : requestScope.CREATE_ERROR};
                var errorUpdate = ${requestScope.UPDATE_ERROR == null ? false : requestScope.UPDATE_ERROR};
                var createSuccessful = ${requestScope.CREATE_MSG == null ? false : true};
                var updateSuccessful = ${requestScope.UPDATE_MSG == null ? false : true};
                if (errorCreate) {

                    $('#productForm').modal('show');
                    $('#btnAction').val("Create Product");
                    $("#txtProductID").attr("readonly", false);
                    $('#imgPreview').attr('src', defaultImg);
                    $('.radio-form').css("display", "none");
                    $('.upload-form').css("display", "block");
                }

                if (errorUpdate) {
                    $('#productForm').modal('show');
                    $('#btnAction').val("Update Product");
                    $("#txtProductID").attr("readonly", true);
                    $('.radio-form').css("display", "block");
                    $(".upload-form").css("display", "none");
                    $('#imgPreview').attr('src', defaultImg);
                    $("#action-form").attr('action', 'AdminUpdateProductController');
                }

                if (createSuccessful) {
                    $('#msgModal').modal('show');
                }

                if (updateSuccessful) {
                    $('#msgModal').modal('show');
                }

            });

            function deleteProduct() {
                $('#confirm')
                        .modal({backdrop: 'static', keyboard: false})
                        .one('click', '#delete', function (e) {
                            document.getElementById('delete-form').submit();
                        });
            }
            ;

            $('#btn-add').click(function () {
                $('#btnAction').val("Create Product");
                $("#txtProductID").attr("readonly", false);
                $('.radio-form').css("display", "none");
                $('#imgPreview').attr('src', defaultImg);
                $('.upload-form').css("display", "block");
                $("#action-form").attr('action', 'AdminCreateProductController');
            });

            $('#productForm').on('hidden.bs.modal', function () {
                $(this).find('form').trigger('reset');
                $('#imgPreview').attr('src', '#');
                $('#imgUpload').css("display", "block");
                $("#txtProductID").attr("readonly", false);
                $(".text-danger").empty();
                currentImg = 'images/default_thumbnail.jpg';
            });

            $('.editBtn').click(function () {
                $(this).parents('tr').find('td.column-data').each(function () {
                    var html = $(this).html();
                    var className = $(this).attr('class');

                    if (className.indexOf('product-id') !== -1) {
                        $("#txtProductID").val(html);
                    } else if (className.indexOf('product-name') !== -1) {
                        $("#txtProductName").val(html);
                    } else if (className.indexOf('product-description') !== -1) {
                        $("#txtDescription").val(html);
                    } else if (className.indexOf('product-quantity') !== -1) {
                        $("#txtQuantity").val(html);
                    } else if (className.indexOf('product-price') !== -1) {
                        $("#txtPrice").val(html);
                        //console.log(html);
                    } else if (className.indexOf('expiry-date') !== -1) {
                        var parts = html.split('-');
                        var mydate = parts[2] + '-' + parts[1] + '-' + parts[0];
                        $("#txtExpiryDate").val(mydate);
                    } else if (className.indexOf('product-categoryID') !== -1) {
                        $("#cbCategory option[value=" + html + "]").attr('selected', 'selected');
                    } else if (className.indexOf('product-imgLink') !== -1) {
                        var imgLink = $('img', this).attr('src');
                        $("#imgPreview").attr('src', imgLink);
                        currentImg = imgLink;
                    }

                    $('#productForm').modal('show');
                    $('#btnAction').val("Update Product");
                    $("#txtProductID").attr("readonly", true);
                    $('.radio-form').css("display", "block");
                    $(".upload-form").css("display", "none");
                    $("#action-form").attr('action', 'AdminUpdateProductController');
                });
            });



            $('.radioImg').on('change', function () {
                $("#imgUpload").val('');
                if ($("#radioCurImg").is(":checked")) {
                    $('.upload-form').css("display", "none");
                    $('#imgPreview').attr('src', currentImg);
                }
                if ($("#radioNewImg").is(":checked")) {
                    $('.upload-form').css("display", "block");
                    $('#imgPreview').attr('src', defaultImg);
                }
                if ($("#radioDefaultImg").is(":checked")) {
                    $('.upload-form').css("display", "none");
                    $('#imgPreview').attr('src', defaultImg);
                }
            });

            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {


                        $('#imgPreview').attr('src', e.target.result);
                    }

                    reader.readAsDataURL(input.files[0]); // convert to base64 string
                }
            }

        </script>
        <jsp:include page="footer.jsp"/>
    </body>
</html>
