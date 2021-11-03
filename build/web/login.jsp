<%-- 
    Document   : login
    Created on : Jan 6, 2021, 7:24:03 PM
    Author     : HuynhBao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
              integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
              crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
        <style>

            /* sign in FORM */
            #logreg-forms{
                width:412px;
                margin:10vh auto;
                background-color:#f3f3f3;
                box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
                transition: all 0.3s cubic-bezier(.25,.8,.25,1);
            }
            #logreg-forms form {
                width: 100%;
                max-width: 410px;
                padding: 15px;
                margin: auto;
            }
            #logreg-forms .form-control {
                position: relative;
                box-sizing: border-box;
                height: auto;
                padding: 10px;
                font-size: 16px;
            }
            #logreg-forms .form-control:focus { z-index: 2; }
            #logreg-forms .form-signin input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            #logreg-forms .form-signin input[type="password"] {
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }

            #logreg-forms .social-login{
                width:390px;
                margin:0 auto;
                margin-bottom: 14px;
            }
            #logreg-forms .social-btn{
                font-weight: 100;
                color:white;
                font-size: 0.9rem;
            }

            #logreg-forms a{
                display: block;
                padding-top:10px;
                color:lightseagreen;
            }

            #logreg-form .lines{
                width:200px;
                border:1px solid red;
            }


            #logreg-forms button[type="submit"]{ margin-top:10px; }

            #logreg-forms .facebook-btn{  background-color:#3C589C; }

            #logreg-forms .google-btn{ background-color: #DF4B3B; }

            #logreg-forms .form-reset, #logreg-forms .form-signup{ display: none; }

            #logreg-forms .form-signup .social-btn{ width:210px; }

            #logreg-forms .form-signup input { margin-bottom: 2px;}

            .form-signup .social-login{
                width:210px !important;
                margin: 0 auto;
            }

            /* Mobile */

            @media screen and (max-width:500px){
                #logreg-forms{
                    width:300px;
                }

                #logreg-forms  .social-login{
                    width:200px;
                    margin:0 auto;
                    margin-bottom: 10px;
                }
                #logreg-forms  .social-btn{
                    font-size: 1.3rem;
                    font-weight: 100;
                    color:white;
                    width:200px;
                    height: 56px;

                }
                #logreg-forms .social-btn:nth-child(1){
                    margin-bottom: 5px;
                }
                #logreg-forms .social-btn span{
                    display: none;
                }
                #logreg-forms  .facebook-btn:after{
                    content:'Facebook';
                }

                #logreg-forms  .google-btn:after{
                    content:'Google+';
                }

            }
        </style>
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div id="logreg-forms" style="margin-top: 140px;">
            <form action="MainController" method="POST" class="form-signin">
                <h1 class="h3 mb-3 font-weight-normal" style="text-align: center"> Sign in</h1>
                <div class="social-login text-center">
                    <a class="btn btn-block google-btn social-btn" href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/Assignment1_HuynhGiaBao/MainController?btnAction=LoginGoogle&response_type=code&client_id=975999887928-u55ge5bjjbcjli6gi8gvcfk4m24ruoes.apps.googleusercontent.com&approval_prompt=force${baseURL}" role="button">
                        <i class="fa fa-google-plus"></i>
                        Login with Google
                    </a>
                </div>
                <p style="text-align:center"> OR  </p>
                <p>${LOGIN_MSG}</p>
                <input type="text" id="inputEmail" class="form-control" name="txtUserID" placeholder="User ID" required="" autofocus="">
                <input type="password" id="inputPassword" class="form-control" name="txtPassword" placeholder="Password" required="">

                <button class="btn btn-success btn-block" type="submit" name="btnAction" value="Login"><i class="fas fa-sign-in-alt"></i> Sign in</button>
                
            </form>

        </div>

    </body>
</html>
