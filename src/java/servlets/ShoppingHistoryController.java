/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.UserDAO;
import dtos.OrderDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "ShoppingHistoryController", urlPatterns = {"/user-history"})
public class ShoppingHistoryController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String SUCCESS = "shopping-history.jsp";
    
    private static final Logger LOGGER = Logger.getLogger(ShoppingHistoryController.class);

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
                url = ERROR;
            } else if (MyConstants.AD_ROLE.equals(user.getRoleID())) {
                url = MyConstants.HOME_PAGE;
            } else {
                String currentPageParam = request.getParameter("page");
                int currentPage = 1;
                if (currentPageParam != null) {
                    if ("".equals(currentPageParam)) {
                        currentPage = 1;
                    } else {
                        currentPage = Integer.parseInt(currentPageParam);

                        if (currentPage == 0) {
                            currentPage = 1;
                        }
                    }
                }

                UserDAO dao = new UserDAO();
                Map<Integer, List<OrderDTO>> map = dao.getUserOrderHistory(user.getUserID(), currentPage);

                int rows = map.keySet().stream().findFirst().get();
                int nOfPages = (int) Math.ceil(rows / (double) MyConstants.recordPerPage);

                request.setAttribute("noOfPages", nOfPages);
                request.setAttribute("currentPage", currentPage);
                request.setAttribute("PAGING_LINK", "MainController?btnAction=UserHistory");

                request.setAttribute("LIST", map.get(rows));
                url = SUCCESS;
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
