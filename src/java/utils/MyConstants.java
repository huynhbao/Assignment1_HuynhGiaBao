/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author HuynhBao
 */
public class MyConstants {
    
    public static final int recordPerPage = 20;
    public static final String defaultImgLink = "images/default_thumbnail.jpg";
    public static final String LOGIN = "login.jsp";
    public static final String HOME_PAGE = "home";
    public static final String INVALID_PAGE = "invalid.jsp";
    public static final String AD_ROLE = "AD";
    public static final String US_ROLE = "US";
    
    //Google Login
    public static String GOOGLE_CLIENT_ID = "975999887928-u55ge5bjjbcjli6gi8gvcfk4m24ruoes.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "l5HIbH8mDgcgJPUILNZSFzMP";
    public static String GOOGLE_REDIRECT_URI = "http://localhost:8084/Assignment1_HuynhGiaBao/MainController?btnAction=LoginGoogle";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";
    //////////////
    
    
    
    /////   Paypal   /////
    public static final String CLIENT_ID = "AVfHvsfzSmGhn3cy6EwC4j14pLIox4f8OsGm0mGh-V6C8BMGvDOxHUZsyCRTnIcA9SidY3-V625H5CZt";
    public static final String CLIENT_SECRET = "EPJTOJi5J3EjCWHaB1C5hSmAQ4yEoCZNS0ELFcm8QAN2KIpOQUt3qBd09aOrS7stk5yk9pfwotrX3Hhq";
    public static final String MODE = "sandbox";
    
    
    public static final String cancelUrl = "http://localhost:8084/Assignment1_HuynhGiaBao/cart.jsp";
    public static final String returnUrl = "http://localhost:8084/Assignment1_HuynhGiaBao/MainController?btnAction=ExecutePayment";
    
    ////////////////////////
}
