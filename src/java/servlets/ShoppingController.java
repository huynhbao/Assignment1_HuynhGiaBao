/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.ProductDAO;
import dtos.CategoryDTO;
import dtos.ProductDTO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import utils.MyConstants;

/**
 *
 * @author HuynhBao
 */
@WebServlet(name = "ShoppingController", urlPatterns = {"/home"})
public class ShoppingController extends HttpServlet {

    private static final String ERROR = "invalid.jsp";
    private static final String SUCCESS = "index.jsp";

    private static final Logger LOGGER = Logger.getLogger(ShoppingController.class);
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
            ProductDAO pDAO = new ProductDAO();

            String searchName = request.getParameter("txtSearchName");
            String searchPriceStart = request.getParameter("txtSearchPriceStart");
            String searchPriceEnd = request.getParameter("txtSearchPriceEnd");
            String searchCategory = request.getParameter("txtCategory");

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

            if (searchCategory == null) {
                searchCategory = "";
            }
            if (searchName == null) {
                searchName = "";
            }
            if (searchPriceStart == null) {
                searchPriceStart = "";
            }
            if (searchPriceEnd == null) {
                searchPriceEnd = "";
            }

            Map<Integer, List<ProductDTO>> productList;
            if (!"".equals(searchCategory) && ("".equals(searchName) && "".equals(searchPriceStart) && "".equals(searchPriceEnd))) {
                productList = pDAO.getProductsByCategory(currentPage, searchCategory);
            } else {
                boolean emptyField = "".equals(searchName) && "".equals(searchPriceStart) && "".equals(searchPriceEnd);
                if (emptyField) {
                    productList = pDAO.getProducts(currentPage);
                } else {
                    boolean allowSearch = false;
                    boolean missingSearchPrice = ("".equals(searchPriceStart) || "".equals(searchPriceEnd)) && (!"".equals(searchPriceStart) || !"".equals(searchPriceEnd));

                    if (!"".equals(searchName)) {
                        allowSearch = true;
                    }

                    if (!missingSearchPrice) {
                        if (!"".equals(searchPriceStart) && !"".equals(searchPriceEnd)) {
                            try {
                                int min = Integer.parseInt(searchPriceStart);
                                int max = Integer.parseInt(searchPriceEnd);
                                if (min > max) {
                                    allowSearch = false;
                                    request.setAttribute("ERROR_NUMBER_SEARCH_PRICE", "Search price end must greater or equal price start!");
                                } else {
                                    allowSearch = true;
                                }
                            } catch (NumberFormatException e) {
                                allowSearch = false;
                                request.setAttribute("ERROR_NUMBER_SEARCH_PRICE", "Price must be float!");
                            }
                        }
                    } else {
                        request.setAttribute("ERROR_MISSING_SEARCH_PRICE", "Please input all field search price!");
                        allowSearch = false;
                    }

                    if (allowSearch) {
                        productList = pDAO.searchProduct(currentPage, searchName, searchPriceStart, searchPriceEnd, searchCategory);
                    } else {
                        productList = pDAO.getProducts(currentPage);
                    }
                }
            }

            List<CategoryDTO> categoryList = pDAO.getCategories();
            int rows = productList.keySet().stream().findFirst().get();

            int nOfPages = (int) Math.ceil(rows / (double) MyConstants.recordPerPage);

            request.setAttribute("PRODUCT_LIST", productList.get(rows));
            request.setAttribute("CATEGORY_LIST", categoryList);
            request.setAttribute("noOfPages", nOfPages);
            request.setAttribute("currentPage", currentPage);
            url = SUCCESS;
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
