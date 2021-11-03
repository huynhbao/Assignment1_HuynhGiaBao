/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.PaymentDAO;
import daos.ProductDAO;
import daos.UserDAO;
import dtos.CartDTO;
import dtos.OrderDTO;
import dtos.ProductDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
 * @author HuynhBao
 */
@WebServlet(name = "CheckoutController", urlPatterns = {"/CheckoutController"})
public class CheckoutController extends HttpServlet {

    private static final String ERROR = "cart.jsp";
    private static final String SUCCESS = "checkout.jsp";

    private static final Logger LOGGER = Logger.getLogger(CheckoutController.class);
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
        boolean paypalPayment = false;
        String approvalLink = "";
        try {

            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USERDTO");

            if (user == null) {
                url = MyConstants.LOGIN;
            } else if (MyConstants.AD_ROLE.equals(user.getRoleID())) {
                url = MyConstants.HOME_PAGE;
            } else {
                CartDTO cart = (CartDTO) session.getAttribute("CART");

                String paymentMethod = request.getParameter("paymentMethod");
                if (cart != null) {

                    ProductDAO dao = new ProductDAO();

                    List<ProductDTO> list = dao.getProductsOutOfStock(cart);

                    if (list != null) {
                        request.setAttribute("OUT_OF_STOCK_LIST", list);
                    } else {
                        if ("paypal".equals(paymentMethod)) {
                            PaymentDAO paymentDAO = new PaymentDAO();
                            approvalLink = paymentDAO.authorizePayment(new ArrayList(cart.getCart().values()));
                            paypalPayment = true;
                        } else {
                            UserDAO uDAO = new UserDAO();
                            int orderID = uDAO.checkout(cart);
                            OrderDTO order = uDAO.getOrderDTO(orderID);
                            if (order != null) {
                                url = SUCCESS;
                            }
                        }

                    }

                }
            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            if (paypalPayment) {
                response.sendRedirect(approvalLink);
            } else {
                request.getRequestDispatcher(url).forward(request, response);
            }

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
