/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dtos.UserDTO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import utils.MyConstants;

/**
 *
 * @author admin
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String ERROR = "invalid.jsp";
    private static final String LOGIN = "LoginController";
    private static final String LOGIN_GOOGLE = "LoginGoogleController";
    private static final String LOGOUT = "LogoutController";
    private static final String SHOPPING = "home";
    private static final String CART = "cart.jsp";
    private static final String SEARCH_PRODUCT = "home";
    private static final String ADD_PRODUCT_TO_CART = "AddProductController";
    private static final String DELETE_PRODUCT_IN_CART = "DeleteProductController";
    private static final String UPDATE_PRODUCT_IN_CART = "UpdateProductController";
    private static final String CHECKOUT = "CheckoutController";
    private static final String EXECUTE_PAYMENT = "ExecutePaymentController";
    private static final String USER_HISTORY = "user-history";
    private static final String SEARCH_HISTORY = "SearchHistoryController";

    private static final String ADMIN_MANAGE_PRODUCTS = "AdminManageProductsController";
    private static final String ADMIN_CREATE_PRODUCT = "AdminCreateProductController";
    private static final String ADMIN_UPDATE_PRODUCT = "AdminUpdateProductController";

    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("btnAction");
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USERDTO");

            if ("Login".equals(action)) {
                url = LOGIN;
            } else if ("LoginGoogle".equals(action)) {
                url = LOGIN_GOOGLE;
            } else if ("Logout".equals(action)) {
                url = LOGOUT;
            } else if ("Shopping".equals(action)) {
                url = SHOPPING;
            } else if ("Cart".equals(action)) {
                if (user == null) {
                    url = LOGIN;
                } else if (MyConstants.AD_ROLE.equals(user.getRoleID())) {
                    url = SHOPPING;
                } else {
                    url = CART;
                }
            } else if ("Search".equals(action)) {
                url = SEARCH_PRODUCT;
            } else if ("AddProduct".equals(action)) {
                url = ADD_PRODUCT_TO_CART;
            } else if ("DeleteProduct".equals(action)) {
                url = DELETE_PRODUCT_IN_CART;
            } else if ("Update Item".equals(action)) {
                url = UPDATE_PRODUCT_IN_CART;
            } else if ("ExecutePayment".equals(action)) {
                url = EXECUTE_PAYMENT;
            } else if ("Confirm".equals(action)) {
                url = CHECKOUT;
            } else if ("UserHistory".equals(action)) {
                url = USER_HISTORY;
            } else if ("AdminManageProducts".equals(action)) {
                url = ADMIN_MANAGE_PRODUCTS;
            } else if ("Create Product".equals(action)) {
                url = ADMIN_CREATE_PRODUCT;
            } else if ("Update Product".equals(action)) {
                url = ADMIN_UPDATE_PRODUCT;
            } else if ("Search History".equals(action)) {
                url = SEARCH_HISTORY;
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
