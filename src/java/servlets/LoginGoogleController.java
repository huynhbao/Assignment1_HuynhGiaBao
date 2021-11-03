/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.UserDAO;
import dtos.GoogleDTO;
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
import utils.GoogleUtils;
import utils.MyConstants;

/**
 *
 * @author HuynhBao
 */
@WebServlet(name = "LoginGoogleController", urlPatterns = {"/LoginGoogleController"})
public class LoginGoogleController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String SUCCESS = "MainController?btnAction=Shopping";

    private static final Logger LOGGER = Logger.getLogger(LoginGoogleController.class);
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
            UserDTO userSs = (UserDTO) session.getAttribute("LOGIN_USERDTO");

            if (userSs != null) {
                url = MyConstants.HOME_PAGE;
            } else {

                String code = request.getParameter("code");
                if (code == null || code.isEmpty()) {
                    //error code
                } else {
                    UserDAO dao = new UserDAO();
                    String accessToken = GoogleUtils.getToken(code);
                    GoogleDTO googleDTO = GoogleUtils.getUserInfo(accessToken);
                    Date curDate = new Date();
                    String userID = "G-" + googleDTO.getId();
                    String email = googleDTO.getEmail();
                    String name = email.substring(0, email.lastIndexOf("@")).replaceAll("[^a-zA-Z]", "");
                    String password = String.valueOf(curDate.getTime());

                    UserDTO user = new UserDTO(userID, name, password, email, "", "", true, "US");

                    UserDTO userChecked = dao.loginGmail(user);

                    if (userChecked.getStatus()) {
                        session.setAttribute("LOGIN_USERDTO", userChecked);

                        url = SUCCESS;
                    } else {
                        request.setAttribute("LOGIN_MSG", "Your account has been disabled");
                    }
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
