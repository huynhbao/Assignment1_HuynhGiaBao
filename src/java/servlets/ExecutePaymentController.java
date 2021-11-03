/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import daos.PaymentDAO;
import daos.UserDAO;
import dtos.CartDTO;
import dtos.OrderDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.MyConstants;

/**
 *
 * @author HuynhBao
 */
@WebServlet(name = "ExecutePaymentController", urlPatterns = {"/ExecutePaymentController"})
public class ExecutePaymentController extends HttpServlet {

    private static final String ERROR = "cart.jsp";
    private static final String SUCCESS = "checkout.jsp";

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(ExecutePaymentController.class);
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws com.paypal.base.rest.PayPalRESTException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, PayPalRESTException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        String paymentId = request.getParameter("paymentId");
        String payerId = request.getParameter("PayerID");

        try {
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USERDTO");

            if (user == null) {
                url = MyConstants.LOGIN;
            } else if (MyConstants.AD_ROLE.equals(user.getRoleID())) {
                url = MyConstants.HOME_PAGE;
            } else {
                PaymentDAO dao = new PaymentDAO();
                Payment payment = dao.executePayment(paymentId, payerId);

                Transaction transaction = payment.getTransactions().get(0);

                CartDTO cart = (CartDTO) session.getAttribute("CART");

                UserDAO uDAO = new UserDAO();
                int orderID = uDAO.checkout(cart);
                OrderDTO order = uDAO.getOrderDTO(orderID);
                if (order != null) {
                    request.setAttribute("TRANSACTION", transaction);
                    url = SUCCESS;
                }
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
        try {
            processRequest(request, response);
        } catch (PayPalRESTException ex) {
            Logger.getLogger(ExecutePaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (PayPalRESTException ex) {
            Logger.getLogger(ExecutePaymentController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
