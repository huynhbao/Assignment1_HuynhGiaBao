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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "SearchHistoryController", urlPatterns = {"/SearchHistoryController"})
public class SearchHistoryController extends HttpServlet {

    private static final String ERROR = "MainController?btnAction=UserHistory";
    private static final String SUCCESS = "shopping-history.jsp";

    private static final Logger LOGGER = Logger.getLogger(SearchHistoryController.class);
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
                String searchDateFrom = request.getParameter("txtSearchDateFrom");
                String searchDateTo = request.getParameter("txtSearchDateTo");
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

                if (searchDateFrom == null) {
                    searchDateFrom = "";
                }
                if (searchDateTo == null) {
                    searchDateTo = "";
                }

                boolean allowSearch = false;
                boolean missingSearchDate = ("".equals(searchDateTo) || "".equals(searchDateFrom)) && (!"".equals(searchDateTo) || !"".equals(searchDateFrom));
                Date dateFrom = null;
                Date dateTo = null;
                if (!missingSearchDate) {
                    if (!"".equals(searchDateTo) && !"".equals(searchDateFrom)) {
                        try {
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            dateFrom = df.parse(searchDateFrom);
                            dateTo = df.parse(searchDateTo);
                            if (dateFrom.after(dateTo)) {
                                allowSearch = false;
                                request.setAttribute("ERROR_SEARCH_DATE", "End Date must greater or equal start date!");
                            } else {
                                allowSearch = true;
                            }

                        } catch (ParseException e) {
                            allowSearch = false;
                            request.setAttribute("ERROR_SEARCH_DATE", "Date must be format at yyyy-MM-dd (Ex: 31/01/2020)");
                        }
                    }
                } else {
                    request.setAttribute("ERROR_SEARCH_DATE", "Please input all field search date!");
                    allowSearch = false;
                }

                

                if (allowSearch) {
                    UserDAO dao = new UserDAO();
                    Map<Integer, List<OrderDTO>> orderList = dao.searchOrderHistory(currentPage, user.getUserID(), dateFrom, dateTo);
                    int rows = orderList.keySet().stream().findFirst().get();

                    int nOfPages = (int) Math.ceil(rows / (double) MyConstants.recordPerPage);

                    String pageLink = String.format("MainController?btnAction=Search History&txtSearchDateFrom=%s&txtSearchDateTo=%s", searchDateFrom, searchDateTo);
                    request.setAttribute("PAGING_LINK", pageLink);
                    request.setAttribute("LIST", orderList.get(rows));
                    request.setAttribute("noOfPages", nOfPages);
                    request.setAttribute("currentPage", currentPage);
                    url = SUCCESS;
                } else {
                    url = ERROR;
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
