/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.AdminDAO;
import dtos.RecordDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.Date;
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
@WebServlet(name = "AdminDeleteProductController", urlPatterns = {"/AdminDeleteProductController"})
public class AdminDeleteProductController extends HttpServlet {

    private static final String ERROR = "MainController?btnAction=AdminManageProducts";
    private static final String SUCCESS = "AdminManageProductsController";
    private static final String LOGIN = "MainController?btnAction=Login";

    private static final Logger LOGGER = Logger.getLogger(AdminDeleteProductController.class);
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

            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USERDTO");

            if (user == null) {
                url = LOGIN;
            } else if (!MyConstants.AD_ROLE.equals(user.getRoleID())) {
                url = ERROR;
                request.setAttribute("MSG", "You do not have permission!");
            } else {
                String[] productList = request.getParameterValues("txtSelectedProduct");
                AdminDAO dao = new AdminDAO();
                Date date = new Date();
                for (int i = 0; i < productList.length; i++) {
                    dao.setStatusProduct(productList[i], false);
                    String action = "delete";
                    String content = "set status from true to false";
                    RecordDTO record = new RecordDTO(0, date, user.getUserID(), productList[i], action, content);
                    dao.recordAction(record);
                }
                url = SUCCESS;
            }

        } catch (Exception e) {
            LOGGER.error(e.toString());
        } finally {
            response.sendRedirect(url);
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
